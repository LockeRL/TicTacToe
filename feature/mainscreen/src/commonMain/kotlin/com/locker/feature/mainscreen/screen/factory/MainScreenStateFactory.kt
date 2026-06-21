package com.locker.feature.mainscreen.screen.factory

import com.locker.feature.mainscreen.Res
import com.locker.feature.mainscreen.play_vs_bot
import com.locker.feature.mainscreen.play_vs_friend
import com.locker.feature.mainscreen.rules
import com.locker.feature.mainscreen.screen.model.MainScreenState
import com.locker.feature.mainscreen.tic_x_tac
import com.locker.feature.mainscreen.toe
import org.jetbrains.compose.resources.getString

object MainScreenStateFactory {
	suspend fun create(): MainScreenState {
		return MainScreenState(
			firstTitle = getString(Res.string.tic_x_tac),
			secondTitle = getString(Res.string.toe),
			playVsFriend = getString(Res.string.play_vs_friend),
			playVsBot = getString(Res.string.play_vs_bot),
			rules = getString(Res.string.rules),
		)
	}
}
