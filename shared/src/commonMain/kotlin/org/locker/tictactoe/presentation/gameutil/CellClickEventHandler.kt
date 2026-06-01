package com.locker.tictactoe.presentation.util

import org.locker.tictactoe.presentation.compose.screens.game.events.CellClickEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class CellClickEventHandler {
    private val _eventFlow: MutableSharedFlow<CellClickEvent> = MutableSharedFlow()
    val eventFlow: SharedFlow<CellClickEvent> = _eventFlow

    suspend fun fireClick(event: CellClickEvent) {
        _eventFlow.emit(event)
    }
}
