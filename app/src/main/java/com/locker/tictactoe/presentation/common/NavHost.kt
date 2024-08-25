package com.locker.tictactoe.presentation.common

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.locker.tictactoe.presentation.theme.LeftExitTransition
import com.locker.tictactoe.presentation.theme.RightEnterTransition

@Composable
fun TicTacNavHost(
    startDestination: String,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = RightEnterTransition,
    exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = LeftExitTransition,
    builder: NavGraphBuilder.(NavHostController) -> Unit
) {
    NavHost(
        navController = navController,
        modifier = modifier
            .background(Color.Black),
        startDestination = startDestination,
        enterTransition = enterTransition,
        exitTransition = exitTransition
    ) {
        builder(navController)
    }
}
