package com.locker.feature.gamescreen.di

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.locker.core.navigation.keys.GameScreenNavKey
import com.locker.core.gamelogic.controller.GameController
import com.locker.core.gamelogic.controller.block.GameField
import com.locker.feature.gamescreen.screen.GameScreen
import com.locker.feature.gamescreen.screen.GameScreenViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation

@OptIn(KoinExperimentalAPI::class)
val gameScreenModule = module {
	factory { GameField() }
	factory { GameController(get()) }
	viewModel { GameScreenViewModel(gameController = get(), navigator = get()) }

	navigation<GameScreenNavKey> {
		GameScreen(modifier = Modifier.fillMaxSize())
	}

}
