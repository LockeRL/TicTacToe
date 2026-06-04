package com.locker.feature.gamescreen.controller.model

sealed class BoardState {
    data object InProgress : BoardState()
    data object Draw : BoardState()

    sealed class Winner(open val winner: CellState) : BoardState() {
        data class MainDiagonal(override val winner: CellState) : Winner(winner)
        data class SideDiagonal(override val winner: CellState) : Winner(winner)
        data class Row(override val winner: CellState, val rowNum: Int) : Winner(winner)
        data class Column(override val winner: CellState, val columnNum: Int) : Winner(winner)
    }
}
