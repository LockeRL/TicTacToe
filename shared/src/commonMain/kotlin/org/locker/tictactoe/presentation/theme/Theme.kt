package org.locker.tictactoe.presentation.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import org.locker.tictactoe.presentation.model.AppColors

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
fun AppColors.toThemeColorsWithAnimation(): ColorScheme = lightColorScheme(
    primary = accent.animateColor(),
    primaryContainer = accentContainer.animateColor(),
    background = background.animateColor(),
    secondary = additional.animateColor(),
    secondaryContainer = additionalContainer.animateColor()
)


@Composable
fun TicTacToeTheme(
    appColors: AppColors = AppColors1,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = appColors.toThemeColorsWithAnimation(),
        typography = Typography,
        content = content
    )
}
