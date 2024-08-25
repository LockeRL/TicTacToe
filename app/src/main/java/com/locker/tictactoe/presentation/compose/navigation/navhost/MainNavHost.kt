package com.locker.tictactoe.presentation.compose.navigation.navhost

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import com.locker.tictactoe.presentation.common.TicTacNavHost
import com.locker.tictactoe.presentation.compose.navigation.screen.MainMenuScreen
import com.locker.tictactoe.presentation.compose.screens.game.view.gameBackButton
import com.locker.tictactoe.presentation.compose.screens.main.screen.MainScreen
import com.locker.tictactoe.presentation.model.BackButton

@Composable
fun MainNavHost(
    innerPadding: PaddingValues,
    updateTopAppBar: (BackButton?) -> Unit,
    modifier: Modifier
) {
    TicTacNavHost(
        startDestination = MainMenuScreen.MenuScreen.name,
        modifier = modifier.padding(innerPadding)
    ) { navController ->
        composable(MainMenuScreen.MenuScreen.name) {
            updateTopAppBar(null)
            MainScreen(
                onStartGameClick = { navController.navigate(MainMenuScreen.TwoManGameScreen.name) },
                modifier = modifier
            )
        }

        composable(MainMenuScreen.TwoManGameScreen.name) {
            updateTopAppBar(gameBackButton(onClick = { navController.popBackStack() }))
            GameNavHost(modifier = modifier)
        }
    }
}
