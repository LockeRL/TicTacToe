package com.locker.feature.gamescreen.screen

import androidx.lifecycle.viewModelScope
import com.locker.core.navigation.Navigator
import com.locker.core.navigation.keys.MainScreenNavKey
import com.locker.feature.core.screen.BaseViewModel
import com.locker.feature.core.screen.ScreenEvent
import com.locker.feature.gamescreen.controller.GameController
import com.locker.feature.gamescreen.controller.handler.CellClickEventHandler
import com.locker.feature.gamescreen.screen.event.CellClickEvent
import com.locker.feature.gamescreen.screen.event.GameEnd
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class GameViewModel(
	val gameController: GameController,
	private val eventHandler: CellClickEventHandler,
	private val navigator: Navigator,
) : BaseViewModel() {

	val eventsJob = gameController.eventsFlow.launchIn(viewModelScope)

	val field = gameController.field
	val activePlayer = gameController.activePlayer

	override fun onEvent(event: ScreenEvent) = when(event) {
		is GameEnd -> {
			navigator.navigate(MainScreenNavKey)
		}
		is CellClickEvent -> {
			viewModelScope.launch {
				eventHandler.fireEvent(event)
			}
		}
		else -> Unit
	}
}
