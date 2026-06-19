package com.locker.feature.colorpicker.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.locker.feature.component.ColorsRow
import com.locker.feature.core.theme.AppColorTheme
import com.locker.feature.core.theme.TicTacToeTheme


@Composable
fun ColorsContainer(
	colorsList: List<AppColorTheme>,
	onColorClick: (Int) -> Unit,
	modifier: Modifier = Modifier
) {
	val colors = TicTacToeTheme.colors

	Box(
		modifier = modifier
			.clip(RoundedCornerShape(50))
			.background(colors.accent.copy(alpha = 0.4f))
	) {
		ColorsRow(
			colorsList = colorsList,
			onColorClick = onColorClick,
		)
	}
}
