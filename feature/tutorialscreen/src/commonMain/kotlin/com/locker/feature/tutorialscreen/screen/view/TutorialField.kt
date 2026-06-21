package com.locker.feature.tutorialscreen.screen.view

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.locker.core.gamelogic.model.BoardState
import com.locker.core.gamelogic.model.CellState
import com.locker.feature.component.field.GameBlockContainer
import com.locker.feature.component.field.GameCell
import com.locker.feature.component.field.MatrixFieldBlock
import com.locker.feature.component.modifier.Border
import com.locker.feature.component.modifier.matrixLine
import com.locker.feature.core.theme.DefaultShape
import com.locker.feature.core.theme.HALF_ALPHA
import com.locker.feature.core.theme.LOW_ALPHA
import com.locker.feature.core.theme.SUB_FIELD_LINE_LENGTH_PERCENT
import com.locker.feature.core.theme.TicTacToeTheme

@Composable
fun TutorialField(
	modifier: Modifier = Modifier,
	isBlockActive: (Int, Int) -> Boolean = { _, _ -> false },
	boardState: BoardState = BoardState.InProgress,
	blockState: (Int, Int) -> BoardState = { _, _ -> BoardState.InProgress },
	content: @Composable (Int, Int, Int, Int) -> Unit,
) {
	val colors = TicTacToeTheme.colors

	GameBlockContainer(
		dimensionSize = 3,
		border = Border(strokeWidth = 1.dp, color = colors.accent),
		boardState = BoardState.InProgress,
		showAlphaAnimation = false,
		showGameCell = false,
		modifier = modifier
			.fillMaxWidth()
			.aspectRatio(1f)
			.matrixLine(
				border = Border(strokeWidth = 8.dp, color = colors.accent),
				dimensionSize = 3,
				column = if (boardState is BoardState.Winner.Column) boardState.columnNum else null,
				row = if (boardState is BoardState.Winner.Row) boardState.rowNum else null,
				mainDiagonal = boardState is BoardState.Winner.MainDiagonal,
				sideDiagonal = boardState is BoardState.Winner.SideDiagonal
			)
	) { fieldI, fieldJ ->
		val blockBg by animateColorAsState(
			targetValue = if (isBlockActive(fieldI, fieldJ)) {
				colors.accent.copy(alpha = LOW_ALPHA)
			} else {
				Color.Transparent
			},
		)

		val winPlayer = blockState(fieldI, fieldJ)
		val hasWin = winPlayer is BoardState.Winner
		val blockAlpha by animateFloatAsState(
			targetValue = if (winPlayer == BoardState.InProgress) 1f else 0.2f
		)

		GameFieldBlock(
			modifier = Modifier
				.fillMaxSize()
				.padding(8.dp)
				.clip(DefaultShape)
				.background(blockBg)
				.alpha(blockAlpha)
		) { i, j ->
			content(fieldI, fieldJ, i, j)
		}

		Box(
			modifier = Modifier
				.alpha(if (hasWin) blockAlpha else 0f)
				.fillMaxSize()
				.padding(8.dp)
				.matrixLine(
					border = Border(strokeWidth = 4.dp, color = colors.accent),
					dimensionSize = 3,
					column = if (winPlayer is BoardState.Winner.Column) winPlayer.columnNum else null,
					row = if (winPlayer is BoardState.Winner.Row) winPlayer.rowNum else null,
					mainDiagonal = winPlayer is BoardState.Winner.MainDiagonal,
					sideDiagonal = winPlayer is BoardState.Winner.SideDiagonal
				)
		)

		GameCell(
			state = if (winPlayer is BoardState.Winner) winPlayer.winner else CellState.Empty,
			modifier = Modifier
				.fillMaxSize()
				.alpha(if (hasWin) 1f else 0f)
		)
	}
}

@Composable
fun GameFieldBlock(
	modifier: Modifier = Modifier,
	content: @Composable (Int, Int) -> Unit,
) {
	val colors = TicTacToeTheme.colors

	MatrixFieldBlock(
		dimensionSize = 3,
		border = Border(
			strokeWidth = (0.5).dp,
			color = colors.additional.copy(HALF_ALPHA),
			percentage = SUB_FIELD_LINE_LENGTH_PERCENT
		),
		modifier = modifier
			.clip(DefaultShape)
	) { i, j ->
		content(i, j)
	}
}
