package com.locker.tictactoe.presentation.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.locker.tictactoe.presentation.model.AppColors

@Composable
private fun Color.animateColor(): Color {
    val animatedColor by animateColorAsState(
        targetValue = this,
        label = this.toString(),
        animationSpec = tween(THEME_COLORS_CHANGE_ANIM_DURATION)
    )
    return animatedColor
}

@Composable
fun AppColors.toThemeColorsWithAnimation() = lightColors(
    primary = accent.animateColor(),
    primaryVariant = accentVariant.animateColor(),
    background = background.animateColor(),
    secondary = additional.animateColor(),
    secondaryVariant = additionalVariant.animateColor()
)


@Composable
fun TicTacToeTheme(
    appColors: AppColors = AppColors1,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = appColors.toThemeColorsWithAnimation(),
        typography = Typography,
        content = content
    )
}
