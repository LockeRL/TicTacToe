package com.locker.tictactoe.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.locker.tictactoe.presentation.model.AppColors
import com.locker.tictactoe.presentation.theme.AppColors1
import com.locker.tictactoe.presentation.theme.AppColors2
import com.locker.tictactoe.presentation.theme.AppColors3
import com.locker.tictactoe.presentation.theme.AppColors4
import com.locker.tictactoe.presentation.theme.AppColors5
import com.locker.tictactoe.presentation.theme.ColorsList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

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
