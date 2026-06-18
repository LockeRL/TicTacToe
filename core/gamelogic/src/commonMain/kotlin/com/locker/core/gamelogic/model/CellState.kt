package com.locker.core.gamelogic.model

import com.locker.core.models.Player

sealed interface CellState {
    data class Occupied(val player: Player?) : CellState
    data object Empty : CellState
}
