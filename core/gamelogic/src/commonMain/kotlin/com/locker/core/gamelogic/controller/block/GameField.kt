package com.locker.core.gamelogic.controller.block

import com.locker.core.gamelogic.model.BoardState
import com.locker.core.gamelogic.model.CellState

class GameField : GameMatrix<GameBlock>(::GameBlock) {
    override fun getCellStateFromItem(item: GameBlock): CellState =
        when (val winState = item.winState.value) {
            is BoardState.Winner -> winState.winner
            is BoardState.Draw -> CellState.Occupied(null)
            else -> CellState.Empty
        }

    override fun resetItem(item: GameBlock) {
        item.reset()
    }

    fun setActiveFieldState(i: Int, j: Int, state: Boolean) {
        get(i, j).setActiveState(state)
    }

}
