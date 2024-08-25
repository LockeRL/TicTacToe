package com.locker.tictactoe.presentation.compose.navigation.viewmodel

import androidx.lifecycle.ViewModel
import com.locker.tictactoe.presentation.model.BackButton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NavigationViewModel : ViewModel() {
    private val _backButton: MutableStateFlow<BackButton?> = MutableStateFlow(null)
    val backButton: StateFlow<BackButton?> = _backButton

    fun setBackButton(button: BackButton?) {
        _backButton.value = button
    }
}
