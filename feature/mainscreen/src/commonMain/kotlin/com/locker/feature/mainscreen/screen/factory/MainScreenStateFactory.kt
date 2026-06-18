package com.locker.feature.mainscreen.screen.factory

import com.locker.feature.mainscreen.*
import com.locker.feature.mainscreen.screen.model.MainScreenState
import org.jetbrains.compose.resources.getString

object MainScreenStateFactory {
	suspend fun create(): MainScreenState {
		return MainScreenState(
			firstTitle = getString(Res.string.tic_x_tac),
			secondTitle = getString(Res.string.toe),
			playButton = getString(Res.string.play),
			easyBotButton = getString(Res.string.bot_easy),
			mediumBotButton = getString(Res.string.bot_medium),
			hardBotButton = getString(Res.string.bot_hard)
		)
	}
}
