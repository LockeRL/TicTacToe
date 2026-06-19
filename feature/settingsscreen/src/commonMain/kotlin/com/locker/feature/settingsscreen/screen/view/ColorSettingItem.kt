package com.locker.feature.settingsscreen.screen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.locker.feature.core.theme.TicTacToeTheme

@Composable
fun ColorSettingItem(
	label: String,
	color: Color,
	modifier: Modifier = Modifier,
	onClick: () -> Unit,
) {
	val colors = TicTacToeTheme.colors
	val typography = TicTacToeTheme.typography
	Row(
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween,
		modifier = modifier
			.clickable {
				onClick()
			}
			.background(
				color = colors.accent.copy(alpha = 0.1f),
				shape = RoundedCornerShape(12.dp),
			)
			.padding(16.dp)
	) {
		Text(
			text = label,
			style = typography.bodyLarge,
			color = colors.accentContainer
		)

		Box(
			modifier = Modifier
				.size(32.dp)
				.clip(CircleShape)
				.background(color)
		)
	}
}
