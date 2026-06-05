package com.locker.feature.gamescreen.screen.factory

import com.locker.feature.gamescreen.Res
import com.locker.feature.gamescreen.controller.model.Player
import com.locker.feature.gamescreen.draw
import com.locker.feature.gamescreen.screen.model.EndGameScreenState
import com.locker.feature.gamescreen.wins
import org.jetbrains.compose.resources.getString

object EndGameScreenStateFactory {
	suspend fun create(winner: Player?): EndGameScreenState = EndGameScreenState(
		icon = winner?.icon,
		title = "${getString(if (winner != null) Res.string.wins else Res.string.draw)}!",
		isVisible = true
	)
}
