package com.locker.feature.colorpicker.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.locker.feature.colorpicker.Res
import com.locker.feature.colorpicker.ic_palette
import com.locker.feature.core.model.AppColors
import com.locker.feature.core.theme.ColorsList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.jetbrains.compose.resources.getString

class ColorsViewModel : ViewModel() {
    val colorsList: List<AppColors> = ColorsList

    private val _activeColorIndex: MutableStateFlow<Int> = MutableStateFlow(INITIAL_INDEX)
    val activeColorIndex: StateFlow<Int> = _activeColorIndex

    val appColors: StateFlow<AppColors> = activeColorIndex.map { id ->
        colorsList[id]
    }.stateIn(viewModelScope, SharingStarted.Eagerly, ColorsList[INITIAL_INDEX])

    fun setAppColorsIndex(index: Int) {
        if (index >= colorsList.size || index < 0)
            return

        _activeColorIndex.value = index
    }

    private companion object {
        const val INITIAL_INDEX = 0
    }
}
