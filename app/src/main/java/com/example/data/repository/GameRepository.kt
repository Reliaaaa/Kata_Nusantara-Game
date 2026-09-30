package com.example.data.repository

import com.example.data.GameDataProvider
import com.example.data.local.dao.GameDao
import com.example.data.local.entity.CaseProgressEntity
import com.example.data.local.entity.UnlockedWordEntity
import com.example.data.local.entity.UserProgressEntity
import com.example.model.Achievement
import com.example.model.CaseData
import com.example.model.DetectiveRank
import com.example.model.DictionaryEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GameRepository(private val gameDao: GameDao) {

    fun getUserProgress(): Flow<UserProgressEntity> {
        return gameDao.getUserProgress().map { entity ->
            entity ?: UserProgressEntity(
                id = 1,
                xp = 0,
                totalScore = 0,
                currentRankLevel = 1,
                casesSolvedCount = 0,
                hintsAvailable = 3
            )
        }
    }

    suspend fun saveUserProgress(progress: UserProgressEntity) {
        gameDao.saveUserProgress(progress)
    }

    fun getAllCaseProgress(): Flow<List<CaseProgressEntity>> {
        return gameDao.getAllCaseProgress()
    }

    suspend fun getCaseProgress(caseId: String): CaseProgressEntity? {
        return gameDao.getCaseProgress(caseId)
    }

    suspend fun saveCaseCompleted(
        caseId: String,
        score: Int,
        timeSeconds: Int,
        earnedStars: Int,
        xpGained: Int
    ) {
        val existing = gameDao.getCaseProgress(caseId)
        val bestScore = maxOf(existing?.bestScore ?: 0, score)
        val bestStars = maxOf(existing?.starsEarned ?: 0, earnedStars)
        val bestTime = if (existing != null && existing.bestTimeSeconds > 0) {
            minOf(existing.bestTimeSeconds, timeSeconds)
        } else {
            timeSeconds
        }

        val updatedCaseProgress = CaseProgressEntity(
            caseId = caseId,
            isUnlocked = true,
            isCompleted = true,
            starsEarned = bestStars,
            bestScore = bestScore,
            bestTimeSeconds = bestTime,
            completedAt = System.currentTimeMillis()
        )
        gameDao.saveCaseProgress(updatedCaseProgress)

        // Update User Progress
        val currentProgress = gameDao.getUserProgress()
        // We can get flow or read once
        // For simplicity:
        // We calculate new rank level based on new XP
    }

    suspend fun unlockCase(caseId: String) {
        val existing = gameDao.getCaseProgress(caseId) ?: CaseProgressEntity(caseId = caseId)
        gameDao.saveCaseProgress(existing.copy(isUnlocked = true))
    }

    suspend fun unlockWord(wordId: String) {
        gameDao.unlockWord(UnlockedWordEntity(wordId))
    }

    fun getUnlockedWordIds(): Flow<List<String>> {
        return gameDao.getAllUnlockedWordIds()
    }

    suspend fun resetAllProgress() {
        gameDao.resetCaseProgress()
        gameDao.resetUnlockedWords()
        gameDao.saveUserProgress(UserProgressEntity(id = 1, xp = 0, totalScore = 0, currentRankLevel = 1, casesSolvedCount = 0))
    }

    fun getDetectiveRank(xp: Int): DetectiveRank {
        val ranks = GameDataProvider.ranks.sortedByDescending { it.minXp }
        return ranks.firstOrNull { xp >= it.minXp } ?: GameDataProvider.ranks.first()
    }
}
