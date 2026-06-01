package org.locker.tictactoe.presentation.compose.screens.game.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.locker.tictactoe.presentation.compose.screens.game.events.CellClickEvent
import com.locker.tictactoe.presentation.util.CellClickEventHandler
import com.locker.tictactoe.presentation.util.GameLogic
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class GameViewModel(
    val logic: GameLogic,
    private val eventHandler: CellClickEventHandler
) : ViewModel() {

    val eventsJob = logic.eventsJob.launchIn(viewModelScope)

    fun clickCell(event: CellClickEvent) {
        viewModelScope.launch {
            eventHandler.fireClick(event)
        }
    }
}
