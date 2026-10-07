package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.EnglishTranslationProvider
import com.example.data.GameDataProvider
import com.example.data.local.AudioEffects
import com.example.model.ChallengeQuestion
import com.example.model.DetectiveCharacter
import com.example.model.GameDifficulty
import com.example.model.StorylineStage
import com.example.model.Suspect
import com.example.ui.components.ParchmentCard
import com.example.ui.components.WaxSealStamp
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.AntiqueGold
import com.example.ui.theme.CardBorder
import com.example.ui.theme.CorkBoard
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.GoldBright
import com.example.ui.theme.InkBrown
import com.example.ui.theme.InkDark
import com.example.ui.theme.InkMuted
import com.example.ui.theme.ParchmentBase
import com.example.ui.theme.ParchmentBorder
import com.example.ui.theme.ParchmentLight
import com.example.ui.theme.StampRed
import com.example.ui.theme.WoodBoard
import com.example.ui.theme.WoodDark
import com.example.viewmodel.GameViewModel
import com.example.viewmodel.StorylineUiState
import kotlin.math.roundToInt

@Composable
fun LinearStorylineScreen(
    storyline: StorylineUiState,
    viewModel: GameViewModel,
    onBackToHome: () -> Unit
) {
    val activeCase = storyline.activeCase
    val currentStage = storyline.stage
    val selectedCharacter = storyline.selectedCharacter

    val gameUiState by viewModel.uiState.collectAsState()
    val difficulty = gameUiState.difficulty
    var soundEnabled by remember { mutableStateOf(AudioEffects.isSoundEnabled) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WoodDark)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // TOP BAR & STEPPER HEADER
            StorylineTopBar(
                stage = currentStage,
                chapterNumber = storyline.currentChapterIndex + 1,
                chapterTitle = if (storyline.isEnglishEnabled) {
                    EnglishTranslationProvider.getCaseTranslation(activeCase.id)?.titleEn ?: activeCase.title
                } else activeCase.title,
                heartsRemaining = storyline.heartsRemaining,
                maxHearts = storyline.maxHearts,
                score = storyline.chapterScore,
                character = selectedCharacter,
                soundEnabled = soundEnabled,
                isBgmEnabled = gameUiState.isBgmEnabled,
                difficulty = difficulty,
                isEnglishEnabled = storyline.isEnglishEnabled,
                onToggleEnglish = {
                    AudioEffects.playClick()
                    viewModel.toggleStorylineEnglish()
                },
                onToggleSound = {
                    soundEnabled = !soundEnabled
                    AudioEffects.isSoundEnabled = soundEnabled
                    if (soundEnabled) AudioEffects.playClick()
                },
                onToggleBgm = {
                    AudioEffects.playClick()
                    viewModel.toggleBgm(!gameUiState.isBgmEnabled)
                },
                onBack = {
                    AudioEffects.playClick()
                    if (currentStage == StorylineStage.CHARACTER_SELECTION) {
                        onBackToHome()
                    } else {
                        val prev = when (currentStage) {
                            StorylineStage.CHARACTER_SELECTION -> StorylineStage.CHARACTER_SELECTION
                            StorylineStage.CHAPTER_PROLOGUE -> StorylineStage.CHARACTER_SELECTION
                            StorylineStage.SOLVE_QUESTIONS -> StorylineStage.CHAPTER_PROLOGUE
                            StorylineStage.EVIDENCE_AND_INTERROGATION -> StorylineStage.SOLVE_QUESTIONS
                            StorylineStage.FINAL_CONFRONTATION -> StorylineStage.EVIDENCE_AND_INTERROGATION
                            StorylineStage.CHAPTER_EPILOGUE -> StorylineStage.FINAL_CONFRONTATION
                        }
                        viewModel.proceedToStoryStage(prev)
                    }
                }
            )

            // STAGE STEPPER BAR
            StageStepperIndicator(
                currentStage = currentStage,
                onStageClick = { targetStage ->
                    if (targetStage.ordinal <= currentStage.ordinal) {
                        AudioEffects.playClick()
                        viewModel.proceedToStoryStage(targetStage)
                    }
                }
            )

            // MAIN STAGE CONTENT
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                when (currentStage) {
                    StorylineStage.CHARACTER_SELECTION -> {
                        CharacterSelectionStage(
                            selectedChar = selectedCharacter,
                            onSelectChar = {
                                AudioEffects.playClick()
                                viewModel.selectStoryCharacter(it)
                            },
                            onConfirm = {
                                AudioEffects.playClueUnlocked()
                                viewModel.proceedToStoryStage(StorylineStage.CHAPTER_PROLOGUE)
                            }
                        )
                    }

                    StorylineStage.CHAPTER_PROLOGUE -> {
                        ChapterPrologueStage(
                            storyline = storyline,
                            viewModel = viewModel,
                            onStartQuestions = {
                                AudioEffects.playClueUnlocked()
                                viewModel.proceedToStoryStage(StorylineStage.SOLVE_QUESTIONS)
                            }
                        )
                    }

                    StorylineStage.SOLVE_QUESTIONS -> {
                        SolveQuestionsStage(
                            storyline = storyline,
                            viewModel = viewModel,
                            onAnswer = { qId, idx ->
                                viewModel.answerStoryQuestion(qId, idx)
                            },
                            onNextQuestion = {
                                AudioEffects.playClick()
                                viewModel.nextStoryQuestion()
                            },
                            onProceedToEvidence = {
                                AudioEffects.playClueUnlocked()
                                viewModel.proceedToStoryStage(StorylineStage.EVIDENCE_AND_INTERROGATION)
                            }
                        )
                    }

                    StorylineStage.EVIDENCE_AND_INTERROGATION -> {
                        EvidenceAndInterrogationStage(
                            storyline = storyline,
                            viewModel = viewModel,
                            onProceedToConfrontation = {
                                AudioEffects.playClueUnlocked()
                                viewModel.proceedToStoryStage(StorylineStage.FINAL_CONFRONTATION)
                            }
                        )
                    }

                    StorylineStage.FINAL_CONFRONTATION -> {
                        FinalConfrontationStage(
                            storyline = storyline,
                            viewModel = viewModel,
                            onProceedToEpilogue = {
                                AudioEffects.playVictoryFanfare()
                                viewModel.proceedToStoryStage(StorylineStage.CHAPTER_EPILOGUE)
                            }
                        )
                    }

                    StorylineStage.CHAPTER_EPILOGUE -> {
                        ChapterEpilogueStage(
                            storyline = storyline,
                            onNextChapter = {
                                AudioEffects.playClueUnlocked()
                                viewModel.completeStoryChapterAndAdvance()
                            },
                            onHome = {
                                AudioEffects.playClick()
                                onBackToHome()
                            }
                        )
                    }
                }
            }
        }
    }
}

// ==========================================
// 1. TOP BAR & STEPPER
// ==========================================

@Composable
fun StorylineTopBar(
    stage: StorylineStage,
    chapterNumber: Int,
    chapterTitle: String,
    heartsRemaining: Int,
    maxHearts: Int,
    score: Int,
    character: DetectiveCharacter,
    soundEnabled: Boolean,
    isBgmEnabled: Boolean = true,
    difficulty: GameDifficulty = GameDifficulty.SEDANG,
    isEnglishEnabled: Boolean = false,
    onToggleEnglish: () -> Unit = {},
    onToggleSound: () -> Unit,
    onToggleBgm: () -> Unit = {},
    onBack: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = WoodBoard,
        shadowElevation = 4.dp,
        border = BorderStroke(1.dp, CardBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Row 1: Back Button + Chapter Title + Difficulty Badge + Score Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.size(32.dp).testTag("storyline_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali",
                        tint = AntiqueGold,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Text(
                    text = "BAB $chapterNumber: $chapterTitle",
                    color = GoldBright,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = Color(difficulty.colorHex)
                ) {
                    Text(
                        text = difficulty.badge,
                        color = InkDark,
                        fontSize = 8.5.sp,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = CorkBoard,
                    border = BorderStroke(1.dp, AntiqueGold.copy(alpha = 0.4f))
                ) {
                    Text(
                        text = "$score PTS",
                        color = AntiqueGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            // Row 2: Stage Progress Subtitle + Action Controls (Language, BGM, Sound, Hearts)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (isEnglishEnabled) "Stage ${stage.stageNumber}/6: ${stage.title}" else "Tahap ${stage.stageNumber}/6: ${stage.title}",
                    color = ParchmentLight.copy(alpha = 0.85f),
                    fontSize = 10.5.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Language Switcher (ID / EN)
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = if (isEnglishEnabled) EmeraldGreen.copy(alpha = 0.25f) else CorkBoard,
                        border = BorderStroke(1.dp, if (isEnglishEnabled) EmeraldGreen else AntiqueGold.copy(alpha = 0.5f)),
                        modifier = Modifier
                            .clickable { onToggleEnglish() }
                            .testTag("storyline_language_toggle")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Translate,
                                contentDescription = "Terjemah Bahasa",
                                tint = if (isEnglishEnabled) EmeraldGreen else AntiqueGold,
                                modifier = Modifier.size(11.dp)
                            )
                            Text(
                                text = if (isEnglishEnabled) "🇬🇧 EN" else "🇮🇩 ID",
                                color = if (isEnglishEnabled) EmeraldGreen else ParchmentLight,
                                fontWeight = FontWeight.Bold,
                                fontSize = 9.5.sp
                            )
                        }
                    }

                    // BGM Music Toggle
                    IconButton(
                        onClick = onToggleBgm,
                        modifier = Modifier.size(26.dp).testTag("storyline_bgm_toggle")
                    ) {
                        Icon(
                            imageVector = Icons.Default.MusicNote,
                            contentDescription = "Musik Backsound",
                            tint = if (isBgmEnabled) GoldBright else Color.Gray,
                            modifier = Modifier.size(15.dp)
                        )
                    }

                    // Sound Effects toggle
                    IconButton(
                        onClick = onToggleSound,
                        modifier = Modifier.size(26.dp).testTag("storyline_sound_toggle")
                    ) {
                        Icon(
                            imageVector = if (soundEnabled) Icons.Default.VolumeUp else Icons.Default.VolumeOff,
                            contentDescription = "Audio Suara Game",
                            tint = if (soundEnabled) AntiqueGold else Color.Gray,
                            modifier = Modifier.size(15.dp)
                        )
                    }

                    // Hearts
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(1.dp)
                    ) {
                        repeat(maxHearts) { index ->
                            val isAlive = index < heartsRemaining
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Hati Kesempatan",
                                tint = if (isAlive) CrimsonRed else Color.Gray.copy(alpha = 0.4f),
                                modifier = Modifier.size(13.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StageStepperIndicator(
    currentStage: StorylineStage,
    onStageClick: (StorylineStage) -> Unit
) {
    val stages = StorylineStage.values()

    ScrollableTabRow(
        selectedTabIndex = currentStage.ordinal,
        containerColor = CorkBoard,
        contentColor = AntiqueGold,
        edgePadding = 6.dp,
        divider = {},
        indicator = { tabPositions ->
            if (currentStage.ordinal < tabPositions.size) {
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[currentStage.ordinal]),
                    color = AntiqueGold,
                    height = 2.5.dp
                )
            }
        }
    ) {
        stages.forEach { stage ->
            val isCurrent = stage == currentStage
            val isPassed = stage.ordinal < currentStage.ordinal

            Tab(
                selected = isCurrent,
                onClick = { onStageClick(stage) },
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 6.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                            .background(
                                when {
                                    isCurrent -> AntiqueGold
                                    isPassed -> EmeraldGreen
                                    else -> WoodDark
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isPassed) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = InkDark,
                                modifier = Modifier.size(12.dp)
                            )
                        } else {
                            Text(
                                text = "${stage.stageNumber}",
                                color = if (isCurrent) InkDark else ParchmentLight.copy(alpha = 0.6f),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Text(
                        text = stage.title,
                        color = when {
                            isCurrent -> GoldBright
                            isPassed -> ParchmentLight
                            else -> ParchmentLight.copy(alpha = 0.5f)
                        },
                        fontSize = 11.sp,
                        fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}

// ==========================================
// STAGE 1: PEMILIHAN KARAKTER (PAGE 1)
// ==========================================

@Composable
fun CharacterSelectionStage(
    selectedChar: DetectiveCharacter,
    onSelectChar: (DetectiveCharacter) -> Unit,
    onConfirm: () -> Unit
) {
    val characters = GameDataProvider.characters

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WaxSealStamp(
                    text = "HALAMAN 1: PILIH DETEKTIF",
                    rotation = -1f,
                    stampColor = AntiqueGold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "PILIH IDENTITAS DETEKTIF",
                    color = GoldBright,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Setiap detektif memiliki keahlian dan cara pandang linguistik unik yang akan membantumu mengungkap kebohongan para saksi.",
                    color = ParchmentLight.copy(alpha = 0.8f),
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }

        items(characters) { char ->
            val isSelected = char.id == selectedChar.id

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectChar(char) }
                    .testTag("char_card_${char.id}"),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) WoodBoard else CorkBoard
                ),
                border = BorderStroke(
                    width = if (isSelected) 2.5.dp else 1.dp,
                    color = if (isSelected) GoldBright else CardBorder
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(84.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .border(2.dp, Color(char.themeColorHex), RoundedCornerShape(10.dp))
                        ) {
                            Image(
                                painter = painterResource(id = char.avatarDrawableRes),
                                contentDescription = char.name,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = char.name,
                                    color = GoldBright,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                if (isSelected) {
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = EmeraldGreen
                                    ) {
                                        Text(
                                            text = "TERPILIH",
                                            color = InkDark,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Black,
                                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }

                            Text(
                                text = char.title,
                                color = Color(char.themeColorHex),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Text(
                                text = char.quote,
                                color = ParchmentLight.copy(alpha = 0.9f),
                                fontSize = 10.sp,
                                fontStyle = FontStyle.Italic,
                                lineHeight = 13.sp
                            )
                        }
                    }

                    HorizontalDivider(color = CardBorder.copy(alpha = 0.4f))

                    Text(
                        text = char.bio,
                        color = ParchmentLight.copy(alpha = 0.85f),
                        fontSize = 11.sp,
                        lineHeight = 14.sp
                    )

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(char.themeColorHex).copy(alpha = 0.15f),
                        border = BorderStroke(1.dp, Color(char.themeColorHex).copy(alpha = 0.5f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = Color(char.themeColorHex),
                                modifier = Modifier.size(16.dp)
                            )
                            Column {
                                Text(
                                    text = "Keahlian Khusus: ${char.specialtyPerk}",
                                    color = Color(char.themeColorHex),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                                Text(
                                    text = char.perkDescription,
                                    color = ParchmentLight,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        item {
            Button(
                onClick = onConfirm,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("confirm_character_button")
                    .shadow(6.dp, RoundedCornerShape(10.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = AntiqueGold),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.5.dp, GoldBright)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "PILIH ${selectedChar.name.uppercase()} & MULAI KISAH",
                        color = InkDark,
                        fontWeight = FontWeight.Black,
                        fontSize = 13.sp
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = InkDark,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

// ==========================================
// STAGE 2: PROLOG CERITA & OLAH TKP (PAGE 2)
// ==========================================

data class CrimeSceneHotspot(
    val id: String,
    val title: String,
    val note: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
fun ChapterPrologueStage(
    storyline: StorylineUiState,
    viewModel: GameViewModel,
    onStartQuestions: () -> Unit
) {
    val activeCase = storyline.activeCase
    val character = storyline.selectedCharacter
    val caseEn = EnglishTranslationProvider.getCaseTranslation(activeCase.id)
    var showEnglishPrologue by remember(storyline.isEnglishEnabled) { mutableStateOf(storyline.isEnglishEnabled) }

    // Interactive Crime Scene Spotting State
    val hotspots = remember {
        listOf(
            CrimeSceneHotspot("h1", "Peti Naskah Terbuka", "Gembok tidak rusak sama sekali. Hanya seseorang yang tahu kombinasi sandi kata kuno yang bisa membukanya!", Icons.Default.Search),
            CrimeSceneHotspot("h2", "Lantai Kayu Dekat Jendela", "Ditemukan serpihan dupa wangi eksklusif dan bercak tetesan air hujan malam hari.", Icons.Default.Lightbulb),
            CrimeSceneHotspot("h3", "Meja Pembacaan Naskah", "Secarik kertas bertuliskan daftar padanan kata tergeletak di bawah buku catatan arsip.", Icons.Default.MenuBook),
            CrimeSceneHotspot("h4", "Jejak Langkah Dermaga", "Jejak kaki basah mengarah mundur perlahan menuju perahu yang bersandar tanpa lentera.", Icons.Default.AutoAwesome)
        )
    }

    var inspectedHotspots by remember { mutableStateOf(setOf<String>()) }
    var activeInspectionNote by remember { mutableStateOf<CrimeSceneHotspot?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Case Art Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.5.dp, AntiqueGold)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = activeCase.drawableRes),
                        contentDescription = activeCase.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.5f),
                                        Color.Black.copy(alpha = 0.85f)
                                    )
                                )
                            )
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "${activeCase.region} • ${activeCase.locationName}",
                            color = AntiqueGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = activeCase.title,
                            color = GoldBright,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = activeCase.subtitle,
                            color = ParchmentLight.copy(alpha = 0.8f),
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }

        // INTERACTIVE OLAH TKP SECTION (CRIME SCENE MINI-GAME)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = WoodBoard),
                border = BorderStroke(1.5.dp, GoldBright)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = AntiqueGold,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "OLAH TKP INTERAKTIF (PERIKSA BUKTI)",
                                color = GoldBright,
                                fontWeight = FontWeight.Black,
                                fontSize = 12.sp
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = CorkBoard,
                            border = BorderStroke(1.dp, AntiqueGold)
                        ) {
                            Text(
                                text = "${inspectedHotspots.size}/${hotspots.size} Diperiksa",
                                color = AntiqueGold,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Text(
                        text = "Ketuk 4 titik bukti di bawah dengan kaca pembesar untuk membaca catatan forensik awal:",
                        color = ParchmentLight.copy(alpha = 0.8f),
                        fontSize = 10.sp
                    )

                    // 4 Interactive Hotspot Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        hotspots.take(2).forEach { spot ->
                            val isFound = inspectedHotspots.contains(spot.id)
                            Button(
                                onClick = {
                                    inspectedHotspots = inspectedHotspots + spot.id
                                    activeInspectionNote = spot
                                    AudioEffects.playClueUnlocked()
                                },
                                modifier = Modifier.weight(1f).height(44.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isFound) EmeraldGreen.copy(alpha = 0.25f) else CorkBoard
                                ),
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, if (isFound) EmeraldGreen else AntiqueGold.copy(alpha = 0.5f))
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = spot.icon,
                                        contentDescription = null,
                                        tint = if (isFound) EmeraldGreen else AntiqueGold,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = spot.title.take(12) + "..",
                                        color = if (isFound) EmeraldGreen else ParchmentLight,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        hotspots.drop(2).forEach { spot ->
                            val isFound = inspectedHotspots.contains(spot.id)
                            Button(
                                onClick = {
                                    inspectedHotspots = inspectedHotspots + spot.id
                                    activeInspectionNote = spot
                                    AudioEffects.playClueUnlocked()
                                },
                                modifier = Modifier.weight(1f).height(44.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isFound) EmeraldGreen.copy(alpha = 0.25f) else CorkBoard
                                ),
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, if (isFound) EmeraldGreen else AntiqueGold.copy(alpha = 0.5f))
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = spot.icon,
                                        contentDescription = null,
                                        tint = if (isFound) EmeraldGreen else AntiqueGold,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = spot.title.take(12) + "..",
                                        color = if (isFound) EmeraldGreen else ParchmentLight,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    // Inspection Note Modal Card
                    activeInspectionNote?.let { spot ->
                        ParchmentCard(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "🔍 Hasil Analisis: ${spot.title}",
                                        color = InkDark,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    IconButton(
                                        onClick = { activeInspectionNote = null },
                                        modifier = Modifier.size(20.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Tutup",
                                            tint = InkBrown,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = spot.note,
                                    color = InkBrown,
                                    fontSize = 11.sp,
                                    lineHeight = 15.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Prologue Story Dispatch
        item {
            ParchmentCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        WaxSealStamp(
                            text = "PENUGASAN KASUS RESMI",
                            rotation = 1f,
                            stampColor = StampRed
                        )
                        Text(
                            text = "NO. ${activeCase.numberCode}",
                            color = InkMuted,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    HorizontalDivider(color = ParchmentBorder.copy(alpha = 0.4f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (showEnglishPrologue) "Case Assignment Briefing (English):" else "Laporan Peristiwa di Tempat Kejadian:",
                            color = InkDark,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )

                        // Translator Switch Button
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = if (showEnglishPrologue) EmeraldGreen.copy(alpha = 0.2f) else ParchmentBorder.copy(alpha = 0.35f),
                            border = BorderStroke(1.dp, if (showEnglishPrologue) EmeraldGreen else ParchmentBorder),
                            modifier = Modifier
                                .clickable {
                                    AudioEffects.playClick()
                                    showEnglishPrologue = !showEnglishPrologue
                                }
                                .testTag("prologue_translate_button")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(Icons.Default.Translate, contentDescription = null, tint = InkBrown, modifier = Modifier.size(12.dp))
                                Text(
                                    text = if (showEnglishPrologue) "🇬🇧 English Active" else "🇬🇧 Terjemah EN",
                                    color = InkDark,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Text(
                        text = if (showEnglishPrologue) (caseEn?.storyIntroEn ?: activeCase.storyIntro) else activeCase.storyIntro,
                        color = InkBrown,
                        fontSize = 11.sp,
                        lineHeight = 16.sp
                    )

                    // English Cultural & Educational Notes
                    if (showEnglishPrologue && caseEn != null) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(6.dp),
                            color = CorkBoard.copy(alpha = 0.12f),
                            border = BorderStroke(1.dp, ParchmentBorder)
                        ) {
                            Column(
                                modifier = Modifier.padding(8.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "📖 Cultural & Language Learning Notes (for English Learners):",
                                    color = InkDark,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                caseEn.culturalNotesEn.forEach { note ->
                                    Text(
                                        text = "• $note",
                                        color = InkBrown,
                                        fontSize = 9.5.sp,
                                        lineHeight = 13.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Dialogue from Selected Character
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = WoodBoard),
                border = BorderStroke(1.dp, CardBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .border(2.dp, AntiqueGold, CircleShape)
                    ) {
                        Image(
                            painter = painterResource(id = character.avatarDrawableRes),
                            contentDescription = character.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${character.name} berujar:",
                            color = GoldBright,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "\"Pintu dan peti di tempat kejadian disegel oleh sandi kebahasaan kuno. Sebelum memeriksa para saksi, kita harus memecahkan teka-teki leksikal ini untuk mengungkap petunjuk rahasia!\"",
                            color = ParchmentLight,
                            fontSize = 11.sp,
                            fontStyle = FontStyle.Italic,
                            lineHeight = 14.sp
                        )
                    }
                }
            }
        }

        // Button to proceed to questions
        item {
            Button(
                onClick = onStartQuestions,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("start_questions_button")
                    .shadow(6.dp, RoundedCornerShape(10.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = AntiqueGold),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.5.dp, GoldBright)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = null,
                        tint = InkDark,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "DEKODIFIKASI SANDI (KERJAKAN SOAL) ->",
                        color = InkDark,
                        fontWeight = FontWeight.Black,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

// ==========================================
// STAGE 3: MENGERJAKAN SOAL INTERAKTIF (PAGE 3)
// ==========================================

@Composable
fun SolveQuestionsStage(
    storyline: StorylineUiState,
    viewModel: GameViewModel,
    onAnswer: (String, Int) -> Unit,
    onNextQuestion: () -> Unit,
    onProceedToEvidence: () -> Unit
) {
    val challenges = storyline.activeCase.challenges
    val currentIndex = storyline.currentQuestionIndex.coerceIn(0, challenges.size - 1)
    val question = challenges.getOrNull(currentIndex)
    val totalQuestions = challenges.size
    val allCompleted = storyline.answeredQuestions.size >= totalQuestions
    val answerResult = storyline.currentAnswerResult
    val hasAnsweredCurrent = question != null && storyline.answeredQuestions.containsKey(question.id)
    val selectedOptionIndex = if (question != null) storyline.answeredQuestions[question.id] else null

    // English Translation & Decoding State
    var showEnglishQuestion by remember(question?.id) { mutableStateOf(storyline.isEnglishEnabled) }
    val questionEn = remember(question?.id) { question?.let { EnglishTranslationProvider.getQuestionTranslation(it.id) } }

    // Game Lifeline Power-Up States
    var eliminatedOptionIndices by remember(question?.id) { mutableStateOf(setOf<Int>()) }
    var isKbbiHintOpen by remember(question?.id) { mutableStateOf(false) }
    var isCharacterPerkOpen by remember(question?.id) { mutableStateOf(false) }
    var streakCombo by remember { mutableIntStateOf(0) }

    // Floating Combo Trigger
    LaunchedEffect(answerResult?.isCorrect) {
        if (answerResult != null) {
            if (answerResult.isCorrect) {
                streakCombo += 1
                AudioEffects.playCorrect()
            } else {
                streakCombo = 0
                AudioEffects.playWrong()
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Progress & Streak Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "HALAMAN 3: UJIAN BAHASA",
                        color = AntiqueGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Soal Sandi (${currentIndex + 1}/$totalQuestions)",
                        color = GoldBright,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                // Streak Banner
                if (streakCombo >= 2) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = CrimsonRed.copy(alpha = 0.2f),
                        border = BorderStroke(1.dp, CrimsonRed)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bolt,
                                contentDescription = null,
                                tint = AmberWarning,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "STREAK x$streakCombo!",
                                color = AmberWarning,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }
                } else {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = CorkBoard,
                        border = BorderStroke(1.dp, AntiqueGold)
                    ) {
                        Text(
                            text = "${storyline.unlockedClueIds.size}/$totalQuestions Petunjuk",
                            color = AntiqueGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            LinearProgressIndicator(
                progress = { ((currentIndex + 1).toFloat() / totalQuestions.toFloat()).coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = AntiqueGold,
                trackColor = CorkBoard
            )
        }

        if (question != null && !allCompleted) {
            // Interactive Lifelines Bar (Alat Detektif)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // 50:50 Lifeline Button
                    OutlinedButton(
                        onClick = {
                            if (!hasAnsweredCurrent && eliminatedOptionIndices.isEmpty()) {
                                AudioEffects.playClick()
                                val wrongIndices = question.options.indices.filter { it != question.correctIndex }
                                eliminatedOptionIndices = wrongIndices.shuffled().take(2).toSet()
                            }
                        },
                        modifier = Modifier.weight(1f).height(38.dp),
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (eliminatedOptionIndices.isNotEmpty()) CorkBoard else WoodBoard
                        ),
                        border = BorderStroke(1.dp, if (eliminatedOptionIndices.isNotEmpty()) Color.Gray else AntiqueGold),
                        enabled = !hasAnsweredCurrent && eliminatedOptionIndices.isEmpty()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = AntiqueGold, modifier = Modifier.size(14.dp))
                            Text(text = "50:50", color = if (eliminatedOptionIndices.isNotEmpty()) Color.Gray else ParchmentLight, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // KBBI Book Lifeline Button
                    OutlinedButton(
                        onClick = {
                            AudioEffects.playClick()
                            isKbbiHintOpen = !isKbbiHintOpen
                        },
                        modifier = Modifier.weight(1f).height(38.dp),
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = WoodBoard),
                        border = BorderStroke(1.dp, AntiqueGold)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(imageVector = Icons.Default.MenuBook, contentDescription = null, tint = AntiqueGold, modifier = Modifier.size(14.dp))
                            Text(text = "Petunjuk", color = ParchmentLight, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Character Perk Lifeline Button
                    OutlinedButton(
                        onClick = {
                            AudioEffects.playClick()
                            isCharacterPerkOpen = !isCharacterPerkOpen
                        },
                        modifier = Modifier.weight(1f).height(38.dp),
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = WoodBoard),
                        border = BorderStroke(1.dp, Color(storyline.selectedCharacter.themeColorHex))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Psychology, contentDescription = null, tint = Color(storyline.selectedCharacter.themeColorHex), modifier = Modifier.size(14.dp))
                            Text(text = "Naluri", color = Color(storyline.selectedCharacter.themeColorHex), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // English Translation / Subtitle Toggle Button
                    OutlinedButton(
                        onClick = {
                            AudioEffects.playClick()
                            showEnglishQuestion = !showEnglishQuestion
                        },
                        modifier = Modifier.weight(1.2f).height(38.dp),
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (showEnglishQuestion) AntiqueGold.copy(alpha = 0.25f) else WoodBoard
                        ),
                        border = BorderStroke(1.dp, if (showEnglishQuestion) GoldBright else AntiqueGold)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Translate, contentDescription = null, tint = if (showEnglishQuestion) GoldBright else AntiqueGold, modifier = Modifier.size(14.dp))
                            Text(
                                text = if (showEnglishQuestion) "🇬🇧 Subtitle: ON" else "🇬🇧 Subtitle: OFF",
                                color = if (showEnglishQuestion) GoldBright else ParchmentLight,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Pop-up Hint if Opened
            if (isKbbiHintOpen) {
                item {
                    ParchmentCard(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Lightbulb, contentDescription = null, tint = AmberWarning, modifier = Modifier.size(20.dp))
                            Text(
                                text = "Petunjuk Kata: ${question.contextReason}",
                                color = InkBrown,
                                fontSize = 11.sp,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }
                }
            }

            // Pop-up Character Instinct if Opened
            if (isCharacterPerkOpen) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = WoodBoard),
                        border = BorderStroke(1.dp, Color(storyline.selectedCharacter.themeColorHex))
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, AntiqueGold, CircleShape)
                            ) {
                                Image(
                                    painter = painterResource(id = storyline.selectedCharacter.avatarDrawableRes),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Text(
                                text = "${storyline.selectedCharacter.name}: \"Perhatikan pilihan kata dan artinya dengan cermat!\"",
                                color = ParchmentLight,
                                fontSize = 11.sp,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }
                }
            }

            // Challenge Parchment Card with English Decoding
            item {
                ParchmentCard(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = Color(question.type.badgeColorHex)
                            ) {
                                Text(
                                    text = question.type.label,
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }

                            Text(
                                text = "+${question.points} Poin",
                                color = InkBrown,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Prompt with English Translation Subtitle (Hanya memberikan arti kata saja)
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = question.prompt,
                                color = InkDark,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )

                            // Subtitle: hanya memberikan arti kata saja
                            if (showEnglishQuestion && questionEn != null && questionEn.targetWordMeaningEn.isNotBlank()) {
                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(6.dp),
                                    color = AntiqueGold.copy(alpha = 0.15f),
                                    border = BorderStroke(1.dp, AntiqueGold.copy(alpha = 0.45f))
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Surface(
                                            shape = RoundedCornerShape(3.dp),
                                            color = AntiqueGold
                                        ) {
                                            Text(
                                                text = "SUBTITLE",
                                                color = InkDark,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Black,
                                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                            )
                                        }
                                        Text(
                                            text = "Arti Kata: \"${questionEn.targetWordMeaningEn}\"",
                                            color = InkDark,
                                            fontSize = 11.5.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            fontStyle = FontStyle.Italic
                                        )
                                    }
                                }
                            }
                        }

                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            color = WoodDark.copy(alpha = 0.08f),
                            border = BorderStroke(1.dp, ParchmentBorder)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp, horizontal = 12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Text(
                                    text = question.targetWord,
                                    color = InkDark,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Black,
                                    textAlign = TextAlign.Center
                                )
                                if (showEnglishQuestion && questionEn != null && questionEn.targetWordMeaningEn.isNotBlank()) {
                                    Text(
                                        text = "Arti Kata: \"${questionEn.targetWordMeaningEn}\"",
                                        color = AntiqueGold,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontStyle = FontStyle.Italic
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Options List with English Subtitles
            items(question.options.mapIndexed { idx, opt -> idx to opt }) { (idx, optionText) ->
                val isEliminated = eliminatedOptionIndices.contains(idx)
                val isSelected = selectedOptionIndex == idx
                val isAnswered = hasAnsweredCurrent
                val isCorrectAnswer = idx == question.correctIndex
                val optionEn = questionEn?.optionsEn?.getOrNull(idx)

                val buttonColor = when {
                    isEliminated -> WoodDark.copy(alpha = 0.4f)
                    !isAnswered -> if (isSelected) AntiqueGold else WoodBoard
                    isSelected && isCorrectAnswer -> EmeraldGreen
                    isSelected && !isCorrectAnswer -> CrimsonRed
                    isCorrectAnswer -> EmeraldGreen.copy(alpha = 0.8f)
                    else -> WoodBoard
                }

                val textColor = when {
                    isEliminated -> Color.Gray
                    !isAnswered -> if (isSelected) InkDark else ParchmentLight
                    isSelected && isCorrectAnswer -> Color.White
                    isSelected && !isCorrectAnswer -> Color.White
                    isCorrectAnswer -> Color.White
                    else -> ParchmentLight.copy(alpha = 0.7f)
                }

                Button(
                    onClick = {
                        if (!hasAnsweredCurrent && !isEliminated) {
                            AudioEffects.playClick()
                            onAnswer(question.id, idx)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 52.dp)
                        .testTag("option_${question.id}_$idx"),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                    border = BorderStroke(
                        1.dp,
                        if (isSelected) GoldBright else CardBorder
                    ),
                    enabled = !hasAnsweredCurrent && !isEliminated,
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(1.dp)
                        ) {
                            Text(
                                text = if (isEliminated)
                                    "-- Tersingkir --"
                                else
                                    "${('A'.code + idx).toChar()}. $optionText",
                                color = textColor,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                            if (showEnglishQuestion && optionEn != null && !isEliminated) {
                                Text(
                                    text = "↳ Arti: $optionEn",
                                    color = if (isSelected) Color.White.copy(alpha = 0.9f) else AntiqueGold.copy(alpha = 0.95f),
                                    fontSize = 10.sp,
                                    fontStyle = FontStyle.Italic
                                )
                            }
                        }

                        if (isAnswered) {
                            if (idx == question.correctIndex) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            } else if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Feedback Card if answered
            if (answerResult != null) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (answerResult.isCorrect) EmeraldGreen.copy(alpha = 0.15f) else CrimsonRed.copy(alpha = 0.15f)
                        ),
                        border = BorderStroke(
                            1.dp,
                            if (answerResult.isCorrect) EmeraldGreen else CrimsonRed
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = if (answerResult.isCorrect) Icons.Default.CheckCircle else Icons.Default.Close,
                                    contentDescription = null,
                                    tint = if (answerResult.isCorrect) EmeraldGreen else CrimsonRed,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = if (answerResult.isCorrect) "JAWABAN TEPAT! (+${question.points} PTS)" else "JAWABAN BELUM TEPAT (-1 HATI)!",
                                    color = if (answerResult.isCorrect) EmeraldGreen else CrimsonRed,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }

                            Text(
                                text = answerResult.explanation,
                                color = ParchmentLight,
                                fontSize = 12.sp,
                                lineHeight = 16.sp
                            )

                            if (showEnglishQuestion && questionEn != null && questionEn.targetWordMeaningEn.isNotBlank()) {
                                Text(
                                    text = "Arti Kata: \"${questionEn.targetWordMeaningEn}\"",
                                    color = AntiqueGold,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    fontStyle = FontStyle.Italic
                                )
                            }

                            if (answerResult.unlockedClueTitle != null) {
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = AntiqueGold.copy(alpha = 0.2f),
                                    border = BorderStroke(1.dp, AntiqueGold.copy(alpha = 0.6f))
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Lightbulb,
                                            contentDescription = null,
                                            tint = GoldBright,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Text(
                                            text = "Petunjuk Terurai: ${answerResult.unlockedClueTitle}",
                                            color = GoldBright,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Next question button
                item {
                    Button(
                        onClick = {
                            if (currentIndex + 1 < totalQuestions) {
                                onNextQuestion()
                            } else {
                                onProceedToEvidence()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("next_question_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = AntiqueGold),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = if (currentIndex + 1 < totalQuestions) "SOAL BERIKUTNYA (${currentIndex + 2}/$totalQuestions) ->" else "SEMUA SOAL SELESAI (LIHAT BUKTI) ->",
                                color = InkDark,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = InkDark,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        } else {
            // ALL QUESTIONS COMPLETED SUMMARY
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = WoodBoard),
                    border = BorderStroke(2.dp, GoldBright)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        WaxSealStamp(
                            text = "DEKODIFIKASI BERHASIL!",
                            rotation = 0f,
                            stampColor = AntiqueGold
                        )

                        Text(
                            text = "SEMUA $totalQuestions SANDI SELESAI!",
                            color = GoldBright,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "Seluruh ${storyline.unlockedClueIds.size} petunjuk rahasia telah berhasil dipecahkan. Sekarang saatnya menginterogasi saksi!",
                            color = ParchmentLight,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )

                        Button(
                            onClick = onProceedToEvidence,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("proceed_to_evidence_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = AntiqueGold),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "MASUK KE RUANG INTEROGASI SAKSI ->",
                                    color = InkDark,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 12.sp
                                )
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    tint = InkDark,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// STAGE 4: RUANG INTEROGASI SAKSI (PAGE 4)
// ==========================================

@Composable
fun EvidenceAndInterrogationStage(
    storyline: StorylineUiState,
    viewModel: GameViewModel,
    onProceedToConfrontation: () -> Unit
) {
    val activeCase = storyline.activeCase
    val unlockedClues = activeCase.clues.filter { storyline.unlockedClueIds.contains(it.id) }
    val suspects = activeCase.suspects
    val selectedSuspect = storyline.selectedSuspectForInterrogation
    val difficulty = viewModel.uiState.collectAsState().value.difficulty

    var activeTab by remember { mutableStateOf(0) } // 0 = Saksi, 1 = Petunjuk
    var interrogationActionMessage by remember { mutableStateOf<String?>(null) }
    var suspectNervousness by remember { mutableIntStateOf(20) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WaxSealStamp(
                    text = "HALAMAN 4: RUANG INTEROGASI",
                    rotation = 0f,
                    stampColor = AntiqueGold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "INTEROGASI & DEDUKSI SAKSI",
                    color = GoldBright,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "Uji alibi para saksi dengan menekan mereka menggunakan petunjuk yang kamu miliki. Amati pengukur kegugupan untuk menemukan pelaku berbohong!",
                    color = ParchmentLight.copy(alpha = 0.8f),
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Sub-tabs
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        AudioEffects.playClick()
                        activeTab = 0
                    },
                    modifier = Modifier.weight(1f).height(42.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (activeTab == 0) AntiqueGold else CorkBoard
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "SAKSI (${suspects.size})",
                        color = if (activeTab == 0) InkDark else ParchmentLight,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }

                Button(
                    onClick = {
                        AudioEffects.playClick()
                        activeTab = 1
                    },
                    modifier = Modifier.weight(1f).height(42.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (activeTab == 1) AntiqueGold else CorkBoard
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "BUKTI TERBUKA (${unlockedClues.size})",
                        color = if (activeTab == 1) InkDark else ParchmentLight,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }

        if (activeTab == 0) {
            // Suspects list
            items(suspects) { suspect ->
                val isEliminated = storyline.eliminatedSuspectIds.contains(suspect.id)
                val isInterrogated = storyline.interrogatedSuspectIds.contains(suspect.id)
                val isSelected = selectedSuspect?.id == suspect.id

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            AudioEffects.playClick()
                            viewModel.selectSuspectForStoryInterrogation(suspect)
                            suspectNervousness = when (difficulty) {
                                GameDifficulty.SANTAI -> if (suspect.isCulprit) 85 else 20
                                GameDifficulty.SEDANG -> if (suspect.isCulprit) 70 else 25
                                GameDifficulty.SANGAT_SULIT -> 50 // SETARA 50% di mode Sangat Sulit!
                            }
                            interrogationActionMessage = null
                        }
                        .testTag("story_suspect_${suspect.id}"),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isEliminated) WoodDark.copy(alpha = 0.6f) else WoodBoard
                    ),
                    border = BorderStroke(
                        width = if (isSelected) 2.dp else 1.dp,
                        color = if (isSelected) GoldBright else CardBorder
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(CircleShape)
                                        .background(Color(suspect.avatarColorHex)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = suspect.avatarInitials,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                }

                                Column {
                                    Text(
                                        text = suspect.name,
                                        color = if (isEliminated) ParchmentLight.copy(alpha = 0.5f) else GoldBright,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = suspect.roleTitle,
                                        color = AntiqueGold,
                                        fontSize = 11.sp
                                    )
                                }
                            }

                            if (isEliminated) {
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = Color.Gray.copy(alpha = 0.3f)
                                ) {
                                    Text(
                                        text = "DISINGKIRKAN",
                                        color = ParchmentLight.copy(alpha = 0.7f),
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            } else if (isInterrogated) {
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = EmeraldGreen.copy(alpha = 0.2f)
                                ) {
                                    Text(
                                        text = "SUDAH DIPERIKSA",
                                        color = EmeraldGreen,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }

                        Text(
                            text = "\"${suspect.fullAlibi}\"",
                            color = ParchmentLight,
                            fontSize = 11.sp,
                            fontStyle = FontStyle.Italic,
                            lineHeight = 15.sp
                        )

                        // INTERACTIVE INTERROGATION DESK WHEN SELECTED
                        if (isSelected && !isEliminated) {
                            HorizontalDivider(color = CardBorder.copy(alpha = 0.4f))

                            // Nervousness Gauge (Pengukur Kegugupan)
                            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Tingkat Kegugupan Saksi:",
                                        color = ParchmentLight.copy(alpha = 0.8f),
                                        fontSize = 10.sp
                                    )
                                    Text(
                                        text = "$suspectNervousness%",
                                        color = if (suspectNervousness > 60) CrimsonRed else EmeraldGreen,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                LinearProgressIndicator(
                                    progress = { (suspectNervousness.toFloat() / 100f).coerceIn(0f, 1f) },
                                    modifier = Modifier.fillMaxWidth().height(5.dp).clip(RoundedCornerShape(3.dp)),
                                    color = if (suspectNervousness > 60) CrimsonRed else EmeraldGreen,
                                    trackColor = CorkBoard
                                )
                            }

                            // 2 Interactive Question Tactics
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Button(
                                    onClick = {
                                        AudioEffects.playClick()
                                        if (suspect.isCulprit) {
                                            if (difficulty == GameDifficulty.SANGAT_SULIT) {
                                                suspectNervousness = 55
                                                interrogationActionMessage = "[MODE SANGAT SULIT] Saksi bersilat kata secara lihai! Pertanyaan alibi jam biasa tidak cukup meruntuhkan keterangannya (gugup 55%). Pemain wajib berpikir kritis memilih uji bukti fisik presisi!"
                                                AudioEffects.playWrong()
                                            } else {
                                                suspectNervousness = 90
                                                interrogationActionMessage = "Saksi berkeringat dingin dan berdalih ragu-ragu! Alibinya goyah saat ditanyakan rincian jam kejadian!"
                                                AudioEffects.playWrong()
                                            }
                                        } else {
                                            suspectNervousness = 15
                                            interrogationActionMessage = "Saksi menjawab dengan sangat tenang dan menunjukkan jadwal kegiatan bersama orang banyak."
                                            AudioEffects.playCorrect()
                                        }
                                    },
                                    modifier = Modifier.weight(1f).height(36.dp),
                                    shape = RoundedCornerShape(6.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = CorkBoard),
                                    border = BorderStroke(1.dp, AntiqueGold.copy(alpha = 0.6f))
                                ) {
                                    Text("Uji Alibi Jam", color = ParchmentLight, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }

                                Button(
                                    onClick = {
                                        AudioEffects.playClick()
                                        if (suspect.isCulprit) {
                                            suspectNervousness = 100
                                            interrogationActionMessage = "[DEDUKSI PRESISI CRITICAL!] Bukti fisik yang kamu sodorkan bertentangan mutlak dengan sanggahannya! Saksi berkeringat dingin dan pengukur kegugupan melonjak ke 100%!"
                                            AudioEffects.playSkakmat()
                                        } else {
                                            suspectNervousness = 10
                                            interrogationActionMessage = "Bukti fisik cocok dengan pengakuannya. Tidak ada kecocokan jejak mencurigakan."
                                            AudioEffects.playCorrect()
                                        }
                                    },
                                    modifier = Modifier.weight(1f).height(36.dp),
                                    shape = RoundedCornerShape(6.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = CorkBoard),
                                    border = BorderStroke(1.dp, AntiqueGold.copy(alpha = 0.6f))
                                ) {
                                    Text("Uji Bukti Fisik", color = ParchmentLight, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }

                            interrogationActionMessage?.let { msg ->
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = if (suspectNervousness >= 70) CrimsonRed.copy(alpha = 0.2f) else EmeraldGreen.copy(alpha = 0.2f),
                                    border = BorderStroke(1.dp, if (suspectNervousness >= 70) CrimsonRed else EmeraldGreen)
                                ) {
                                    Text(
                                        text = msg,
                                        color = ParchmentLight,
                                        fontSize = 11.sp,
                                        modifier = Modifier.padding(8.dp),
                                        fontStyle = FontStyle.Italic
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                OutlinedButton(
                                    onClick = {
                                        AudioEffects.playClick()
                                        viewModel.eliminateSuspectInStory(suspect.id)
                                    },
                                    modifier = Modifier.height(34.dp),
                                    shape = RoundedCornerShape(6.dp),
                                    border = BorderStroke(1.dp, CardBorder)
                                ) {
                                    Text(
                                        text = "Singkirkan (Bukan Pelaku)",
                                        color = ParchmentLight,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // Unlocked clues list
            items(unlockedClues) { clue ->
                ParchmentCard(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = clue.title,
                                color = InkDark,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = AntiqueGold.copy(alpha = 0.25f)
                            ) {
                                Text(
                                    text = clue.category,
                                    color = InkBrown,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Text(
                            text = clue.revealedDetail,
                            color = InkBrown,
                            fontSize = 11.sp,
                            lineHeight = 15.sp
                        )
                    }
                }
            }
        }

        // Bottom CTA to proceed to Final Confrontation
        item {
            Button(
                onClick = onProceedToConfrontation,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("proceed_to_confrontation_button")
                    .shadow(6.dp, RoundedCornerShape(10.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = AntiqueGold),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.5.dp, GoldBright)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Gavel,
                        contentDescription = null,
                        tint = InkDark,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "SIDANG TERBUKA (TUDUH PELAKU) ->",
                        color = InkDark,
                        fontWeight = FontWeight.Black,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

// ==========================================
// STAGE 5: KONFRONTASI AKHIR / SKAKMAT (PAGE 5)
// ==========================================

@Composable
fun FinalConfrontationStage(
    storyline: StorylineUiState,
    viewModel: GameViewModel,
    onProceedToEpilogue: () -> Unit
) {
    val activeCase = storyline.activeCase
    val suspects = activeCase.suspects.filter { !storyline.eliminatedSuspectIds.contains(it.id) }
    val accusedSuspectId = storyline.accusedSuspectId
    val isSuccess = storyline.isConfrontationSuccess
    val feedback = storyline.confrontationFeedback
    val selectedSuspect = activeCase.suspects.find { it.id == accusedSuspectId }

    // Screen Shake Animatable
    val shakeOffset = remember { Animatable(0f) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .offset { IntOffset(shakeOffset.value.roundToInt(), 0) }
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            WaxSealStamp(
                text = "HALAMAN 5: SIDANG TERBUKA",
                rotation = 0f,
                stampColor = StampRed
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "KONFRONTASI & TUDUHAN PELAKU",
                color = GoldBright,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = "Di hadapan para tetua dan abdi dalem, tunjuk siapa dalang sesungguhnya berdasarkan bukti yang telah kamu kumpulkan!",
                color = ParchmentLight.copy(alpha = 0.8f),
                fontSize = 11.sp,
                textAlign = TextAlign.Center
            )
        }

        if (!isSuccess) {
            // Suspect Selector
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = WoodBoard),
                    border = BorderStroke(1.dp, CardBorder)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "1. Tunjuk Siapa Tersangka Pelaku:",
                            color = GoldBright,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )

                        suspects.forEach { s ->
                            val isSelected = s.id == accusedSuspectId

                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        AudioEffects.playClick()
                                        viewModel.setAccusedSuspectInStory(s.id)
                                    },
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSelected) AntiqueGold else CorkBoard,
                                border = BorderStroke(
                                    1.dp,
                                    if (isSelected) GoldBright else CardBorder
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .clip(CircleShape)
                                            .background(Color(s.avatarColorHex)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = s.avatarInitials,
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp
                                        )
                                    }

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = s.name,
                                            color = if (isSelected) InkDark else ParchmentLight,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp
                                        )
                                        Text(
                                            text = s.roleTitle,
                                            color = if (isSelected) InkBrown else AntiqueGold,
                                            fontSize = 10.sp
                                        )
                                    }

                                    if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = null,
                                            tint = InkDark,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (feedback != null) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = CrimsonRed.copy(alpha = 0.2f)),
                        border = BorderStroke(1.dp, CrimsonRed)
                    ) {
                        Text(
                            text = feedback,
                            color = Color.White,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(12.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Gavel Accusation Button with Screen Shake Trigger
            item {
                Button(
                    onClick = {
                        AudioEffects.playGavelBang()
                        viewModel.submitStoryConfrontation()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("submit_accusation_button")
                        .shadow(6.dp, RoundedCornerShape(10.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.5.dp, GoldBright)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Gavel,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "KETUK PALU & TUDUH PELAKU!",
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        } else {
            // SUCCESSFUL CONFRONTATION & CONFESSION SCENE!
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = WoodBoard),
                    border = BorderStroke(2.dp, GoldBright)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        WaxSealStamp(
                            text = "KASUS TERPECAHKAN!",
                            rotation = 0f,
                            stampColor = EmeraldGreen
                        )

                        Text(
                            text = "SKAKMAT! PENGAKUAN: ${selectedSuspect?.name}",
                            color = GoldBright,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            textAlign = TextAlign.Center
                        )

                        ParchmentCard(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "\"${selectedSuspect?.confession}\"",
                                    color = InkDark,
                                    fontSize = 13.sp,
                                    fontStyle = FontStyle.Italic,
                                    lineHeight = 18.sp
                                )

                                val suspectEn = selectedSuspect?.let { EnglishTranslationProvider.getSuspectTranslation(it.id) }
                                if (storyline.isEnglishEnabled && suspectEn != null && suspectEn.confessionEn.isNotBlank()) {
                                    Text(
                                        text = "🇬🇧 \"${suspectEn.confessionEn}\"",
                                        color = InkBrown,
                                        fontSize = 12.sp,
                                        fontStyle = FontStyle.Italic,
                                        lineHeight = 16.sp
                                    )
                                }

                                Text(
                                    text = "- Berita Acara Pemeriksaan Sidang Adat",
                                    color = InkBrown,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.align(Alignment.End)
                                )
                            }
                        }

                        Button(
                            onClick = onProceedToEpilogue,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("proceed_to_epilogue_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = AntiqueGold),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.5.dp, GoldBright)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "LIHAT EPILOG & PENYELESAIAN BAB ->",
                                    color = InkDark,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 12.sp
                                )
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    tint = InkDark,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// STAGE 6: EPILOG & HADIAH BAB (PAGE 6)
// ==========================================

@Composable
fun ChapterEpilogueStage(
    storyline: StorylineUiState,
    onNextChapter: () -> Unit,
    onHome: () -> Unit
) {
    val activeCase = storyline.activeCase
    val character = storyline.selectedCharacter
    val stars = if (storyline.heartsRemaining >= 3) 3 else if (storyline.heartsRemaining >= 2) 2 else 1
    val isLastChapter = storyline.currentChapterIndex >= GameDataProvider.getInitialCases().size - 1

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            WaxSealStamp(
                text = "HALAMAN 6: KELULUSAN BAB",
                rotation = 0f,
                stampColor = AntiqueGold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "BAB ${storyline.currentChapterIndex + 1} TERPECAHKAN!",
                color = GoldBright,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = activeCase.title,
                color = ParchmentLight.copy(alpha = 0.8f),
                fontSize = 13.sp
            )
        }

        // Stars & Rewards
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = WoodBoard),
                border = BorderStroke(1.5.dp, AntiqueGold)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(3) { idx ->
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = if (idx < stars) GoldBright else Color.Gray.copy(alpha = 0.4f),
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }

                    Text(
                        text = when (stars) {
                            3 -> "INVESTIGASI SEMPURNA!"
                            2 -> "INVESTIGASI SANGAT BAIK!"
                            else -> "KASUS BERHASIL DITUNTASKAN!"
                        },
                        color = GoldBright,
                        fontWeight = FontWeight.Black,
                        fontSize = 14.sp
                    )

                    HorizontalDivider(color = CardBorder.copy(alpha = 0.4f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "+${350 + (stars * 50)} XP", color = GoldBright, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text(text = "Pengalaman", color = ParchmentLight.copy(alpha = 0.7f), fontSize = 10.sp)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "${storyline.chapterScore} PTS", color = AntiqueGold, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text(text = "Skor Akhir", color = ParchmentLight.copy(alpha = 0.7f), fontSize = 10.sp)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "${storyline.unlockedClueIds.size} Kata", color = EmeraldGreen, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text(text = "Kamus Baru", color = ParchmentLight.copy(alpha = 0.7f), fontSize = 10.sp)
                        }
                    }
                }
            }
        }

        // Epilogue Narrative / Dialogue
        item {
            ParchmentCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .border(1.5.dp, AntiqueGold, CircleShape)
                        ) {
                            Image(
                                painter = painterResource(id = character.avatarDrawableRes),
                                contentDescription = character.name,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Column {
                            Text(
                                text = "Refleksi ${character.name}:",
                                color = InkDark,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "Catatan Penutupan Kasus",
                                color = InkBrown,
                                fontSize = 10.sp
                            )
                        }
                    }

                    HorizontalDivider(color = ParchmentBorder.copy(alpha = 0.4f))

                    Text(
                        text = "\"Misteri ${activeCase.title} telah terpecahkan berkat ketelitian leksikologi dan kepekaan rasa bahasa. Pusaka adat kembali aman, dan kebenaran telah ditegakkan. Namun perjalanan detektif bahasa di Nusantara baru saja dimulai!\"",
                        color = InkBrown,
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Italic,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        // Action Buttons: Next Chapter or Home
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (!isLastChapter) {
                    Button(
                        onClick = onNextChapter,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("next_chapter_story_button")
                            .shadow(6.dp, RoundedCornerShape(10.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = AntiqueGold),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.5.dp, GoldBright)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "LANJUT KE BAB ${storyline.currentChapterIndex + 2} ->",
                                color = InkDark,
                                fontWeight = FontWeight.Black,
                                fontSize = 13.sp
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = InkDark,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                OutlinedButton(
                    onClick = onHome,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .testTag("story_home_button"),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, CardBorder)
                ) {
                    Text(
                        text = "KEMBALI KE BERANDA",
                        color = ParchmentLight,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
