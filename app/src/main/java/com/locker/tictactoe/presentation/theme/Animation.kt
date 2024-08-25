package com.locker.tictactoe.presentation.theme

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry

val RightEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition
    @Composable
    get() = {
        fadeIn(animationSpec = tween(SCREEN_CHANGE_ANIM_DURATION)) + slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.Right, tween(SCREEN_CHANGE_ANIM_DURATION)
        )
    }

val LeftExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition
    @Composable
    get() = {
        fadeOut(animationSpec = tween(SCREEN_CHANGE_ANIM_DURATION)) + slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Left, tween(SCREEN_CHANGE_ANIM_DURATION)
        )
    }

val nextGameScreenTweenSpec: TweenSpec<Float> =
    tween(durationMillis = NEXT_GAME_SCREEN_CHANGE_ANIM_DURATION)
