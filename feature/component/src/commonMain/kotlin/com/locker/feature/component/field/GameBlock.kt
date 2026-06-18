package com.locker.feature.component.field

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.locker.core.gamelogic.model.BoardState
import com.locker.core.gamelogic.model.CellState
import com.locker.feature.component.modifier.Border
import com.locker.feature.component.modifier.border
import com.locker.feature.component.modifier.matrixLine
import com.locker.feature.core.theme.FADE_WIN_BLOCK_TIME
import com.locker.feature.core.theme.TicTacToeTheme
import kotlin.math.max

@Composable
fun MatrixFieldBlock(
    dimensionSize: Int,
    border: Border,
    modifier: Modifier = Modifier,
    content: @Composable (Int, Int) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        for (i in 0..<dimensionSize) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                for (j in 0..<dimensionSize) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .border(
                                start = if (j != 0) border else null,
                                top = if (i != 0) border else null,
                                end = if (j != dimensionSize - 1) border else null,
                                bottom = if (i != dimensionSize - 1) border else null
                            )
                    ) {
                        content(i, j)
                    }
                }
            }
        }
    }
}

@Composable
fun GameBlockContainer(
    dimensionSize: Int,
    border: Border,
    modifier: Modifier = Modifier,
    boardState: BoardState = BoardState.InProgress,
    showAlphaAnimation: Boolean = true,
    showGameCell: Boolean = true,
    showWinAfterAnimation: Boolean = false,
    content: @Composable (Int, Int) -> Unit
) {
    val colors = TicTacToeTheme.colors

    val winAlpha by animateFloatAsState(
        targetValue = if (boardState == BoardState.InProgress) 0f else 1f,
        label = "winner_block",
        animationSpec = tween(durationMillis = FADE_WIN_BLOCK_TIME)
    )

    val shadowAlpha = max(0.2f, 1f - if (showAlphaAnimation) winAlpha else 0f)

    Box(modifier = modifier) {
        MatrixFieldBlock(
            dimensionSize = dimensionSize,
            border = border,
            content = content,
            modifier = Modifier
                .fillMaxSize()
                .alpha(shadowAlpha)
        )

        if (boardState != BoardState.InProgress) {
            if (!showWinAfterAnimation || winAlpha == 0f) {
                Box(
                    modifier = Modifier
                        .alpha(shadowAlpha)
                        .fillMaxSize()
                        .matrixLine(
                            border = Border(strokeWidth = 4.dp, color = colors.accent),
                            dimensionSize = dimensionSize,
                            column = if (boardState is BoardState.Winner.Column) boardState.columnNum else null,
                            row = if (boardState is BoardState.Winner.Row) boardState.rowNum else null,
                            mainDiagonal = boardState is BoardState.Winner.MainDiagonal,
                            sideDiagonal = boardState is BoardState.Winner.SideDiagonal
                        )
                )
            }

            if (showGameCell) {
                GameCell(
                    state = if (boardState is BoardState.Winner) boardState.winner else CellState.Empty,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(if (showAlphaAnimation) winAlpha else 0f)
                )
            }
        }
    }
}
