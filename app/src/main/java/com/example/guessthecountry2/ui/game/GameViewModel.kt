package com.example.guessthecountry2.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guessthecountry2.data.model.Country
import com.example.guessthecountry2.data.repository.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repository: CountryRepository
) : ViewModel() {

    // The entire game state lives in one place
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    init {
        loadCountries()
    }

    private fun loadCountries() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val countries = repository.getCountries()
                countries.fold(
                    onSuccess = { list ->
                        android.util.Log.d("GameViewModel", "✅ Success: ${list.size} countries loaded")
                        val filtered = list
                            .filter { it.flags.png.isNotEmpty() }
                            .shuffled()
                            .take(10)
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                countries = filtered,
                                currentIndex = 0
                            )
                        }
                    },
                    onFailure = { error ->
                        android.util.Log.e("GameViewModel", "❌ Error: ${error.message}")
                        android.util.Log.e("GameViewModel", "❌ Cause: ${error.cause}")
                        android.util.Log.e("GameViewModel", "❌ Stack: ${error.stackTraceToString()}")
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = error.message
                            )
                        }
                    }
                )
            } catch (e: Exception) {
                android.util.Log.e("GameViewModel", "❌ Exception: ${e.message}")
                android.util.Log.e("GameViewModel", "❌ Stack: ${e.stackTraceToString()}")
            }
        }
    }
    fun submitGuess(guess: String, playerName: String) {
        val state = _uiState.value
        val currentCountry = state.countries.getOrNull(state.currentIndex) ?: return
        val correctName = currentCountry.name.common

        if (guess.trim().equals(correctName, ignoreCase = true)) {
            val newScore = state.score + 1
            val nextIndex = state.currentIndex + 1

            if (nextIndex >= state.countries.size) {
                // Game complete — save score and go to results
                saveScore(playerName, newScore, state.countries.size)
                _uiState.update {
                    it.copy(
                        score = newScore,
                        isGameOver = true,
                        isComplete = true
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        score = newScore,
                        currentIndex = nextIndex,
                        hintsUsed = 0,
                        currentGuess = ""
                    )
                }
            }
        } else {
            // Wrong answer — game over
            saveScore(playerName, state.score, state.countries.size)
            _uiState.update {
                it.copy(isGameOver = true, isComplete = false)
            }
        }
    }

    fun useHint() {
        val state = _uiState.value
        if (state.hintsUsed < 3) {
            _uiState.update { it.copy(hintsUsed = it.hintsUsed + 1) }
        }
    }

    fun updateGuess(guess: String) {
        _uiState.update { it.copy(currentGuess = guess) }
    }

    fun resetGame() {
        loadCountries()
        _uiState.update {
            it.copy(
                score = 0,
                currentIndex = 0,
                hintsUsed = 0,
                currentGuess = "",
                isGameOver = false,
                isComplete = false
            )
        }
    }

    private fun saveScore(playerName: String, score: Int, total: Int) {
        viewModelScope.launch {
            repository.saveScore(playerName, score, total)
        }
    }

    // Builds hints dynamically from the country data
    fun getHints(country: Country): List<String> {
        val hints = mutableListOf<String>()
        country.region?.let { hints.add("This country is in $it") }
        country.capital?.firstOrNull()?.let { hints.add("Its capital city is $it") }
        country.population?.let {
            val pop = when {
                it >= 1_000_000_000 -> "${it / 1_000_000_000}B"
                it >= 1_000_000 -> "${it / 1_000_000}M"
                else -> "${it / 1_000}K"
            }
            hints.add("Its population is approximately $pop")
        }
        return hints.take(3)
    }
}

// All game state in one data class
data class GameUiState(
    val isLoading: Boolean = false,
    val countries: List<Country> = emptyList(),
    val currentIndex: Int = 0,
    val score: Int = 0,
    val hintsUsed: Int = 0,
    val currentGuess: String = "",
    val isGameOver: Boolean = false,
    val isComplete: Boolean = false,
    val errorMessage: String? = null
)