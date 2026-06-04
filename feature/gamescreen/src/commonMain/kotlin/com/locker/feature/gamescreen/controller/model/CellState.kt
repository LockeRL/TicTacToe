package com.locker.feature.gamescreen.controller.model

import com.locker.models.Player

sealed interface CellState {
    data class Occupied(val player: Player?) : CellState
    data object Empty : CellState
}
