package com.locker.feature.core.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
	private val eventsFlow: MutableSharedFlow<ScreenEvent> = MutableSharedFlow(
		extraBufferCapacity = 1,
		onBufferOverflow = BufferOverflow.DROP_OLDEST,
	)

	init {
		eventsFlow.onEach {
			onEvent(it)
		}.launchIn(viewModelScope)
	}

	abstract fun onEvent(event: ScreenEvent): Any

	fun fireEvent(event: ScreenEvent) {
		viewModelScope.launch {
			eventsFlow.emit(event)
		}
	}
}
