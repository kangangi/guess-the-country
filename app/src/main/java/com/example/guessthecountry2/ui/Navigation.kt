package com.example.guessthecountry2.ui

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Game : Screen("game/{playerName}") {
        fun createRoute(playerName: String) = "game/$playerName"
    }
    object Results : Screen("results/{playerName}/{score}/{total}") {
        fun createRoute(playerName: String, score: Int, total: Int) =
            "results/$playerName/$score/$total"
    }
    object Leaderboard : Screen("leaderboard")
}