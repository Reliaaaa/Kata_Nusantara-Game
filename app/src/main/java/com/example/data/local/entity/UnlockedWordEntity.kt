package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unlocked_words")
data class UnlockedWordEntity(
    @PrimaryKey val wordId: String,
    val unlockedAt: Long = System.currentTimeMillis()
)
