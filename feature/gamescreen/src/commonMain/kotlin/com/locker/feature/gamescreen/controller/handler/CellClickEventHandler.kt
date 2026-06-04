package com.locker.feature.gamescreen.controller.handler

import com.locker.feature.gamescreen.screen.event.CellClickEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class CellClickEventHandler {
    private val _eventFlow: MutableSharedFlow<CellClickEvent> = MutableSharedFlow()
    val eventFlow: SharedFlow<CellClickEvent> = _eventFlow

    suspend fun fireEvent(event: CellClickEvent) {
        _eventFlow.emit(event)
    }
}
