package com.locker.feature.tutorialscreen.screen

import androidx.lifecycle.viewModelScope
import com.locker.core.navigation.Navigator
import com.locker.feature.core.screen.BaseViewModel
import com.locker.feature.core.screen.ScreenEvent
import com.locker.feature.tutorialscreen.screen.event.TutorialBackEvent
import com.locker.feature.tutorialscreen.screen.factory.TutorialScreenStateFactory
import com.locker.feature.tutorialscreen.screen.model.TutorialScreenState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class TutorialScreenViewModel(
    private val navigator: Navigator
) : BaseViewModel() {

    val screenState: StateFlow<TutorialScreenState> = flow {
        emit(TutorialScreenStateFactory.create())
    }.stateIn(viewModelScope, SharingStarted.Eagerly, TutorialScreenState())

    override fun onEvent(event: ScreenEvent) {
        when (event) {
            is TutorialBackEvent -> navigator.goBack()
        }
    }
}
