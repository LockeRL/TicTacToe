package com.locker.feature.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.locker.core.models.Difficulty
import com.locker.core.models.Player
import com.locker.feature.core.theme.TicTacToeTheme
import com.locker.resources.Res
import com.locker.resources.ic_circle
import com.locker.resources.ic_cross
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun DifficultySelector(
	selectedDifficulty: Difficulty,
	onDifficultySelected: (Difficulty) -> Unit,
	modifier: Modifier = Modifier
) {
	val difficulties = Difficulty.entries
	val colors = TicTacToeTheme.colors
	val selectedIndex = difficulties.indexOf(selectedDifficulty)

	Box(
		modifier = modifier
			.fillMaxWidth()
			.height(120.dp),
		contentAlignment = Alignment.BottomCenter
	) {
		Canvas(modifier = Modifier.fillMaxSize()) {
			drawArc(
				color = colors.accent.copy(alpha = 0.1f),
				startAngle = 180f,
				sweepAngle = 180f,
				useCenter = true,
				size = Size(size.width, size.height * 2),
				topLeft = Offset(0f, 0f)
			)
		}

		BoxWithConstraints(
			modifier = Modifier
				.fillMaxWidth()
				.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
		) {
			val width = maxWidth
			val itemWidth = width / 3
			val indicatorOffset by animateFloatAsState(
				targetValue = selectedIndex * itemWidth.value,
				animationSpec = tween(300)
			)

			Box(
				modifier = Modifier
					.offset(x = indicatorOffset.dp)
					.size(width = itemWidth, height = 50.dp)
					.padding(4.dp)
					.background(colors.accent, RoundedCornerShape(16.dp))
			)

			Row(modifier = Modifier.fillMaxWidth()) {
				difficulties.forEach { difficulty ->
					Box(
						modifier = Modifier
							.weight(1f)
							.height(50.dp)
							.clickable(
								interactionSource = null,
								indication = null,
							) {
								onDifficultySelected(difficulty)
							},
						contentAlignment = Alignment.Center
					) {
						Text(
							text = difficulty.name.replaceFirstChar { it.uppercase() },
							color = if (selectedDifficulty == difficulty) colors.accentContainer else colors.accent,
							fontWeight = FontWeight.Bold,
							fontSize = 14.sp
						)
					}
				}
			}
		}
	}
}

@Composable
fun PlayerSelector(
	selectedPlayer: Player,
	onSymbolSelected: (Player) -> Unit,
	modifier: Modifier = Modifier
) {
	Row(
		horizontalArrangement = Arrangement.spacedBy(32.dp),
		verticalAlignment = Alignment.CenterVertically,
		modifier = modifier
	) {
		PlayerItem(
			iconRes = Res.drawable.ic_cross,
			isSelected = selectedPlayer == Player.CROSS,
			onClick = { onSymbolSelected(Player.CROSS) }
		)
		PlayerItem(
			iconRes = Res.drawable.ic_circle,
			isSelected = selectedPlayer == Player.CIRCLE,
			onClick = { onSymbolSelected(Player.CIRCLE) }
		)
	}
}

@Composable
fun PlayerItem(
	iconRes: DrawableResource,
	isSelected: Boolean,
	onClick: () -> Unit
) {
	val colors = TicTacToeTheme.colors
	Box(
		modifier = Modifier
			.size(64.dp)
			.clip(CircleShape)
			.background(if (isSelected) colors.accent else colors.accent.copy(alpha = 0.1f))
			.clickable { onClick() }
			.padding(16.dp),
		contentAlignment = Alignment.Center
	) {
		Icon(
			painter = painterResource(iconRes),
			contentDescription = null,
			tint = if (isSelected) colors.accentContainer else colors.accent,
			modifier = Modifier.fillMaxSize()
		)
	}
}
