package com.example.guessthecountry2.ui.welcome

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

val Pink = Color(0xFFE8547A)
val LightPink = Color(0xFFFFF0F5)
val DarkPink = Color(0xFFC03060)

@Composable
fun WelcomeScreen(onStartGame: (String) -> Unit) {
    var playerName by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = LightPink
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "🌍", fontSize = 72.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Guess That Flag!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = DarkPink
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "How well do you know the world?",
                fontSize = 14.sp,
                color = Color(0xFFB07090),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            OutlinedTextField(
                value = playerName,
                onValueChange = {
                    playerName = it
                    showError = false
                },
                label = { Text("Your name") },
                placeholder = { Text("Enter your name…") },
                isError = showError,
                supportingText = {
                    if (showError) Text("Please enter your name")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Pink,
                    focusedLabelColor = Pink,
                    cursorColor = Pink
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (playerName.trim().isEmpty()) {
                        showError = true
                    } else {
                        onStartGame(playerName.trim())
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Pink)
            ) {
                Text("Start Game →", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "10 countries · 3 hints each",
                fontSize = 12.sp,
                color = Color(0xFFD0A0B8)
            )
        }
    }
}