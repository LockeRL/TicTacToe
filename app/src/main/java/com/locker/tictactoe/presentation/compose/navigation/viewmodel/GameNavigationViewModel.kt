package com.locker.tictactoe.presentation.compose.navigation.viewmodel

import androidx.lifecycle.ViewModel
import com.locker.tictactoe.presentation.model.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GameNavigationViewModel : ViewModel() {
    private val _gameWinner: MutableStateFlow<Player?> = MutableStateFlow(null)
    val gameWinner: StateFlow<Player?> = _gameWinner

    fun setGameWinner(winner: Player?) {
        _gameWinner.value = winner
    }
}
