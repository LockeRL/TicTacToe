package com.locker.feature.gamescreen.screen

import androidx.lifecycle.viewModelScope
import com.locker.core.navigation.Navigator
import com.locker.core.navigation.keys.MainScreenNavKey
import com.locker.core.gamelogic.controller.GameController
import com.locker.core.gamelogic.model.BoardState
import com.locker.core.gamelogic.model.CellState
import com.locker.feature.core.screen.BaseViewModel
import com.locker.feature.core.screen.ScreenEvent
import com.locker.feature.gamescreen.screen.event.CellClickEvent
import com.locker.feature.gamescreen.screen.event.MainMenuEvent
import com.locker.feature.gamescreen.screen.event.NextGameEvent
import com.locker.feature.gamescreen.screen.factory.EndGameScreenStateFactory
import com.locker.feature.gamescreen.screen.model.EndGameScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class GameScreenViewModel(
	val gameController: GameController,
	private val navigator: Navigator,
) : BaseViewModel() {

	private val _endScreen: MutableStateFlow<EndGameScreenState> =
		MutableStateFlow(EndGameScreenState())
	val endScreen: StateFlow<EndGameScreenState> = _endScreen

	val field = gameController.field
	val activePlayer = gameController.activePlayer

	val gameEndJob = field.winState.onEach { boardState ->
		if (boardState != BoardState.InProgress) {
			_endScreen.value = EndGameScreenStateFactory.create(
				winner = ((field.winState.value as? BoardState.Winner)?.winner as? CellState.Occupied)?.player
			)
		}
	}
		.launchIn(viewModelScope)


	override fun onEvent(event: ScreenEvent) = when (event) {

		is CellClickEvent -> {
			gameController.onCellClick(
				fieldI = event.fieldI,
				fieldJ = event.fieldJ,
				blockI = event.blockI,
				blockJ = event.blockJ
			)
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
