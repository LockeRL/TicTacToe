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
import kotlinx.coroutines.isActive

@Composable
fun TutorialFreeMove() {
	var step by remember { mutableStateOf(0) }

	LaunchedEffect(Unit) {
		while (isActive) {
			step = 0
			delay(1000)
			step = 1 // Move that sends to finished block
			delay(500)
			step = 2 // Highlight all free blocks
			delay(1500)
		}
	}

	Box(modifier = Modifier.fillMaxSize()) {
		TutorialField(
			isBlockActive = { i, j ->
				val isMiddleBlock = i == 1 && j == 1
				step >= 2 && !isMiddleBlock
			},
			blockState = { i, j ->
				if (i == 1 && j == 1) {
					BoardState.Winner.MainDiagonal(CellState.Occupied(Player.CIRCLE))
				} else {
					BoardState.InProgress
				}
			}
		) { fieldI, fieldJ, i, j ->
			when {
				step >= 1 && fieldI == 2 && fieldJ == 0 && i == 1 && j == 1 -> {
					GameCell(
						state = CellState.Occupied(Player.CROSS),
						modifier = Modifier.fillMaxSize()
					)
				}

				fieldI == 1 && fieldJ == 1 && i == j -> {
					GameCell(
						state = CellState.Occupied(Player.CIRCLE),
						modifier = Modifier.fillMaxSize()
					)
				}

				else -> {
					Box(Modifier.fillMaxSize())
				}
			}
		}
	}
}
