package com.example.guessthecountry2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.guessthecountry2.data.model.ScoreEntity

@Database(
    entities = [ScoreEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scoreDao(): ScoreDao
}