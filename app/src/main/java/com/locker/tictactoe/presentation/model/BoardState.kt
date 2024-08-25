package com.locker.tictactoe.presentation.model

sealed class BoardState {
    data object InProgress : BoardState()
    data object Draw : BoardState()

    sealed class Winner(open val winner: CellState) : BoardState() {
        data class MainDiagonal(override val winner: CellState) : Winner(winner)
        data class SideDiagonal(override val winner: CellState) : Winner(winner)
        data class Row(override val winner: CellState, val rowId: Int) : Winner(winner)
        data class Column(override val winner: CellState, val columnId: Int) : Winner(winner)
    }
}
