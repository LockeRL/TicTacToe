package org.locker.tictactoe.presentation.compose.navigation.navhost

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.locker.tictactoe.presentation.common.TicTacNavHost
import org.locker.tictactoe.presentation.compose.navigation.screen.GameScreen
import org.locker.tictactoe.presentation.compose.navigation.viewmodel.GameNavigationViewModel
import org.locker.tictactoe.presentation.compose.screens.game.screen.GameScreen
import org.locker.tictactoe.presentation.compose.screens.game.screen.NextGameScreen
import org.locker.tictactoe.presentation.theme.nextGameScreenTweenSpec
import org.koin.compose.koinInject

@Composable
fun GameNavHost(
    modifier: Modifier = Modifier,
    gameNavViewModel: GameNavigationViewModel = koinInject()
) {
    val navController = rememberNavController()

    val winner by gameNavViewModel.gameWinner.collectAsState()
    TicTacNavHost(
        startDestination = GameScreen.GameScreen.name,
        navController = navController,
        enterTransition = { fadeIn(nextGameScreenTweenSpec) },
        exitTransition = { fadeOut(nextGameScreenTweenSpec) },
        modifier = Modifier
    ) {
        composable(GameScreen.GameScreen.name) {
            GameScreen(
                onEndGameAction = { winner ->
                    gameNavViewModel.setGameWinner(winner)
                    navController.navigate(GameScreen.NextGameScreen.name)
                },
                modifier = modifier
            )
        }

        composable(GameScreen.NextGameScreen.name) {
            NextGameScreen(
                winner = winner,
                onNextGameClick = {
                    navController.navigate(GameScreen.GameScreen.name) {
                        popUpTo(navController.graph.findStartDestination().id)
                    }
                }
            )
        }
    }
}
