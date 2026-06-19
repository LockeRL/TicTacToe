package com.locker.feature.settingsscreen.screen

import androidx.lifecycle.viewModelScope
import com.locker.core.data.repository.IThemeRepository
import com.locker.core.models.AppColors
import com.locker.feature.core.screen.BaseViewModel
import com.locker.feature.core.screen.ScreenEvent
import com.locker.feature.core.theme.AppColorTheme
import com.locker.feature.settingsscreen.screen.event.SaveThemeEvent
import com.locker.feature.settingsscreen.screen.event.UpdateColorsEvent
import com.locker.feature.settingsscreen.screen.factory.SettingsScreenStateFactory
import com.locker.feature.settingsscreen.screen.model.SettingsScreenState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
	private val themeRepository: IThemeRepository
) : BaseViewModel() {

	private val _currentEditTheme = MutableSharedFlow<AppColorTheme>(extraBufferCapacity = 1)
	val currentEditTheme: SharedFlow<AppColorTheme> = _currentEditTheme

	val settingsStrings: StateFlow<SettingsScreenState> = flow {
		emit(SettingsScreenStateFactory.create())
	}.stateIn(viewModelScope, SharingStarted.Eagerly, SettingsScreenState())

	override fun onEvent(event: ScreenEvent): Any = when (event) {
		is SaveThemeEvent -> {
			saveTheme()
		}

		is UpdateColorsEvent -> {
			viewModelScope.launch {
				_currentEditTheme.emit(event.theme)
			}
		}

		else -> Unit
	}

	private fun saveTheme() {
		viewModelScope.launch {
			val theme = currentEditTheme.lastOrNull()
			if (theme != null) {
				themeRepository.insertColorTheme(
					AppColors(
						background = theme.background,
						accent = theme.accent,
						additional = theme.additional,
						additionalContainer = theme.additionalContainer,
						accentContainer = theme.accentContainer
					)
				)
			}
		}
	}
}
