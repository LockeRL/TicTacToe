package com.locker.feature.core.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val LocalAppColors = staticCompositionLocalOf<AppColorTheme> {
	error("LocalAppColors not provided")
}

private val LocalTypography = staticCompositionLocalOf<Typography> {
	error("LocalTypography not provided")
}

object TicTacToeTheme {
	val colors: AppColorTheme
		@Composable
		@ReadOnlyComposable
		get() = LocalAppColors.current

	val typography: Typography
		@Composable
		@ReadOnlyComposable
		get() = LocalTypography.current
}

@Composable
private fun Color.animateColor(): Color {
	val animatedColor by animateColorAsState(
		targetValue = this,
		label = "color_animation",
		animationSpec = tween(THEME_COLORS_CHANGE_ANIM_DURATION)
	)
	return animatedColor
}

@Composable
fun AppColorTheme.animate(): AppColorTheme = AppColorTheme(
	background = background.animateColor(),
	accent = accent.animateColor(),
	additional = additional.animateColor(),
	additionalContainer = additionalContainer.animateColor(),
	accentContainer = accentContainer.animateColor(),
)

@Composable
fun TicTacToeTheme(
	appColors: AppColorTheme = AppColorTheme.DEFAULT,
	content: @Composable () -> Unit
) {
	val animatedColors = appColors.animate()

	CompositionLocalProvider(
		LocalAppColors provides animatedColors,
		LocalTypography provides Typography,
	) {
		content()
	}
}
