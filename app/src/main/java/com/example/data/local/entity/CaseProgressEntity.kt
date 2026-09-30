package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "case_progress")
data class CaseProgressEntity(
    @PrimaryKey val caseId: String,
    val isUnlocked: Boolean = false,
    val isCompleted: Boolean = false,
    val starsEarned: Int = 0,
    val bestScore: Int = 0,
    val bestTimeSeconds: Int = 0,
    val completedAt: Long = 0L
)
