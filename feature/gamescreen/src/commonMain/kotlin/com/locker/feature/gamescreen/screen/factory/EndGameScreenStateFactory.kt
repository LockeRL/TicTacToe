package com.locker.feature.gamescreen.screen.factory

import com.locker.core.models.Player
import com.locker.feature.gamescreen.Res as GameRes
import com.locker.feature.gamescreen.draw
import com.locker.feature.gamescreen.screen.model.EndGameScreenState
import com.locker.feature.gamescreen.wins
import com.locker.resources.main_menu
import com.locker.resources.next_game
import com.locker.resources.Res as ResRes
import org.jetbrains.compose.resources.getString

object EndGameScreenStateFactory {
	suspend fun create(winner: Player?): EndGameScreenState = EndGameScreenState(
		icon = winner?.icon,
		title = "${getString(if (winner != null) GameRes.string.wins else GameRes.string.draw)}!",
		nextGame = getString(ResRes.string.next_game),
		mainMenu = getString(ResRes.string.main_menu),
		isVisible = true
	)
}
