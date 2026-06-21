package com.locker.feature.mainscreen.screen

import androidx.lifecycle.viewModelScope
import com.locker.core.models.Difficulty
import com.locker.core.models.Player
import com.locker.core.navigation.Navigator
import com.locker.core.navigation.keys.BotGameScreenNavKey
import com.locker.core.navigation.keys.GameScreenNavKey
import com.locker.core.navigation.keys.TutorialScreenNavKey
import com.locker.core.navigation.keys.navmodel.DifficultyNavModel
import com.locker.core.navigation.keys.navmodel.PlayerNavModel
import com.locker.feature.core.screen.BaseViewModel
import com.locker.feature.core.screen.ScreenEvent
import com.locker.feature.mainscreen.screen.event.BotPlayClickEvent
import com.locker.feature.mainscreen.screen.event.PlayClickEvent
import com.locker.feature.mainscreen.screen.event.RulesClickEvent
import com.locker.feature.mainscreen.screen.factory.MainScreenStateFactory
import com.locker.feature.mainscreen.screen.model.MainScreenState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class MainScreenViewModel(
	private val navigator: Navigator,
) : BaseViewModel() {

	val screenState: StateFlow<MainScreenState> = flow {
		emit(MainScreenStateFactory.create())
	}.stateIn(viewModelScope, SharingStarted.Eagerly, MainScreenState())

	override fun onEvent(event: ScreenEvent) = when (event) {
		is PlayClickEvent -> {
			navigator.navigate(GameScreenNavKey)
		}

		is RulesClickEvent -> {
			navigator.navigate(TutorialScreenNavKey)
		}

		is BotPlayClickEvent -> {
			navigator.navigate(
				BotGameScreenNavKey(
					difficulty = when (event.difficulty) {
						Difficulty.EASY -> DifficultyNavModel.EASY
						Difficulty.MEDIUM -> DifficultyNavModel.MEDIUM
						Difficulty.HARD -> DifficultyNavModel.HARD
					},
					playerSymbol = when (event.player) {
						Player.CROSS -> PlayerNavModel.CROSS
						Player.CIRCLE -> PlayerNavModel.CIRCLE
					},
				)
			)
		}

		else -> Unit
	}
}
