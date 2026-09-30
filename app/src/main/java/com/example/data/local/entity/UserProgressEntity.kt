package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_progress")
data class UserProgressEntity(
    @PrimaryKey val id: Int = 1,
    val xp: Int = 0,
    val totalScore: Int = 0,
    val currentRankLevel: Int = 1,
    val casesSolvedCount: Int = 0,
    val hintsAvailable: Int = 3,
    val soundEnabled: Boolean = true,
    val hapticEnabled: Boolean = true,
    val selectedCharacterId: String = "char_arya",
    val currentStoryChapter: Int = 0
)
