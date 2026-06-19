package com.locker.feature.colorpicker.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.locker.core.data.repository.IThemeRepository
import com.locker.feature.colorpicker.mapper.toUi
import com.locker.feature.core.theme.AppColorTheme
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ColorsViewModel(
	private val themeRepository: IThemeRepository
) : ViewModel() {
	val colorsList: StateFlow<List<AppColorTheme>> =
		themeRepository.getColorThemes().map { list ->
			list.map { it.toUi() }
		}.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

	val currentColorTheme: StateFlow<AppColorTheme?> =
		themeRepository.getCurrentColorTheme().map { it.toUi() }
			.stateIn(viewModelScope, SharingStarted.Eagerly, null)

	fun setAppColorsId(index: Int) {
		viewModelScope.launch {
			themeRepository.selectColorTheme(index)
		}
	}
}
