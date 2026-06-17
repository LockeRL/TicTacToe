package com.locker.feature.gamebot.di

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.locker.core.models.Difficulty
import com.locker.core.models.Player
import com.locker.core.navigation.keys.BotGameScreenNavKey
import com.locker.feature.gamebot.screen.BotGameScreen
import com.locker.feature.gamebot.screen.BotGameScreenViewModel
import com.locker.feature.gamescreen.controller.GameController
import com.locker.feature.gamescreen.controller.block.GameField
import com.locker.feature.gamescreen.controller.handler.CellClickEventHandler
import org.koin.compose.koinInject
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation

@OptIn(KoinExperimentalAPI::class)
val botGameModule = module {
    viewModel { (difficulty: Difficulty, userPlayer: Player) ->
        val eventHandler = CellClickEventHandler()
        BotGameScreenViewModel(
            gameController = GameController(GameField(), eventHandler),
            eventHandler = eventHandler,
            navigator = get(),
            initialDifficulty = difficulty,
            initialUserPlayer = userPlayer
        )
    }

    navigation<BotGameScreenNavKey> { key ->
        val difficulty = try {
            Difficulty.valueOf(key.difficulty.uppercase())
        } catch (e: Exception) {
            Difficulty.EASY
        }
        val userPlayer = if (key.playerSymbol.lowercase() == "circle") Player.CIRCLE else Player.CROSS
        
        BotGameScreen(
            viewModel = koinInject { parametersOf(difficulty, userPlayer) },
            modifier = Modifier.fillMaxSize()
        )
    }
}
