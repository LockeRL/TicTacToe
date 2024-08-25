package com.locker.tictactoe.presentation.compose.navigation.screen

class GameScreen(name: String) : Screen(name) {
    data object GameScreen : MainMenuScreen("TwoManGameScreen")
    data object NextGameScreen : MainMenuScreen("NextGameScreen")
}
