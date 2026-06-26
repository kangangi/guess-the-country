package com.example.guessthecountry2.ui.results

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.guessthecountry2.ui.welcome.DarkPink
import com.example.guessthecountry2.ui.welcome.LightPink
import com.example.guessthecountry2.ui.welcome.Pink

@Composable
fun ResultsScreen(
    playerName: String,
    score: Int,
    total: Int,
    onPlayAgain: () -> Unit,
    onChangePlayer: () -> Unit,
    onLeaderboard: () -> Unit
) {
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
            Text(text = "🏆", fontSize = 72.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Game Over, $playerName!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = DarkPink
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "$score",
                fontSize = 80.sp,
                fontWeight = FontWeight.Bold,
                color = Pink
            )

            Text(
                text = "countries guessed correctly",
                fontSize = 14.sp,
                color = Color(0xFFB07090)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Summary card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFFF4C0D1),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total countries", color = Color(0xFF72243E))
                        Text("$total", color = Color(0xFF72243E), fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Correct answers", color = Color(0xFF72243E))
                        Text("$score", color = Color(0xFF72243E), fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onPlayAgain,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Pink)
            ) {
                Text("Play Again", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onLeaderboard,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(26.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Pink)
            ) {
                Text("View Leaderboard 🏆", fontSize = 16.sp, color = Pink)
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onChangePlayer,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(26.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Pink)
            ) {
                Text("Change Player", fontSize = 16.sp, color = Pink)
            }
        }
    }
}

