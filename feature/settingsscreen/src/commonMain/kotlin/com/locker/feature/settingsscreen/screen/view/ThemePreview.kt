package com.locker.feature.settingsscreen.screen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.locker.core.models.Player
import com.locker.feature.component.TicTacToeIconButton
import com.locker.feature.component.field.MatrixFieldBlock
import com.locker.feature.component.field.PlayerIcon
import com.locker.feature.component.modifier.Border
import com.locker.feature.component.modifier.matrixLine
import com.locker.feature.core.theme.AppColorTheme
import com.locker.feature.core.theme.HALF_ALPHA
import com.locker.feature.core.theme.SUB_FIELD_LINE_LENGTH_PERCENT
import com.locker.feature.core.theme.TicTacToeTheme
import com.locker.resources.Res
import com.locker.resources.ic_settings
import org.jetbrains.compose.resources.painterResource

@Composable
fun ThemePreview(theme: AppColorTheme) {
	val appColors = TicTacToeTheme.colors

	Row(
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(16.dp),
		modifier = Modifier
			.clip(RoundedCornerShape(24.dp))
			.background(theme.background)
			.border(4.dp, appColors.accent, RoundedCornerShape(24.dp))
			.padding(20.dp)
	) {
		Box(
			modifier = Modifier
				.size(160.dp),
			contentAlignment = Alignment.Center
		) {
			MatrixFieldBlock(
				dimensionSize = 3,
				border = Border(
					strokeWidth = (0.8).dp,
					color = theme.additional.copy(HALF_ALPHA),
					percentage = SUB_FIELD_LINE_LENGTH_PERCENT
				),
				modifier = Modifier
					.fillMaxSize()
					.matrixLine(
						border = Border(strokeWidth = 4.dp, color = theme.additional),
						dimensionSize = 3,
						mainDiagonal = true,
					)
			) { i, j ->
				Box(modifier = Modifier.fillMaxSize()) {
					when {
						i == j -> {
							PlayerIcon(
								player = Player.CIRCLE,
								color = theme.additional,
								modifier = Modifier
									.fillMaxSize()
							)
						}

						i + j == 3 -> {
							PlayerIcon(
								player = Player.CROSS,
								color = theme.accent,
								modifier = Modifier
									.fillMaxSize()
							)
						}
					}
				}
			}
		}

		TicTacToeIconButton(
			icon = painterResource(Res.drawable.ic_settings),
			shape = CircleShape,
			onClick = { },
			contentDescription = "SettingsPreview",
			contentColor = theme.additionalContainer,
			containerColor = theme.background,
			modifier = Modifier.size(32.dp),
		)
	}
}
