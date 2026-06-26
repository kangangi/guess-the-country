package com.example.guessthecountry2.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scores")
data class ScoreEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val playerName: String,
    val score: Int,
    val total: Int,
    val timestamp: Long = System.currentTimeMillis()
)