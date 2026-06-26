package com.example.guessthecountry2.data.repository
import com.example.guessthecountry2.BuildConfig

import com.example.guessthecountry2.data.local.ScoreDao
import com.example.guessthecountry2.data.model.Country
import com.example.guessthecountry2.data.model.ScoreEntity
import com.example.guessthecountry2.data.remote.CountryApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryRepository @Inject constructor(
    private val apiService: CountryApiService,
    private val scoreDao: ScoreDao
) {

    // Network
    suspend fun getCountries(): Result<List<Country>> {
        return try {
            android.util.Log.d("Repository", "Token being sent: Bearer ${BuildConfig.COUNTRIES_API_KEY}")
            val countries = apiService.getAllCountries(
                token = "Bearer ${BuildConfig.COUNTRIES_API_KEY}"
            )
            Result.success(countries)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Database
    suspend fun saveScore(playerName: String, score: Int, total: Int) {
        scoreDao.insertScore(
            ScoreEntity(
                playerName = playerName,
                score = score,
                total = total
            )
        )
    }

    fun getTopScores(): Flow<List<ScoreEntity>> {
        return scoreDao.getTopScores()
    }

    fun getPlayerBestScore(playerName: String): Flow<ScoreEntity?> {
        return scoreDao.getPlayerBestScore(playerName)
    }
}