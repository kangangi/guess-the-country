package com.example.guessthecountry2.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.guessthecountry2.ui.welcome.DarkPink
import com.example.guessthecountry2.ui.welcome.LightPink
import com.example.guessthecountry2.ui.welcome.Pink

@Composable
fun GameScreen(
    playerName: String,
    onGameOver: (String, Int, Int) -> Unit,
    viewModel: GameViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Navigate when game ends
    LaunchedEffect(uiState.isGameOver) {
        if (uiState.isGameOver) {
            onGameOver(playerName, uiState.score, uiState.countries.size)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = LightPink
    ) {
        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Pink)
                }
            }

            uiState.errorMessage != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Failed to load countries.\n${uiState.errorMessage}",
                        textAlign = TextAlign.Center,
                        color = DarkPink
                    )
                }
            }

            uiState.countries.isNotEmpty() -> {
                val country = uiState.countries[uiState.currentIndex]
                val hints = viewModel.getHints(country)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Top bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF4C0D1), RoundedCornerShape(8.dp))
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("👤 $playerName", color = Color(0xFF72243E), fontWeight = FontWeight.Bold)
                        Text("Score: ${uiState.score}", color = Color(0xFF72243E), fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Country ${uiState.currentIndex + 1} of ${uiState.countries.size}",
                        fontSize = 12.sp,
                        color = Color(0xFFB07090),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Flag image
                    AsyncImage(
                        model = country.flags.png,
                        contentDescription = "Flag",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Which country does this flag belong to?",
                        fontSize = 13.sp,
                        color = Color(0xFFB07090)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Guess input
                    OutlinedTextField(
                        value = uiState.currentGuess,
                        onValueChange = { viewModel.updateGuess(it) },
                        placeholder = { Text("Type your answer…") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Pink,
                            cursorColor = Pink
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Submit + Hint buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { viewModel.submitGuess(uiState.currentGuess, playerName) },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Pink)
                        ) {
                            Text("Submit")
                        }

                        Button(
                            onClick = { viewModel.useHint() },
                            enabled = uiState.hintsUsed < 3,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFF4C0D1),
                                contentColor = Color(0xFF72243E),
                                disabledContainerColor = Color(0xFFE0E0E0)
                            )
                        ) {
                            Text("Hint 💡 ${uiState.hintsUsed}/3")
                        }
                    }

                    // Hints revealed so far
                    if (uiState.hintsUsed > 0) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            hints.take(uiState.hintsUsed).forEach { hint ->
                                Text(
                                    text = "💡 $hint",
                                    fontSize = 13.sp,
                                    color = Color(0xFF72243E),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFFFFF4F7), RoundedCornerShape(8.dp))
                                        .padding(10.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}