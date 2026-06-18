package com.locker.feature.gamebotscreen.di

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.locker.core.models.Difficulty
import com.locker.core.models.Player
import com.locker.core.navigation.keys.BotGameScreenNavKey
import com.locker.core.navigation.keys.navmodel.DifficultyNavModel
import com.locker.core.navigation.keys.navmodel.PlayerNavModel
import com.locker.feature.gamebotscreen.screen.GameBotScreen
import com.locker.feature.gamebotscreen.screen.GameBotScreenViewModel
import org.koin.compose.koinInject
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation

@OptIn(KoinExperimentalAPI::class)
val botGameModule = module {
    viewModel { (difficulty: Difficulty, userPlayer: Player) ->
        GameBotScreenViewModel(
            gameController = get(),
            navigator = get(),
            initialDifficulty = difficulty,
            initialUserPlayer = userPlayer
        )
    }

    navigation<BotGameScreenNavKey> { key ->
        val userPlayer = when (key.playerSymbol) {
            PlayerNavModel.CROSS -> Player.CROSS
            PlayerNavModel.CIRCLE -> Player.CIRCLE
        }

        val difficulty = when (key.difficulty) {
            DifficultyNavModel.EASY -> Difficulty.EASY
            DifficultyNavModel.MEDIUM -> Difficulty.MEDIUM
            DifficultyNavModel.HARD -> Difficulty.HARD
        }
        
        GameBotScreen(
            viewModel = koinInject { parametersOf(difficulty, userPlayer) },
            modifier = Modifier.fillMaxSize()
        )
    }
}
