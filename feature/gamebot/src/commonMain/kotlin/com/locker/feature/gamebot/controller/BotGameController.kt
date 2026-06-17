package com.locker.feature.gamebot.controller

import com.locker.core.models.Difficulty
import com.locker.core.models.Player
import com.locker.feature.gamebot.bot.BotFactory
import com.locker.feature.gamescreen.controller.GameController
import com.locker.feature.gamescreen.controller.handler.CellClickEventHandler
import com.locker.feature.gamescreen.controller.model.BoardState
import com.locker.feature.gamescreen.screen.event.CellClickEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class BotGameController(
    private val gameController: GameController,
    difficultyFlow: Flow<Difficulty>,
    userPlayerFlow: Flow<Player>,
    private val eventHandler: CellClickEventHandler,
    scope: CoroutineScope,
    private val onThinkingChanged: (Boolean) -> Unit = {}
) {
    init {
        setup(difficultyFlow, userPlayerFlow, scope)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun setup(difficultyFlow: Flow<Difficulty>, userPlayerFlow: Flow<Player>, scope: CoroutineScope) {
        combine(difficultyFlow, userPlayerFlow) { difficulty, userPlayer ->
            val bot = BotFactory.createBot(difficulty)
            val botPlayer = if (userPlayer == Player.CROSS) Player.CIRCLE else Player.CROSS
            bot to botPlayer
        }.flatMapLatest { (bot, botPlayer) ->
            // Combine activePlayer and winState to trigger bot even if player doesn't change (e.g. on reset)
            combine(gameController.activePlayer, gameController.field.winState) { player, winState ->
                player to winState
            }.onEach { (player, winState) ->
                if (player == botPlayer && winState == BoardState.InProgress) {
                    onThinkingChanged(true)
                    delay(600)
                    val move = bot.getMove(gameController.field, player, gameController.activeBlock)
                    if (move != null) {
                        eventHandler.fireEvent(CellClickEvent(move.fieldI, move.fieldJ, move.blockI, move.blockJ))
                    }
                    onThinkingChanged(false)
                }
            }
        }.launchIn(scope)
    }
}
