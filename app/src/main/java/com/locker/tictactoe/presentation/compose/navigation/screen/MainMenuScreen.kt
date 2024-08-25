package com.locker.tictactoe.presentation.compose.navigation.screen

sealed class MainMenuScreen(val name: String) : Screen(name) {
    data object TwoManGameScreen : MainMenuScreen("TwoManGameScreen")
    data object MenuScreen : MainMenuScreen("MainScreen")
}
