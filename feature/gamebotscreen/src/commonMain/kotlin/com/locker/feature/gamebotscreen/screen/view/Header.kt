package com.locker.feature.gamebotscreen.screen.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.locker.core.models.Player
import com.locker.feature.component.field.color
import com.locker.feature.core.theme.TicTacToeTheme
import com.locker.feature.gamebotscreen.screen.model.HeaderState

@Composable
fun Header(
	header: HeaderState,
	modifier: Modifier = Modifier
) {
	val colors = TicTacToeTheme.colors
	val typography = TicTacToeTheme.typography

	val playerColor = header.player?.color() ?: colors.accent
	val botColor = Player.entries.firstOrNull { it != header.player }?.color() ?: colors.additional

	Row(
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(
			space = 16.dp,
			alignment = Alignment.CenterHorizontally,
		),
		modifier = modifier
			.height(48.dp)
	) {
		Text(
			text = header.subtitle,
			color = if (header.isUserTurn) playerColor else botColor,
			style = typography.titleLarge,
		)

		if (!header.isUserTurn) {
			CircularProgressIndicator(
				color = botColor ,
				trackColor = botColor.copy(alpha = 0.2f),
				strokeCap = StrokeCap.Round,
				strokeWidth = 4.dp,
				modifier = Modifier
					.size(32.dp)
			)
		}
	}
}
