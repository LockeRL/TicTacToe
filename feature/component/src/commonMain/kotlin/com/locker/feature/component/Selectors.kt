package com.locker.feature.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.locker.core.models.Difficulty
import com.locker.core.models.Player
import com.locker.feature.component.modifier.clickableWithoutIndication
import com.locker.feature.core.theme.TicTacToeTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun DifficultySelector(
	selectedDifficulty: Difficulty,
	onDifficultySelected: (Difficulty) -> Unit,
	modifier: Modifier = Modifier
) {
	val colors = TicTacToeTheme.colors
	val typography = TicTacToeTheme.typography
	val difficulties = Difficulty.entries
	val selectedIndex = difficulties.indexOf(selectedDifficulty)
	val height = 48.dp

	BoxWithConstraints(
		modifier = modifier
			.height(height)
			.clip(RoundedCornerShape(16.dp))
			.background(colors.accent.copy(alpha = 0.1f))
			.padding(4.dp)
	) {
		val width = maxWidth
		val itemWidth = width / difficulties.size
		val indicatorHeight = height - 8.dp
		val indicatorOffset = animateDpAsState(
			targetValue = itemWidth * selectedIndex,
			animationSpec = tween(300)
		)

		Box(
			modifier = Modifier
				.offset(x = indicatorOffset.value)
				.size(width = itemWidth, height = indicatorHeight)
				.background(colors.accent, RoundedCornerShape(12.dp))
		)

		Row(modifier = Modifier.fillMaxWidth()) {
			difficulties.forEach { difficulty ->
				Box(
					modifier = Modifier
						.weight(1f)
						.height(indicatorHeight)
						.clickableWithoutIndication {
							onDifficultySelected(difficulty)
						},
					contentAlignment = Alignment.Center
				) {
					Text(
						text = stringResource(difficulty.title).uppercase(),
						color = if (selectedDifficulty == difficulty) colors.accentContainer else colors.accent,
						fontWeight = FontWeight.Bold,
						style = typography.bodyMedium,
					)
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
	val players = Player.entries
	val colors = TicTacToeTheme.colors
	val selectedIndex = players.indexOf(selectedPlayer)
	
	val itemSize = 64.dp
	val innerPadding = 8.dp
	val spacing = 16.dp
	
	val containerHeight = itemSize + (innerPadding * 2)
	val containerWidth = (itemSize * players.size) + (spacing * (players.size - 1)) + (innerPadding * 2)

	Box(
		modifier = modifier
			.size(width = containerWidth, height = containerHeight)
			.clip(RoundedCornerShape(20.dp))
			.background(colors.accent.copy(alpha = 0.1f))
			.padding(innerPadding)
	) {
		val indicatorOffset by animateDpAsState(
			targetValue = (itemSize + spacing) * selectedIndex,
			animationSpec = tween(300)
		)

		Box(
			modifier = Modifier
				.offset(x = indicatorOffset)
				.size(itemSize)
				.background(colors.accent, RoundedCornerShape(16.dp))
		)

		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.spacedBy(spacing)
		) {
			players.forEach { player ->
				val isSelected = selectedPlayer == player
				Box(
					modifier = Modifier
						.size(itemSize)
						.clickableWithoutIndication {
							onSymbolSelected(player)

						},
					contentAlignment = Alignment.Center
				) {
					Icon(
						painter = painterResource(player.icon),
						contentDescription = null,
						tint = if (isSelected) colors.accentContainer else colors.accent,
						modifier = Modifier.size(48.dp)
					)
				}
			}
		}
	}
}
