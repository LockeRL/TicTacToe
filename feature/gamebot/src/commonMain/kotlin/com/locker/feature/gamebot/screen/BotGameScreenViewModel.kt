package com.locker.feature.gamebot.screen

import androidx.lifecycle.viewModelScope
import com.locker.core.models.Difficulty
import com.locker.core.models.Player
import com.locker.core.navigation.Navigator
import com.locker.core.navigation.keys.MainScreenNavKey
import com.locker.feature.core.screen.BaseViewModel
import com.locker.feature.core.screen.ScreenEvent
import com.locker.feature.gamebot.controller.BotGameController
import com.locker.feature.gamebot.screen.event.UpdateBotSettingsEvent
import com.locker.feature.gamescreen.controller.GameController
import com.locker.feature.gamescreen.controller.handler.CellClickEventHandler
import com.locker.feature.gamescreen.controller.model.BoardState
import com.locker.feature.gamescreen.controller.model.CellState
import com.locker.feature.gamescreen.screen.event.CellClickEvent
import com.locker.feature.gamescreen.screen.event.GameEndEvent
import com.locker.feature.gamescreen.screen.event.MainMenuEvent
import com.locker.feature.gamescreen.screen.event.NextGameEvent
import com.locker.feature.gamescreen.screen.model.EndGameScreenState
import com.locker.resources.you_lost
import com.locker.resources.you_won
import com.locker.resources.draw_game
import org.jetbrains.compose.resources.getString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class BotGameScreenViewModel(
    val gameController: GameController,
    private val eventHandler: CellClickEventHandler,
    private val navigator: Navigator,
    initialDifficulty: Difficulty,
    initialUserPlayer: Player
) : BaseViewModel() {

    private val _difficulty = MutableStateFlow(initialDifficulty)
    val difficulty: StateFlow<Difficulty> = _difficulty

    private val _userPlayer = MutableStateFlow(initialUserPlayer)
    val userPlayer: StateFlow<Player> = _userPlayer

    private val _isBotThinking = MutableStateFlow(false)
    val isBotThinking: StateFlow<Boolean> = _isBotThinking

    private val botGameController = BotGameController(
        gameController = gameController,
        difficultyFlow = _difficulty,
        userPlayerFlow = _userPlayer,
        eventHandler = eventHandler,
        scope = viewModelScope,
        onThinkingChanged = { _isBotThinking.value = it }
    )

    val eventsJob = gameController.eventsFlow.launchIn(viewModelScope)

    private val _endScreen: MutableStateFlow<EndGameScreenState> =
        MutableStateFlow(EndGameScreenState())
    val endScreen: StateFlow<EndGameScreenState> = _endScreen

    val field = gameController.field
    val activePlayer = gameController.activePlayer


    override fun onEvent(event: ScreenEvent) = when (event) {
        is GameEndEvent -> {
            viewModelScope.launch {
                val winner = ((field.winState.value as? BoardState.Winner)?.winner as? CellState.Occupied)?.player
                val title = when {
                    winner == _userPlayer.value -> getString(com.locker.resources.Res.string.you_won)
                    winner == null -> getString(com.locker.resources.Res.string.draw_game)
                    else -> getString(com.locker.resources.Res.string.you_lost)
                }
                _endScreen.value = EndGameScreenState(
                    icon = winner?.icon,
                    title = title,
                    isVisible = true
                )
            }
        }

        is CellClickEvent -> {
            if (activePlayer.value == _userPlayer.value && !isBotThinking.value) {
                viewModelScope.launch {
                    eventHandler.fireEvent(event)
                }
            } else Unit
        }

        is NextGameEvent -> {
            reset()
            // If the user chooses a player that is NOT the starting player (CROSS), 
            // we don't need to do anything as BotGameController reacts to activePlayer change.
            // But CROSS always starts. If user is CIRCLE, bot (CROSS) must move.
        }

        is MainMenuEvent -> {
            navigator.navigate(MainScreenNavKey)
        }

        is UpdateBotSettingsEvent -> {
            _difficulty.value = event.difficulty
            _userPlayer.value = event.userPlayer
        }

        else -> Unit
    }

    private fun reset() {
        gameController.reset()
    }
}
