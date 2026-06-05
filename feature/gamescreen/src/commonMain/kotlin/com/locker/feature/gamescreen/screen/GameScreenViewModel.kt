package com.locker.feature.gamescreen.screen

import androidx.lifecycle.viewModelScope
import com.locker.core.navigation.Navigator
import com.locker.core.navigation.keys.MainScreenNavKey
import com.locker.feature.core.screen.BaseViewModel
import com.locker.feature.core.screen.ScreenEvent
import com.locker.feature.gamescreen.controller.GameController
import com.locker.feature.gamescreen.controller.handler.CellClickEventHandler
import com.locker.feature.gamescreen.controller.model.BoardState
import com.locker.feature.gamescreen.controller.model.CellState
import com.locker.feature.gamescreen.screen.event.CellClickEvent
import com.locker.feature.gamescreen.screen.event.GameEndEvent
import com.locker.feature.gamescreen.screen.event.MainMenuEvent
import com.locker.feature.gamescreen.screen.event.NextGameEvent
import com.locker.feature.gamescreen.screen.factory.EndGameScreenStateFactory
import com.locker.feature.gamescreen.screen.model.EndGameScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class GameScreenViewModel(
	val gameController: GameController,
	private val eventHandler: CellClickEventHandler,
	private val navigator: Navigator,
) : BaseViewModel() {

	val eventsJob = gameController.eventsFlow.launchIn(viewModelScope)

	private val _endScreen: MutableStateFlow<EndGameScreenState> =
		MutableStateFlow(EndGameScreenState())
	val endScreen: StateFlow<EndGameScreenState> = _endScreen

	val field = gameController.field
	val activePlayer = gameController.activePlayer


	override fun onEvent(event: ScreenEvent) = when (event) {
		is GameEndEvent -> {
			viewModelScope.launch {
				_endScreen.value = EndGameScreenStateFactory.create(
					winner = ((field.winState.value as? BoardState.Winner)?.winner as? CellState.Occupied)?.player
				)
			}

		}

		is CellClickEvent -> {
			viewModelScope.launch {
				eventHandler.fireEvent(event)
			}
		}

		is NextGameEvent -> {
			reset()
		}

		is MainMenuEvent -> {
			navigator.navigate(MainScreenNavKey)
		}

		else -> Unit
	}

	private fun reset() {
		gameController.reset()
	}
}
