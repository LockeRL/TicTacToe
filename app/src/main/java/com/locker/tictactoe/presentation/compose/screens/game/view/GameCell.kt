package com.locker.tictactoe.presentation.compose.screens.game.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.locker.tictactoe.presentation.model.CellState

@Composable
fun GameCell(
    state: CellState,
    modifier: Modifier = Modifier,
    onCellClick: () -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.clickable { onCellClick() }
    ) {
        val player = (state as? CellState.Occupied)?.player
        if (player != null) {
            PlayerIcon(player = player, modifier = Modifier.fillMaxSize())
        }
    }
}
