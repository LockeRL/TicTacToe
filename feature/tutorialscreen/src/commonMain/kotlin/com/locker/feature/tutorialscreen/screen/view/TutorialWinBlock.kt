package com.locker.feature.tutorialscreen.screen.view

import androidx.compose.foundation.layout.Box
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

@Composable
fun TutorialWinBlock(
	modifier: Modifier = Modifier
) {
	var step by remember { mutableStateOf(0) }

	LaunchedEffect(Unit) {
		while (true) {
			step = 0
			delay(2000)
			step = 1 // Win central block
			delay(1500)
		}
	}

	val crosses = listOf(
		1 to 1,
		1 to 0,
	)

	val circles = listOf(
		0 to 0,
		0 to 1,
	)

	TutorialField(
		modifier = modifier,
		isBlockActive = { i, j ->
			val isMiddleBlock = i == 1 && j == 1
			step < 1 && isMiddleBlock
		},
		blockState = { i, j ->
			if (i == 1 && j == 1 && step == 1) {
				BoardState.Winner.Row(winner = CellState.Occupied(Player.CROSS), rowNum = 1)
			} else {
				BoardState.InProgress
			}
		}
	) { fieldI, fieldJ, i, j ->
		if (fieldI == 1 && fieldJ == 1) {
			GameCell(
				state = when {
					i to j in crosses -> CellState.Occupied(Player.CROSS)
					i to j in circles -> CellState.Occupied(Player.CIRCLE)
					step == 1 && i == 1 && j == 2 -> CellState.Occupied(Player.CROSS)
					else -> CellState.Empty
				},
				modifier = Modifier.fillMaxSize()
			)
		} else {
			Box(Modifier.fillMaxSize())
		}
	}
}
