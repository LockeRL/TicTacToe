package com.locker.feature.tutorialscreen.screen.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.locker.core.gamelogic.model.BoardState
import com.locker.core.gamelogic.model.CellState
import com.locker.core.models.Player
import com.locker.feature.component.field.GameCell
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
fun TutorialHowToWin(
	modifier: Modifier = Modifier
) {
	var step by remember { mutableStateOf(0) }

	LaunchedEffect(Unit) {
		while (isActive) {
			step = 0
			delay(2000)
			step = 1 // Win center right block and game
			delay(1500)
		}
	}

	val crossesMap = mapOf(
		(1 to 0) to listOf(
			2 to 0,
			2 to 1,
			2 to 2,
		),
		(1 to 1) to listOf(
			2 to 0,
			2 to 1,
			2 to 2,
		),
		(1 to 2) to listOf(
			2 to 0,
			2 to 1,
		)
	)

	val circlesMap = mapOf(
		(2 to 0) to listOf(
			1 to 0,
			1 to 1,
			1 to 2,
		),
		(2 to 1) to listOf(
			1 to 0,
			1 to 1,
			1 to 2,
		),
		(2 to 2) to listOf(
			1 to 0,
			1 to 1,
		)
	)

	TutorialField(
		modifier = modifier,
		isBlockActive = { i, j ->
			step < 1 && i == 1 && j == 2
		},
		boardState = if (step == 1) {
			BoardState.Winner.Row(winner = CellState.Occupied(Player.CROSS), rowNum = 1)
		} else {
			BoardState.InProgress
		},
		blockState = { i, j ->
			val crossesList = crossesMap[i to j] ?: emptyList()
			val circlesList = circlesMap[i to j] ?: emptyList()
			when {
				crossesList.size == 3 -> BoardState.Winner.Row(
					winner = CellState.Occupied(Player.CROSS),
					rowNum = 2,
				)

				circlesList.size == 3 -> BoardState.Winner.Row(
					winner = CellState.Occupied(Player.CIRCLE),
					rowNum = 1,
				)

				step == 1 && i == 1 && j == 2 -> BoardState.Winner.Row(
					winner = CellState.Occupied(Player.CROSS),
					rowNum = 2,
				)

				else -> BoardState.InProgress
			}
		}
	) { fieldI, fieldJ, i, j ->
		GameCell(
			state = when {
				(i to j) in crossesMap.getOrElse(
					key = fieldI to fieldJ, defaultValue = { emptyList() }
				) -> CellState.Occupied(Player.CROSS)

				(i to j) in circlesMap.getOrElse(
					key = fieldI to fieldJ, defaultValue = { emptyList() }
				) -> CellState.Occupied(Player.CIRCLE)

				step == 1 && fieldI == 1 && fieldJ == 2 && i == 2 && j == 2 ->
					CellState.Occupied(Player.CROSS)

				else -> CellState.Empty
			},
			modifier = Modifier.fillMaxSize()
		)
	}
}
