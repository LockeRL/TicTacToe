package com.locker.tictactoe.presentation.model

sealed interface CellState {
    data class Occupied(val player: Player?) : CellState
    data object Empty : CellState
}
