package org.locker.tictactoe.presentation.compose.screens.game.events

data class CellClickEvent(
    val fieldI: Int,
    val fieldJ: Int,
    val blockI: Int,
    val blockJ: Int
)
