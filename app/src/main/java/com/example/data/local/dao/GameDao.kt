package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.CaseProgressEntity
import com.example.data.local.entity.UnlockedWordEntity
import com.example.data.local.entity.UserProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM user_progress WHERE id = 1 LIMIT 1")
    fun getUserProgress(): Flow<UserProgressEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserProgress(progress: UserProgressEntity)

    @Query("SELECT * FROM case_progress")
    fun getAllCaseProgress(): Flow<List<CaseProgressEntity>>

    @Query("SELECT * FROM case_progress WHERE caseId = :caseId LIMIT 1")
    suspend fun getCaseProgress(caseId: String): CaseProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCaseProgress(caseProgress: CaseProgressEntity)

    @Query("SELECT wordId FROM unlocked_words")
    fun getAllUnlockedWordIds(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun unlockWord(word: UnlockedWordEntity)

    @Query("DELETE FROM case_progress")
    suspend fun resetCaseProgress()

    @Query("DELETE FROM unlocked_words")
    suspend fun resetUnlockedWords()
}
