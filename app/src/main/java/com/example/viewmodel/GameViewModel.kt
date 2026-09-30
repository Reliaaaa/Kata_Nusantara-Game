package com.example.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.GameDataProvider
import com.example.data.local.AppDatabase
import com.example.data.local.entity.CaseProgressEntity
import com.example.data.local.entity.UserProgressEntity
import com.example.data.remote.FirebaseService
import com.example.data.remote.GeminiService
import com.example.data.remote.GroundingResult
import com.example.data.remote.MusicGenerationResult
import com.example.data.remote.UserCloudProfile
import com.example.data.remote.VideoGenerationResult
import com.example.data.repository.GameRepository
import com.example.model.Achievement
import com.example.model.CaseData
import com.example.model.ChallengeQuestion
import com.example.model.DetectiveCharacter
import com.example.model.DetectiveRank
import com.example.model.DictionaryEntry
import com.example.model.StorylineStage
import com.example.model.Suspect
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class Screen {
    HOME,
    CASE_SELECTION,
    INVESTIGATION,
    DICTIONARY,
    ACHIEVEMENTS,
    SETTINGS,
    GROUNDING_INTELLIGENCE,
    MUSIC_STUDIO,
    VEO_RECONSTRUCTION,
    CLOUD_SYNC,
    LINEAR_STORYLINE
}

enum class CaseOutcomeType {
    VICTORY,
    DEFEAT
}

data class InvestigationState(
    val activeCase: CaseData? = null,
    val revealedClueIds: Set<String> = emptySet(),
    val eliminatedSuspectIds: Set<String> = emptySet(),
    val selectedSuspect: Suspect? = null,
    val activeChallenge: ChallengeQuestion? = null,
    val isChallengeOpen: Boolean = false,
    val heartsRemaining: Int = 3,
    val maxHearts: Int = 3,
    val currentScore: Int = 0,
    val elapsedSeconds: Int = 0,
    val outcome: CaseOutcomeType? = null,
    val accusedSuspect: Suspect? = null,
    val lastAnswerResult: AnswerResult? = null,
    val isAccusationOpen: Boolean = false,
    val hintsCount: Int = 3
)

data class StorylineUiState(
    val stage: StorylineStage = StorylineStage.CHARACTER_SELECTION,
    val selectedCharacter: DetectiveCharacter = GameDataProvider.characters.first(),
    val currentChapterIndex: Int = 0,
    val activeCase: CaseData = GameDataProvider.getInitialCases().first(),
    val currentQuestionIndex: Int = 0,
    val answeredQuestions: Map<String, Int> = emptyMap(),
    val unlockedClueIds: Set<String> = emptySet(),
    val interrogatedSuspectIds: Set<String> = emptySet(),
    val eliminatedSuspectIds: Set<String> = emptySet(),
    val selectedSuspectForInterrogation: Suspect? = null,
    val accusedSuspectId: String? = null,
    val selectedContradictionClueId: String? = null,
    val isConfrontationSuccess: Boolean = false,
    val confrontationFeedback: String? = null,
    val heartsRemaining: Int = 3,
    val maxHearts: Int = 3,
    val chapterScore: Int = 0,
    val currentAnswerResult: AnswerResult? = null,
    val isEnglishEnabled: Boolean = false,
    val isTranslatingWithAi: Boolean = false,
    val aiTranslationResult: String? = null
)

data class AnswerResult(
    val isCorrect: Boolean,
    val selectedIndex: Int,
    val explanation: String,
    val unlockedClueTitle: String? = null
)

data class GroundingUiState(
    val isLoading: Boolean = false,
    val activeType: String = "SEARCH", // "SEARCH" or "MAPS"
    val query: String = "",
    val searchResult: GroundingResult? = null,
    val mapsResult: GroundingResult? = null,
    val errorMessage: String? = null
)

data class MusicUiState(
    val isLoading: Boolean = false,
    val prompt: String = "Musik gamelan misterius Jawa berpadu gesekan rebab malam hari untuk suasana investigasi keraton",
    val useFullTrack: Boolean = false,
    val currentResult: MusicGenerationResult? = null,
    val isPlaying: Boolean = false,
    val errorMessage: String? = null
)

data class VideoUiState(
    val isLoading: Boolean = false,
    val prompt: String = "Rekonstruksi adegan dramatis pelaku menyelinap di paviliun kuno membawa gulungan pusaka",
    val selectedBitmap: Bitmap? = null,
    val aspectRatio: String = "16:9", // "16:9" or "9:16"
    val result: VideoGenerationResult? = null,
    val errorMessage: String? = null
)

data class CloudAuthUiState(
    val isLoading: Boolean = false,
    val userProfile: UserCloudProfile? = null,
    val isSyncing: Boolean = false,
    val lastSyncStatus: String? = null,
    val errorMessage: String? = null
)

data class GameUiState(
    val currentScreen: Screen = Screen.HOME,
    val userProgress: UserProgressEntity = UserProgressEntity(),
    val currentRank: DetectiveRank = GameDataProvider.ranks.first(),
    val cases: List<CaseData> = emptyList(),
    val dictionaryEntries: List<DictionaryEntry> = GameDataProvider.initialDictionary,
    val unlockedWordIds: Set<String> = emptySet(),
    val achievements: List<Achievement> = GameDataProvider.achievements,
    val investigation: InvestigationState = InvestigationState(),
    val storyline: StorylineUiState = StorylineUiState(),
    val grounding: GroundingUiState = GroundingUiState(),
    val music: MusicUiState = MusicUiState(),
    val video: VideoUiState = VideoUiState(),
    val cloud: CloudAuthUiState = CloudAuthUiState(),
    val toastMessage: String? = null
)

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GameRepository
    private val geminiService = GeminiService(application)
    private val firebaseService = FirebaseService(application)

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null
    private var mediaPlayer: MediaPlayer? = null

    init {
        val database = AppDatabase.getDatabase(application)
        repository = GameRepository(database.gameDao())

        // Initial check for Firebase Auth state
        val currentFirebaseUser = firebaseService.getCurrentUser()
        _uiState.update { it.copy(cloud = it.cloud.copy(userProfile = currentFirebaseUser)) }

        // Combine DB flows with case list
        viewModelScope.launch {
            combine(
                repository.getUserProgress(),
                repository.getAllCaseProgress(),
                repository.getUnlockedWordIds()
            ) { userProg, caseProgressList, unlockedWords ->
                val progressMap = caseProgressList.associateBy { it.caseId }
                val initialCases = GameDataProvider.getInitialCases()

                val mergedCases = initialCases.mapIndexed { index, baseCase ->
                    val dbProg = progressMap[baseCase.id]
                    val isFirst = index == 0
                    val prevCompleted = if (index > 0) {
                        progressMap[initialCases[index - 1].id]?.isCompleted == true
                    } else false

                    val unlocked = isFirst || prevCompleted || (dbProg?.isUnlocked == true) || (userProg.xp >= baseCase.unlockXpRequired)
                    val completed = dbProg?.isCompleted == true
                    val stars = dbProg?.starsEarned ?: 0
                    val bestScore = dbProg?.bestScore ?: 0

                    baseCase.copy(
                        isUnlocked = unlocked,
                        isCompleted = completed,
                        starsEarned = stars,
                        bestScore = bestScore
                    )
                }

                val rank = repository.getDetectiveRank(userProg.xp)
                val unlockedSet = unlockedWords.toSet()

                val updatedAchievements = GameDataProvider.achievements.map { ach ->
                    val isUnlocked = when (ach.id) {
                        "ach_first_solve" -> userProg.casesSolvedCount >= 1
                        "ach_master_baku" -> unlockedSet.size >= 4
                        "ach_antonim_ace" -> unlockedSet.size >= 3
                        "ach_sinonim_pro" -> unlockedSet.size >= 3
                        "ach_three_stars" -> mergedCases.any { it.starsEarned >= 3 }
                        "ach_rank_up" -> userProg.xp >= 700
                        else -> false
                    }
                    ach.copy(isUnlocked = isUnlocked)
                }

                _uiState.update { current ->
                    current.copy(
                        userProgress = userProg,
                        currentRank = rank,
                        cases = mergedCases,
                        unlockedWordIds = unlockedSet,
                        achievements = updatedAchievements
                    )
                }
            }.collect {}
        }
    }

    fun navigateTo(screen: Screen) {
        _uiState.update { it.copy(currentScreen = screen) }
    }

    fun startLinearStoryline(fromBeginning: Boolean = false) {
        val allCases = _uiState.value.cases.ifEmpty { GameDataProvider.getInitialCases() }
        val savedChapter = if (fromBeginning) 0 else _uiState.value.userProgress.currentStoryChapter.coerceIn(0, allCases.size - 1)
        val caseForChapter = allCases.getOrNull(savedChapter) ?: allCases.first()
        val savedCharId = _uiState.value.userProgress.selectedCharacterId
        val initialChar = GameDataProvider.characters.find { it.id == savedCharId } ?: GameDataProvider.characters.first()
        val initialStage = if (fromBeginning) StorylineStage.CHARACTER_SELECTION else StorylineStage.CHAPTER_PROLOGUE
        val hearts = if (initialChar.id == "char_panji") 4 else 3

        _uiState.update { current ->
            current.copy(
                currentScreen = Screen.LINEAR_STORYLINE,
                storyline = StorylineUiState(
                    stage = initialStage,
                    selectedCharacter = initialChar,
                    currentChapterIndex = savedChapter,
                    activeCase = caseForChapter,
                    currentQuestionIndex = 0,
                    answeredQuestions = emptyMap(),
                    unlockedClueIds = emptySet(),
                    interrogatedSuspectIds = emptySet(),
                    eliminatedSuspectIds = emptySet(),
                    selectedSuspectForInterrogation = null,
                    accusedSuspectId = null,
                    selectedContradictionClueId = null,
                    isConfrontationSuccess = false,
                    confrontationFeedback = null,
                    heartsRemaining = hearts,
                    maxHearts = hearts,
                    chapterScore = 0,
                    currentAnswerResult = null
                )
            )
        }
    }

    fun selectStoryCharacter(character: DetectiveCharacter) {
        val hearts = if (character.id == "char_panji") 4 else 3
        _uiState.update { current ->
            current.copy(
                storyline = current.storyline.copy(
                    selectedCharacter = character,
                    heartsRemaining = hearts,
                    maxHearts = hearts
                )
            )
        }
        viewModelScope.launch {
            repository.saveUserProgress(
                _uiState.value.userProgress.copy(selectedCharacterId = character.id)
            )
        }
    }

    fun proceedToStoryStage(stage: StorylineStage) {
        _uiState.update { current ->
            current.copy(
                storyline = current.storyline.copy(
                    stage = stage,
                    currentAnswerResult = null,
                    confrontationFeedback = null
                )
            )
        }
    }

    fun answerStoryQuestion(questionId: String, selectedIndex: Int) {
        val storyline = _uiState.value.storyline
        val activeCase = storyline.activeCase
        val question = activeCase.challenges.find { it.id == questionId } ?: return

        val isCorrect = selectedIndex == question.correctIndex
        val bonus = if (isCorrect && storyline.selectedCharacter.id == "char_arya") 50 else 0
        val earnedScore = if (isCorrect) (question.points + bonus) else 0

        val newUnlockedClues = if (isCorrect) {
            storyline.unlockedClueIds + question.rewardClueId
        } else {
            storyline.unlockedClueIds
        }

        val newHearts = if (!isCorrect) {
            (storyline.heartsRemaining - 1).coerceAtLeast(0)
        } else {
            storyline.heartsRemaining
        }

        val matchingClue = activeCase.clues.find { it.id == question.rewardClueId }

        val answerResult = AnswerResult(
            isCorrect = isCorrect,
            selectedIndex = selectedIndex,
            explanation = question.explanation,
            unlockedClueTitle = if (isCorrect) matchingClue?.title else null
        )

        _uiState.update { current ->
            current.copy(
                storyline = current.storyline.copy(
                    answeredQuestions = current.storyline.answeredQuestions + (questionId to selectedIndex),
                    unlockedClueIds = newUnlockedClues,
                    heartsRemaining = newHearts,
                    chapterScore = current.storyline.chapterScore + earnedScore,
                    currentAnswerResult = answerResult
                )
            )
        }
    }

    fun nextStoryQuestion() {
        _uiState.update { current ->
            val nextIdx = current.storyline.currentQuestionIndex + 1
            current.copy(
                storyline = current.storyline.copy(
                    currentQuestionIndex = nextIdx,
                    currentAnswerResult = null
                )
            )
        }
    }

    fun selectSuspectForStoryInterrogation(suspect: Suspect?) {
        _uiState.update { current ->
            val interrogated = if (suspect != null) {
                current.storyline.interrogatedSuspectIds + suspect.id
            } else {
                current.storyline.interrogatedSuspectIds
            }
            current.copy(
                storyline = current.storyline.copy(
                    selectedSuspectForInterrogation = suspect,
                    interrogatedSuspectIds = interrogated
                )
            )
        }
    }

    fun eliminateSuspectInStory(suspectId: String) {
        _uiState.update { current ->
            val updated = current.storyline.eliminatedSuspectIds + suspectId
            current.copy(
                storyline = current.storyline.copy(
                    eliminatedSuspectIds = updated,
                    selectedSuspectForInterrogation = null
                )
            )
        }
    }

    fun setAccusedSuspectInStory(suspectId: String) {
        _uiState.update { current ->
            current.copy(
                storyline = current.storyline.copy(
                    accusedSuspectId = suspectId,
                    confrontationFeedback = null
                )
            )
        }
    }

    fun setContradictionClueInStory(clueId: String) {
        _uiState.update { current ->
            current.copy(
                storyline = current.storyline.copy(
                    selectedContradictionClueId = clueId,
                    confrontationFeedback = null
                )
            )
        }
    }

    fun submitStoryConfrontation() {
        val state = _uiState.value.storyline
        val accusedId = state.accusedSuspectId

        if (accusedId == null) {
            _uiState.update { it.copy(storyline = it.storyline.copy(confrontationFeedback = "Pilih salah satu tersangka terlebih dahulu!")) }
            return
        }

        val suspect = state.activeCase.suspects.find { it.id == accusedId } ?: return

        if (suspect.isCulprit) {
            com.example.data.local.AudioEffects.playSkakmat()
            _uiState.update { current ->
                current.copy(
                    storyline = current.storyline.copy(
                        isConfrontationSuccess = true,
                        confrontationFeedback = "DEDUKSI TEPAT! ${suspect.name} terbukti bersalah dan mengakui seluruh perbuatannya!",
                        chapterScore = current.storyline.chapterScore + 500
                    )
                )
            }
        } else {
            com.example.data.local.AudioEffects.playWrong()
            val newHearts = (state.heartsRemaining - 1).coerceAtLeast(0)
            _uiState.update { current ->
                current.copy(
                    storyline = current.storyline.copy(
                        heartsRemaining = newHearts,
                        confrontationFeedback = "Tuduhan salah! ${suspect.name} memiliki alibi yang kuat. Sisa kesempatan berkurang!"
                    )
                )
            }
        }
    }

    fun completeStoryChapterAndAdvance() {
        val state = _uiState.value.storyline
        val completedCase = state.activeCase
        val earnedScore = state.chapterScore.coerceAtLeast(600)
        val stars = if (state.heartsRemaining >= 3) 3 else if (state.heartsRemaining >= 2) 2 else 1

        val allCases = _uiState.value.cases.ifEmpty { GameDataProvider.getInitialCases() }
        val nextChapterIdx = state.currentChapterIndex + 1

        viewModelScope.launch {
            val xpGain = 350 + (stars * 50)
            repository.saveCaseCompleted(
                caseId = completedCase.id,
                score = earnedScore,
                timeSeconds = 120,
                earnedStars = stars,
                xpGained = xpGain
            )

            val updatedUser = _uiState.value.userProgress.copy(
                xp = _uiState.value.userProgress.xp + xpGain,
                totalScore = _uiState.value.userProgress.totalScore + earnedScore,
                casesSolvedCount = _uiState.value.userProgress.casesSolvedCount + 1,
                currentStoryChapter = nextChapterIdx.coerceAtMost(allCases.size - 1)
            )
            repository.saveUserProgress(updatedUser)

            if (nextChapterIdx < allCases.size) {
                val nextCase = allCases[nextChapterIdx]
                _uiState.update { current ->
                    current.copy(
                        storyline = StorylineUiState(
                            stage = StorylineStage.CHAPTER_PROLOGUE,
                            selectedCharacter = current.storyline.selectedCharacter,
                            currentChapterIndex = nextChapterIdx,
                            activeCase = nextCase,
                            currentQuestionIndex = 0,
                            answeredQuestions = emptyMap(),
                            unlockedClueIds = emptySet(),
                            interrogatedSuspectIds = emptySet(),
                            eliminatedSuspectIds = emptySet(),
                            selectedSuspectForInterrogation = null,
                            accusedSuspectId = null,
                            selectedContradictionClueId = null,
                            isConfrontationSuccess = false,
                            confrontationFeedback = null,
                            heartsRemaining = current.storyline.maxHearts,
                            maxHearts = current.storyline.maxHearts,
                            chapterScore = 0,
                            currentAnswerResult = null
                        ),
                        toastMessage = "Bab ${nextChapterIdx + 1} Terbuka: ${nextCase.title}!"
                    )
                }
            } else {
                _uiState.update { current ->
                    current.copy(
                        currentScreen = Screen.HOME,
                        toastMessage = "Selamat! Seluruh Alur Cerita Nusantara Telah Berhasil Dipecahkan!"
                    )
                }
            }
        }
    }

    fun toggleStorylineEnglish() {
        _uiState.update { current ->
            current.copy(
                storyline = current.storyline.copy(
                    isEnglishEnabled = !current.storyline.isEnglishEnabled
                )
            )
        }
    }

    fun requestStoryAiTranslation(textToTranslate: String, context: String) {
        viewModelScope.launch {
            _uiState.update { current ->
                current.copy(
                    storyline = current.storyline.copy(
                        isTranslatingWithAi = true,
                        aiTranslationResult = null
                    )
                )
            }
            val result = geminiService.translateIndonesianToEnglish(textToTranslate, context)
            _uiState.update { current ->
                current.copy(
                    storyline = current.storyline.copy(
                        isTranslatingWithAi = false,
                        aiTranslationResult = result.getOrNull() ?: "AI Translation unavailable. Using standard English annotations."
                    )
                )
            }
        }
    }

    fun clearStoryAiTranslation() {
        _uiState.update { current ->
            current.copy(
                storyline = current.storyline.copy(
                    aiTranslationResult = null
                )
            )
        }
    }

    fun startCase(caseId: String) {
        val selectedCase = _uiState.value.cases.find { it.id == caseId } ?: return
        if (!selectedCase.isUnlocked) return

        timerJob?.cancel()
        _uiState.update { current ->
            current.copy(
                currentScreen = Screen.INVESTIGATION,
                investigation = InvestigationState(
                    activeCase = selectedCase,
                    revealedClueIds = emptySet(),
                    eliminatedSuspectIds = emptySet(),
                    selectedSuspect = null,
                    activeChallenge = null,
                    isChallengeOpen = false,
                    heartsRemaining = 3,
                    currentScore = 0,
                    elapsedSeconds = 0,
                    outcome = null,
                    accusedSuspect = null,
                    lastAnswerResult = null,
                    isAccusationOpen = false,
                    hintsCount = current.userProgress.hintsAvailable
                )
            )
        }

        startTimer()
    }

    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000L)
                _uiState.update { current ->
                    if (current.investigation.outcome == null && current.investigation.activeCase != null) {
                        current.copy(
                            investigation = current.investigation.copy(
                                elapsedSeconds = current.investigation.elapsedSeconds + 1
                            )
                        )
                    } else {
                        current
                    }
                }
            }
        }
    }

    fun openChallenge(challenge: ChallengeQuestion) {
        _uiState.update { current ->
            current.copy(
                investigation = current.investigation.copy(
                    activeChallenge = challenge,
                    isChallengeOpen = true,
                    lastAnswerResult = null
                )
            )
        }
    }

    fun closeChallengeDialog() {
        _uiState.update { current ->
            current.copy(
                investigation = current.investigation.copy(
                    isChallengeOpen = false,
                    activeChallenge = null,
                    lastAnswerResult = null
                )
            )
        }
    }

    fun submitChallengeAnswer(selectedIndex: Int) {
        val currentCase = _uiState.value.investigation.activeCase ?: return
        val challenge = _uiState.value.investigation.activeChallenge ?: return

        val isCorrect = selectedIndex == challenge.correctIndex

        if (isCorrect) {
            val rewardClue = currentCase.clues.find { it.id == challenge.rewardClueId }
            val newClues = _uiState.value.investigation.revealedClueIds + challenge.rewardClueId
            val newScore = _uiState.value.investigation.currentScore + challenge.points

            viewModelScope.launch {
                repository.unlockWord("word_${challenge.id}")
            }

            _uiState.update { state ->
                state.copy(
                    investigation = state.investigation.copy(
                        revealedClueIds = newClues,
                        currentScore = newScore,
                        lastAnswerResult = AnswerResult(
                            isCorrect = true,
                            selectedIndex = selectedIndex,
                            explanation = challenge.explanation,
                            unlockedClueTitle = rewardClue?.title
                        )
                    )
                )
            }
        } else {
            val updatedHearts = _uiState.value.investigation.heartsRemaining - 1
            val outcome = if (updatedHearts <= 0) CaseOutcomeType.DEFEAT else null

            _uiState.update { state ->
                state.copy(
                    investigation = state.investigation.copy(
                        heartsRemaining = maxOf(0, updatedHearts),
                        outcome = outcome,
                        lastAnswerResult = AnswerResult(
                            isCorrect = false,
                            selectedIndex = selectedIndex,
                            explanation = challenge.explanation
                        )
                    )
                )
            }
        }
    }

    fun selectSuspect(suspect: Suspect?) {
        _uiState.update { current ->
            current.copy(
                investigation = current.investigation.copy(
                    selectedSuspect = suspect
                )
            )
        }
    }

    fun toggleEliminateSuspect(suspectId: String) {
        _uiState.update { current ->
            val set = current.investigation.eliminatedSuspectIds.toMutableSet()
            if (set.contains(suspectId)) {
                set.remove(suspectId)
            } else {
                set.add(suspectId)
            }
            current.copy(
                investigation = current.investigation.copy(
                    eliminatedSuspectIds = set
                )
            )
        }
    }

    fun openAccusationDialog(open: Boolean) {
        _uiState.update { current ->
            current.copy(
                investigation = current.investigation.copy(
                    isAccusationOpen = open
                )
            )
        }
    }

    fun accuseSuspect(suspect: Suspect) {
        val currentCase = _uiState.value.investigation.activeCase ?: return
        val isCulprit = suspect.isCulprit

        if (isCulprit) {
            timerJob?.cancel()
            val earnedStars = when {
                _uiState.value.investigation.heartsRemaining == 3 -> 3
                _uiState.value.investigation.heartsRemaining >= 2 -> 2
                else -> 1
            }
            val finalScore = _uiState.value.investigation.currentScore + 250 + (earnedStars * 50)
            val xpGained = 200 + (_uiState.value.investigation.heartsRemaining * 50)

            viewModelScope.launch {
                val currentProg = _uiState.value.userProgress
                val newTotalXp = currentProg.xp + xpGained
                val newTotalScore = currentProg.totalScore + finalScore
                val newSolvedCount = currentProg.casesSolvedCount + 1

                val updatedProg = currentProg.copy(
                    xp = newTotalXp,
                    totalScore = newTotalScore,
                    casesSolvedCount = newSolvedCount
                )
                repository.saveUserProgress(updatedProg)

                repository.saveCaseCompleted(
                    caseId = currentCase.id,
                    score = finalScore,
                    timeSeconds = _uiState.value.investigation.elapsedSeconds,
                    earnedStars = earnedStars,
                    xpGained = xpGained
                )

                val cases = _uiState.value.cases
                val currentIndex = cases.indexOfFirst { it.id == currentCase.id }
                if (currentIndex >= 0 && currentIndex + 1 < cases.size) {
                    val nextCase = cases[currentIndex + 1]
                    repository.unlockCase(nextCase.id)
                }

                // Auto sync with Firestore if signed in
                if (_uiState.value.cloud.userProfile != null) {
                    firebaseService.saveProgressToCloud(updatedProg, emptyList())
                }
            }

            _uiState.update { current ->
                current.copy(
                    investigation = current.investigation.copy(
                        outcome = CaseOutcomeType.VICTORY,
                        accusedSuspect = suspect,
                        currentScore = finalScore,
                        isAccusationOpen = false
                    )
                )
            }
        } else {
            val updatedHearts = _uiState.value.investigation.heartsRemaining - 1
            val outcome = if (updatedHearts <= 0) CaseOutcomeType.DEFEAT else null

            _uiState.update { current ->
                current.copy(
                    investigation = current.investigation.copy(
                        heartsRemaining = maxOf(0, updatedHearts),
                        outcome = outcome,
                        isAccusationOpen = false,
                        selectedSuspect = suspect
                    ),
                    toastMessage = "Tuduhan salah! ${suspect.name} bukan pelakunya. Teliti lagi petunjuk bukti!"
                )
            }
        }
    }

    fun useHint() {
        val currentHints = _uiState.value.investigation.hintsCount
        if (currentHints <= 0) return

        val currentCase = _uiState.value.investigation.activeCase ?: return
        val revealed = _uiState.value.investigation.revealedClueIds

        val unrevealed = currentCase.clues.firstOrNull { !revealed.contains(it.id) }
        if (unrevealed != null) {
            _uiState.update { current ->
                current.copy(
                    investigation = current.investigation.copy(
                        revealedClueIds = revealed + unrevealed.id,
                        hintsCount = currentHints - 1
                    ),
                    toastMessage = "Petunjuk rahasia terbuka: ${unrevealed.title}!"
                )
            }
        } else {
            val innocentToEliminate = currentCase.suspects.firstOrNull {
                !it.isCulprit && !_uiState.value.investigation.eliminatedSuspectIds.contains(it.id)
            }
            if (innocentToEliminate != null) {
                _uiState.update { current ->
                    current.copy(
                        investigation = current.investigation.copy(
                            eliminatedSuspectIds = current.investigation.eliminatedSuspectIds + innocentToEliminate.id,
                            hintsCount = currentHints - 1
                        ),
                        toastMessage = "Penyelidikan menegaskan: ${innocentToEliminate.name} tidak bersalah!"
                    )
                }
            }
        }
    }

    // --- 1. Google Search Grounding with gemini-3.5-flash ---
    fun runSearchGrounding(query: String) {
        if (query.isBlank()) return
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    grounding = it.grounding.copy(
                        isLoading = true,
                        activeType = "SEARCH",
                        query = query,
                        errorMessage = null
                    )
                )
            }
            val result = geminiService.searchGrounding(query)
            result.fold(
                onSuccess = { res ->
                    _uiState.update {
                        it.copy(grounding = it.grounding.copy(isLoading = false, searchResult = res))
                    }
                },
                onFailure = { err ->
                    _uiState.update {
                        it.copy(grounding = it.grounding.copy(isLoading = false, errorMessage = err.message))
                    }
                }
            )
        }
    }

    // --- 2. Google Maps Grounding with gemini-3.5-flash ---
    fun runMapsGrounding(locationQuery: String) {
        if (locationQuery.isBlank()) return
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    grounding = it.grounding.copy(
                        isLoading = true,
                        activeType = "MAPS",
                        query = locationQuery,
                        errorMessage = null
                    )
                )
            }
            val result = geminiService.mapsGrounding(locationQuery)
            result.fold(
                onSuccess = { res ->
                    _uiState.update {
                        it.copy(grounding = it.grounding.copy(isLoading = false, mapsResult = res))
                    }
                },
                onFailure = { err ->
                    _uiState.update {
                        it.copy(grounding = it.grounding.copy(isLoading = false, errorMessage = err.message))
                    }
                }
            )
        }
    }

    // --- 3. Music Generation with lyria-3-clip-preview / lyria-3-pro-preview ---
    fun generateMusic(prompt: String, useFullTrack: Boolean) {
        viewModelScope.launch {
            stopAudio()
            _uiState.update {
                it.copy(
                    music = it.music.copy(
                        isLoading = true,
                        prompt = prompt,
                        useFullTrack = useFullTrack,
                        errorMessage = null
                    )
                )
            }
            val result = geminiService.generateMusic(prompt, useFullTrack)
            result.fold(
                onSuccess = { res ->
                    _uiState.update {
                        it.copy(
                            music = it.music.copy(
                                isLoading = false,
                                currentResult = res
                            ),
                            toastMessage = "Musik investigasi Nusantara berhasil dibuat!"
                        )
                    }
                    if (res.audioFilePath != null) {
                        playAudio(res.audioFilePath)
                    }
                },
                onFailure = { err ->
                    _uiState.update {
                        it.copy(
                            music = it.music.copy(
                                isLoading = false,
                                errorMessage = err.message
                            )
                        )
                    }
                }
            )
        }
    }

    fun playAudio(path: String) {
        try {
            stopAudio()
            mediaPlayer = MediaPlayer().apply {
                setDataSource(path)
                prepare()
                start()
                setOnCompletionListener {
                    _uiState.update { it.copy(music = it.music.copy(isPlaying = false)) }
                }
            }
            _uiState.update { it.copy(music = it.music.copy(isPlaying = true)) }
        } catch (e: Exception) {
            _uiState.update { it.copy(toastMessage = "Gagal memutar audio: ${e.message}") }
        }
    }

    fun stopAudio() {
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
            _uiState.update { it.copy(music = it.music.copy(isPlaying = false)) }
        } catch (e: Exception) {
            // ignore
        }
    }

    // --- 4. Video Generation with veo-3.1-fast-generate-preview ---
    fun setVideoBitmap(bitmap: Bitmap?) {
        _uiState.update { it.copy(video = it.video.copy(selectedBitmap = bitmap)) }
    }

    fun generateVideo(prompt: String, aspectRatio: String) {
        val bitmap = _uiState.value.video.selectedBitmap
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    video = it.video.copy(
                        isLoading = true,
                        prompt = prompt,
                        aspectRatio = aspectRatio,
                        errorMessage = null
                    )
                )
            }
            val result = geminiService.generateVideo(prompt, bitmap, aspectRatio)
            result.fold(
                onSuccess = { res ->
                    _uiState.update {
                        it.copy(
                            video = it.video.copy(
                                isLoading = false,
                                result = res
                            ),
                            toastMessage = "Rekonstruksi video Veo 3.1 berhasil dikirim!"
                        )
                    }
                },
                onFailure = { err ->
                    _uiState.update {
                        it.copy(
                            video = it.video.copy(
                                isLoading = false,
                                errorMessage = err.message
                            )
                        )
                    }
                }
            )
        }
    }

    // --- 5. Firebase Auth & Cloud Firestore Sync ---
    fun signInWithFirebase() {
        viewModelScope.launch {
            _uiState.update { it.copy(cloud = it.cloud.copy(isLoading = true, errorMessage = null)) }
            val result = firebaseService.signInAnonymously()
            result.fold(
                onSuccess = { userProfile ->
                    _uiState.update {
                        it.copy(
                            cloud = it.cloud.copy(
                                isLoading = false,
                                userProfile = userProfile,
                                lastSyncStatus = "Berhasil masuk sebagai ${userProfile.displayName}"
                            ),
                            toastMessage = "Terhubung dengan Firebase Auth!"
                        )
                    }
                    syncProgressToCloud()
                },
                onFailure = { err ->
                    _uiState.update {
                        it.copy(
                            cloud = it.cloud.copy(
                                isLoading = false,
                                errorMessage = "Autentikasi Firebase: ${err.message}"
                            )
                        )
                    }
                }
            )
        }
    }

    fun syncProgressToCloud() {
        viewModelScope.launch {
            _uiState.update { it.copy(cloud = it.cloud.copy(isSyncing = true, errorMessage = null)) }
            val currentProg = _uiState.value.userProgress
            val result = firebaseService.saveProgressToCloud(currentProg, emptyList())
            result.fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(
                            cloud = it.cloud.copy(
                                isSyncing = false,
                                lastSyncStatus = "Progres tersinkronisasi ke Cloud Firestore pada ${java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())}"
                            ),
                            toastMessage = "Data tersimpan ke Cloud Firestore!"
                        )
                    }
                },
                onFailure = { err ->
                    _uiState.update {
                        it.copy(
                            cloud = it.cloud.copy(
                                isSyncing = false,
                                errorMessage = "Gagal sinkron Firestore: ${err.message}"
                            )
                        )
                    }
                }
            )
        }
    }

    fun restoreProgressFromCloud() {
        viewModelScope.launch {
            _uiState.update { it.copy(cloud = it.cloud.copy(isSyncing = true, errorMessage = null)) }
            val result = firebaseService.loadProgressFromCloud()
            result.fold(
                onSuccess = { data ->
                    val xp = (data["xp"] as? Number)?.toInt() ?: 0
                    val score = (data["totalScore"] as? Number)?.toInt() ?: 0
                    val solved = (data["casesSolvedCount"] as? Number)?.toInt() ?: 0
                    val rankLevel = (data["currentRankLevel"] as? Number)?.toInt() ?: 1

                    val restored = _uiState.value.userProgress.copy(
                        xp = xp,
                        totalScore = score,
                        casesSolvedCount = solved,
                        currentRankLevel = rankLevel
                    )
                    repository.saveUserProgress(restored)

                    _uiState.update {
                        it.copy(
                            cloud = it.cloud.copy(
                                isSyncing = false,
                                lastSyncStatus = "Data detektif berhasil dipulihkan dari Firestore!"
                            ),
                            toastMessage = "Data dipulihkan dari Cloud Firestore ($xp XP)!"
                        )
                    }
                },
                onFailure = { err ->
                    _uiState.update {
                        it.copy(
                            cloud = it.cloud.copy(
                                isSyncing = false,
                                errorMessage = "Pemulihan Cloud: ${err.message}"
                            )
                        )
                    }
                }
            )
        }
    }

    fun signOutFirebase() {
        viewModelScope.launch {
            firebaseService.signOut()
            _uiState.update {
                it.copy(
                    cloud = it.cloud.copy(
                        userProfile = null,
                        lastSyncStatus = "Keluar dari akun Firebase."
                    ),
                    toastMessage = "Akun telah keluar."
                )
            }
        }
    }

    fun clearToast() {
        _uiState.update { it.copy(toastMessage = null) }
    }

    fun restartCurrentCase() {
        val currentCaseId = _uiState.value.investigation.activeCase?.id ?: return
        startCase(currentCaseId)
    }

    fun resetAllProgress() {
        viewModelScope.launch {
            repository.resetAllProgress()
            navigateTo(Screen.HOME)
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopAudio()
    }
}
