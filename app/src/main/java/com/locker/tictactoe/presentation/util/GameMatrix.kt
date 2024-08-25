package com.locker.tictactoe.presentation.util

import com.locker.tictactoe.presentation.model.BoardState
import com.locker.tictactoe.presentation.model.CellState
import com.locker.tictactoe.presentation.model.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class GameMatrix<T>(initValue: () -> T) {
    val dimensionSize = 3

    private val matrix: List<List<T>> =
        List(dimensionSize) { List(dimensionSize) { initValue() } }

    private val _winState: MutableStateFlow<BoardState> = MutableStateFlow(BoardState.InProgress)
    val winState: StateFlow<BoardState> = _winState

    abstract fun getCellStateFromItem(item: T): CellState

    operator fun get(i: Int, j: Int): T = matrix[i][j]

    fun getCellState(i: Int, j: Int): CellState = getCellStateFromItem(get(i, j))

    fun getAndUpdateBoardState(): BoardState {
        val winState = checkWinner()

        val finishState =
            if (winState is BoardState.Winner)
                winState
            else {
                if (isFull())
                    BoardState.Draw
                else
                    BoardState.InProgress
            }

        _winState.value = finishState
        return finishState
    }

    private fun isFull(): Boolean =
        matrix.flatten().all { getCellStateFromItem(it) is CellState.Occupied }

    private fun checkWinner(): BoardState {
        for (i in 0..<3) {
            val rowRes = checkRow(i)
            if (rowRes is CellState.Occupied)
                return BoardState.Winner.Row(rowRes, i)
        }

        for (i in 0..<3) {
            val columnRes = checkColumn(i)
            if (columnRes is CellState.Occupied)
                return BoardState.Winner.Column(columnRes, i)
        }

        val mainRes = checkMainDiagonal()
        if (mainRes is CellState.Occupied)
            return BoardState.Winner.MainDiagonal(mainRes)

        val sideRes = checkSideDiagonal()
        if (sideRes is CellState.Occupied)
            return BoardState.Winner.SideDiagonal(sideRes)

        return BoardState.InProgress
    }

    private fun checkRow(i: Int): CellState {
        val list = List(dimensionSize) { index ->
            getCellState(i, index)
        }
        return checkList(list)
    }

    private fun checkColumn(i: Int): CellState {
        val list = List(dimensionSize) { index ->
            getCellState(index, i)
        }
        return checkList(list)
    }

    private fun checkMainDiagonal(): CellState {
        val list = List(dimensionSize) { index ->
            getCellState(index, index)
        }
        return checkList(list)
    }

    private fun checkSideDiagonal(): CellState {
        val list = List(dimensionSize) { index ->
            getCellState(index, dimensionSize - index - 1)
        }
        return checkList(list)
    }

    private fun checkList(list: List<CellState>): CellState =
        if (list.all { it is CellState.Occupied && it.player == Player.CROSS })
            CellState.Occupied(Player.CROSS)
        else if (list.all { it is CellState.Occupied && it.player == Player.CIRCLE })
            CellState.Occupied(Player.CIRCLE)
        else CellState.Empty
}
