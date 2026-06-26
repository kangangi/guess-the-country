package com.example.guessthecountry2.ui.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guessthecountry2.data.model.ScoreEntity
import com.example.guessthecountry2.data.repository.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LeaderboardViewModel @Inject constructor(
    repository: CountryRepository
) : ViewModel() {

    val uiState: StateFlow<LeaderboardUiState> = repository
        .getTopScores()
        .map { scores -> LeaderboardUiState(scores = scores) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = LeaderboardUiState()
        )
}

data class LeaderboardUiState(
    val scores: List<ScoreEntity> = emptyList()
)