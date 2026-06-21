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
import com.locker.core.gamelogic.model.CellState
import com.locker.core.models.Player
import com.locker.feature.component.field.GameCell
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
fun TutorialNextMove() {
	var step by remember { mutableStateOf(0) }

	LaunchedEffect(Unit) {
		while (isActive) {
			step = 0
			delay(1000)
			step = 1 // Player moves in middle-right block, top-left cell
			delay(500)
			step = 2 // Highlight top-left block
			delay(1500)
		}
	}

	val highlightedBlockRow = 0
	val highlightedBlockCol = 0

	Box(modifier = Modifier.fillMaxSize()) {
		TutorialField(
			isBlockActive = { i, j ->
				step >= 2 && i == highlightedBlockRow && j == highlightedBlockCol
			},
		) { fieldI, fieldJ, i, j ->
			if (step >= 1 && fieldI == 1 && fieldJ == 2 && i == 0 && j == 0) {
				GameCell(
					state = CellState.Occupied(Player.CROSS),
					modifier = Modifier.fillMaxSize()
				)
			} else {
				Box(Modifier.fillMaxSize())
			}
		}
	}
}
