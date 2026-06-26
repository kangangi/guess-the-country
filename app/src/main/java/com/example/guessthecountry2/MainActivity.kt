package com.example.guessthecountry2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.guessthecountry2.ui.Screen
import com.example.guessthecountry2.ui.game.GameScreen
import com.example.guessthecountry2.ui.leaderboard.LeaderboardScreen
import com.example.guessthecountry2.ui.results.ResultsScreen
import com.example.guessthecountry2.ui.theme.GuessTheCountry2Theme
import com.example.guessthecountry2.ui.welcome.WelcomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GuessTheCountry2Theme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Welcome.route) {

        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onStartGame = { playerName ->
                    navController.navigate(Screen.Game.createRoute(playerName))
                }
            )
        }

        composable(
            route = Screen.Game.route,
            arguments = listOf(navArgument("playerName") { type = NavType.StringType })
        ) { backStackEntry ->
            val playerName = backStackEntry.arguments?.getString("playerName") ?: ""
            GameScreen(
                playerName = playerName,
                onGameOver = { name, score, total ->
                    navController.navigate(Screen.Results.createRoute(name, score, total)) {
                        popUpTo(Screen.Game.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.Results.route,
            arguments = listOf(
                navArgument("playerName") { type = NavType.StringType },
                navArgument("score") { type = NavType.IntType },
                navArgument("total") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val playerName = backStackEntry.arguments?.getString("playerName") ?: ""
            val score = backStackEntry.arguments?.getInt("score") ?: 0
            val total = backStackEntry.arguments?.getInt("total") ?: 0
            ResultsScreen(
                playerName = playerName,
                score = score,
                total = total,
                onPlayAgain = {
                    navController.navigate(Screen.Game.createRoute(playerName)) {
                        popUpTo(Screen.Welcome.route)
                    }
                },
                onChangePlayer = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                },
                onLeaderboard = {
                    navController.navigate(Screen.Leaderboard.route)
                }
            )
        }

        composable(Screen.Leaderboard.route) {
            LeaderboardScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}