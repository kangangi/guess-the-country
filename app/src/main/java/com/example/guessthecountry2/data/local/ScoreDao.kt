package com.example.guessthecountry2.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.guessthecountry2.data.model.ScoreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScore(score: ScoreEntity)

    @Query("SELECT * FROM scores ORDER BY score DESC LIMIT 10")
    fun getTopScores(): Flow<List<ScoreEntity>>

    @Query("SELECT * FROM scores WHERE playerName = :playerName ORDER BY score DESC LIMIT 1")
    fun getPlayerBestScore(playerName: String): Flow<ScoreEntity?>
}