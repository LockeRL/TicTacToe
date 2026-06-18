package com.locker.feature.gamebotscreen.screen.factory

import com.locker.core.models.Difficulty
import com.locker.core.models.Player
import com.locker.feature.gamebotscreen.Res as GameRes
import com.locker.feature.gamebotscreen.draw_game
import com.locker.feature.gamebotscreen.screen.model.EndGameScreenState
import com.locker.feature.gamebotscreen.you_lost
import com.locker.feature.gamebotscreen.you_won
import com.locker.resources.main_menu
import com.locker.resources.next_game
import org.jetbrains.compose.resources.getString
import com.locker.resources.Res as ResRes

object EndGameScreenStateFactory {
	suspend fun create(
		winner: Player?,
		userPlayer: Player,
		difficulty: Difficulty,
	): EndGameScreenState = EndGameScreenState(
		icon = winner?.icon,
		title = when (winner) {
			userPlayer -> getString(GameRes.string.you_won)
			null -> getString(GameRes.string.draw_game)
			else -> getString(GameRes.string.you_lost)
		},
		nextGame = getString(ResRes.string.next_game),
		mainMenu = getString(ResRes.string.main_menu),
		difficulty = difficulty,
		player = userPlayer,
		isVisible = true,
	)
}
