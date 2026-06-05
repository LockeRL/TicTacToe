package com.locker.feature.gamescreen.controller.model

sealed interface CellState {
    data class Occupied(val player: Player?) : CellState
    data object Empty : CellState
}
