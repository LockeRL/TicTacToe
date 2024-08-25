package com.locker.tictactoe.presentation.util

import com.locker.tictactoe.presentation.model.BoardState
import com.locker.tictactoe.presentation.model.CellState

class GameField : GameMatrix<GameBlock>(::GameBlock) {
    override fun getCellStateFromItem(item: GameBlock): CellState =
        when (val winState = item.winState.value) {
            is BoardState.Winner -> winState.winner
            is BoardState.Draw -> CellState.Occupied(null)
            else -> CellState.Empty
        }

    fun setActiveFieldState(i: Int, j: Int, state: Boolean) {
        get(i, j).setActiveState(state)
    }

}
