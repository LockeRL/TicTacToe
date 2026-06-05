package com.locker.feature.mainscreen.screen

import androidx.lifecycle.viewModelScope
import com.locker.core.navigation.Navigator
import com.locker.core.navigation.keys.GameScreenNavKey
import com.locker.feature.core.screen.BaseViewModel
import com.locker.feature.core.screen.ScreenEvent
import com.locker.feature.mainscreen.screen.event.PlayClickEvent
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

	override fun onEvent(event: ScreenEvent) = when(event) {
		is PlayClickEvent -> {
			navigator.navigate(GameScreenNavKey)
		}
		else -> Unit
	}
}
