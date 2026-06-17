package com.locker.core.logic.model

sealed interface CellState {
    data class Occupied(val player: Player?) : CellState
    data object Empty : CellState
}
