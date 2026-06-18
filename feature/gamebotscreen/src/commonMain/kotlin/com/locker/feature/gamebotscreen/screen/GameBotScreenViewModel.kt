package com.locker.feature.gamebotscreen.screen

import androidx.lifecycle.viewModelScope
import com.locker.core.gamelogic.controller.GameController
import com.locker.core.gamelogic.model.BoardState
import com.locker.core.gamelogic.model.CellState
import com.locker.core.models.Difficulty
import com.locker.core.models.Player
import com.locker.core.navigation.Navigator
import com.locker.core.navigation.keys.MainScreenNavKey
import com.locker.feature.core.screen.BaseViewModel
import com.locker.feature.core.screen.ScreenEvent
import com.locker.feature.gamebotscreen.bot.BotFactory
import com.locker.feature.gamebotscreen.screen.event.CellClickEvent
import com.locker.feature.gamebotscreen.screen.event.MainMenuEvent
import com.locker.feature.gamebotscreen.screen.event.NextGameEvent
import com.locker.feature.gamebotscreen.screen.event.UpdateBotSettingsEvent
import com.locker.feature.gamebotscreen.screen.factory.EndGameScreenStateFactory
import com.locker.feature.gamebotscreen.screen.factory.HeaderStateFactory
import com.locker.feature.gamebotscreen.screen.model.EndGameScreenState
import com.locker.feature.gamebotscreen.screen.model.HeaderState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class GameBotScreenViewModel(
    val gameController: GameController,
    private val navigator: Navigator,
    initialDifficulty: Difficulty,
    initialUserPlayer: Player
) : BaseViewModel() {

    private val _difficulty = MutableStateFlow(initialDifficulty)

    private val _userPlayer = MutableStateFlow(initialUserPlayer)

    private val _isBotThinking = MutableStateFlow(false)

    private val _endScreen: MutableStateFlow<EndGameScreenState> =
        MutableStateFlow(EndGameScreenState())
    val endScreen: StateFlow<EndGameScreenState> = _endScreen

    private val _header: MutableStateFlow<HeaderState> = MutableStateFlow(HeaderState())
    val header: StateFlow<HeaderState> = _header

    val field = gameController.field
    val activePlayer = gameController.activePlayer

    private val botJob = combine(_difficulty, _userPlayer) { difficulty, userPlayer ->
        delay(600)
        val bot = BotFactory.createBot(difficulty)
        val botPlayer = if (userPlayer == Player.CROSS) Player.CIRCLE else Player.CROSS
        bot to botPlayer
    }.flatMapLatest { (bot, botPlayer) ->
        combine(gameController.activePlayer, gameController.field.winState) { player, winState ->
            player to winState
        }.onEach { (player, winState) ->
            if (player == botPlayer && winState == BoardState.InProgress) {
                _isBotThinking.value = true
                delay(600)
                val move = bot.getMove(gameController.field, player, gameController.activeBlock)
                if (move != null) {
                    gameController.onCellClick(move.fieldI, move.fieldJ, move.blockI, move.blockJ)
                }
                _isBotThinking.value = false
            }
        }
    }.launchIn(viewModelScope)

    private val headerJob = combine(
        _isBotThinking,
        activePlayer,
        _userPlayer,
        field.winState,
    ) { isThinking, activePlayer, userPlayer, boardState ->
        if (boardState == BoardState.InProgress) {
            _header.value = HeaderStateFactory.create(
                player = activePlayer,
                isBotThinking = isThinking,
                isUserTurn = activePlayer == userPlayer,
            )
        }
    }.launchIn(viewModelScope)

    private val endScreenJob = combine(
        _userPlayer,
        _difficulty,
        field.winState,
    ) { user, difficulty, boardState ->
        if (boardState != BoardState.InProgress) {
            _endScreen.value = EndGameScreenStateFactory.create(
                winner = ((field.winState.value as? BoardState.Winner)?.winner as? CellState.Occupied)?.player,
                userPlayer = user,
                difficulty = difficulty,
            )
        }
    }.launchIn(viewModelScope)


    override fun onEvent(event: ScreenEvent) = when (event) {
        is CellClickEvent -> {
            if (activePlayer.value == _userPlayer.value && !_isBotThinking.value) {
                viewModelScope.launch {
                    gameController.onCellClick(event.fieldI, event.fieldJ, event.blockI, event.blockJ)
                }
            } else Unit
        }

        is NextGameEvent -> {
            reset()
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
