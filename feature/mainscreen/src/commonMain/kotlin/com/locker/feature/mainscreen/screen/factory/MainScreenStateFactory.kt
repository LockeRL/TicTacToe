package com.locker.feature.mainscreen.screen.factory

import com.locker.feature.mainscreen.Res
import com.locker.feature.mainscreen.play
import com.locker.feature.mainscreen.screen.model.MainScreenState
import com.locker.feature.mainscreen.tic_x_tac
import com.locker.feature.mainscreen.toe
import org.jetbrains.compose.resources.getString

object MainScreenStateFactory {
	suspend fun create(): MainScreenState {
		val mainScreenState = MainScreenState(
			firstTitle = getString(Res.string.tic_x_tac),
			secondTitle = getString(Res.string.toe),
			playButton = getString(Res.string.play)
		)
		return mainScreenState
	}
}
