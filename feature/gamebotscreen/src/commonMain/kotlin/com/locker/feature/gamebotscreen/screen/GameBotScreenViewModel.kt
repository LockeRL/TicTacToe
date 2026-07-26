package com.locker.feature.gamebotscreen.screen

import androidx.lifecycle.viewModelScope
import com.locker.core.gamelogic.controller.GameController
import com.locker.core.gamelogic.model.BoardState
import com.locker.core.gamelogic.model.CellState
import com.locker.core.models.Difficulty
import com.locker.core.models.Player
import com.locker.core.navigation.Navigator
import com.locker.core.navigation.keys.MainScreenNavKey
import com.locker.core.gamelogic.bot.BotFactory
import com.locker.feature.core.screen.BaseViewModel
import com.locker.feature.core.screen.ScreenEvent
import com.locker.feature.gamebotscreen.screen.event.CellClickEvent
import com.locker.feature.gamebotscreen.screen.event.MainMenuEvent
import com.locker.feature.gamebotscreen.screen.event.NextGameEvent
import com.locker.feature.gamebotscreen.screen.event.UpdateBotDifficulty
import com.locker.feature.gamebotscreen.screen.event.UpdateUserPlayer
import com.locker.feature.gamebotscreen.screen.factory.EndGameScreenStateFactory
import com.locker.feature.gamebotscreen.screen.factory.HeaderStateFactory
import com.locker.feature.gamebotscreen.screen.model.EndGameScreenState
import com.locker.feature.gamebotscreen.screen.model.HeaderState
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.time.TimeSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class GameBotScreenViewModel(
	val gameController: GameController,
	private val navigator: Navigator,
	initialDifficulty: Difficulty,
	initialUserPlayer: Player
) : BaseViewModel() {

	private var playedAs: Player = initialUserPlayer

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
		val bot = BotFactory.createBot(difficulty)
		val botPlayer = if (userPlayer == Player.CROSS) Player.CIRCLE else Player.CROSS
		bot to botPlayer
	}.flatMapLatest { (bot, botPlayer) ->
		combine(gameController.activePlayer, gameController.field.winState) { player, winState ->
			player to winState
		}.onEach { (player, winState) ->
			if (player == botPlayer && winState == BoardState.InProgress) {
				_isBotThinking.value = true
				try {
					val startTime = TimeSource.Monotonic.markNow()
					val move = withContext(Dispatchers.Default) {
						bot.getMove(
							field = gameController.field,
							activePlayer = player,
							activeBlock = gameController.activeBlock
						)
					}
					val executionTime = startTime.elapsedNow().inWholeMilliseconds
					val remainingDelay = 600 - executionTime
					if (remainingDelay > 0) {
						delay(remainingDelay)
					}
					if (move != null) {
						gameController.onCellClick(
							fieldI = move.fieldI,
							fieldJ = move.fieldJ,
							blockI = move.blockI,
							blockJ = move.blockJ
						)
					}
				} catch (e: Exception) {
					e.printStackTrace()
					bot.getRandomMove(
						field = gameController.field,
						activeBlock = gameController.activeBlock
					)?.let { fallbackMove ->
						gameController.onCellClick(
							fieldI = fallbackMove.fieldI,
							fieldJ = fallbackMove.fieldJ,
							blockI = fallbackMove.blockI,
							blockJ = fallbackMove.blockJ
						)
					}
				} finally {
					_isBotThinking.value = false
				}
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
				player = _userPlayer.value,
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
			_isBotThinking.value = false
			_endScreen.value = EndGameScreenStateFactory.create(
				winner = ((field.winState.value as? BoardState.Winner)?.winner as? CellState.Occupied)?.player,
				userPlayer = user,
				difficulty = difficulty,
				playedAs = playedAs
			)
		}
	}.launchIn(viewModelScope)


	override fun onEvent(event: ScreenEvent) = when (event) {
		is CellClickEvent -> {
			if (activePlayer.value == _userPlayer.value && !_isBotThinking.value) {
				gameController.onCellClick(event.fieldI, event.fieldJ, event.blockI, event.blockJ)
			} else Unit
		}

		is NextGameEvent -> {
			reset()
		}

		is MainMenuEvent -> {
			navigator.navigate(MainScreenNavKey)
		}

		is UpdateUserPlayer -> {
			_userPlayer.value = event.player
		}

		is UpdateBotDifficulty -> {
			_difficulty.value = event.difficulty
		}

		else -> Unit
	}

	private fun reset() {
		gameController.reset()
		playedAs = _userPlayer.value
	}
}
