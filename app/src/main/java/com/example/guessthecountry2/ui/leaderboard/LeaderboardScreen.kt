package com.example.guessthecountry2.ui.leaderboard


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.guessthecountry2.data.model.ScoreEntity
import com.example.guessthecountry2.ui.welcome.DarkPink
import com.example.guessthecountry2.ui.welcome.LightPink
import com.example.guessthecountry2.ui.welcome.Pink

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(
    onBack: () -> Unit,
    viewModel: LeaderboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🏆 Leaderboard", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Pink)
            )
        },
        containerColor = LightPink
    ) { padding ->
        if (uiState.scores.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No scores yet!\nPlay a game to get on the board.",
                    color = Color(0xFFB07090),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(uiState.scores) { index, score ->
                    ScoreRow(position = index + 1, score = score)
                }
            }
        }
    }
}

@Composable
fun ScoreRow(position: Int, score: ScoreEntity) {
    val medal = when (position) {
        1 -> "🥇"
        2 -> "🥈"
        3 -> "🥉"
        else -> "$position."
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFFF4C0D1),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = medal, fontSize = 24.sp)
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = score.playerName,
                    fontWeight = FontWeight.Bold,
                    color = DarkPink
                )
                Text(
                    text = "${score.score} / ${score.total} correct",
                    fontSize = 12.sp,
                    color = Color(0xFF903050)
                )
            }
            Text(
                text = "${score.score}",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Pink
            )
        }
    }
}