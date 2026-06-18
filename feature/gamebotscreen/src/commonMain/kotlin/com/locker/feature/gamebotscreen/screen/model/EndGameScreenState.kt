package com.locker.feature.gamebotscreen.screen.model

import com.locker.core.models.Difficulty
import com.locker.core.models.Player
import org.jetbrains.compose.resources.DrawableResource

data class EndGameScreenState(
	val icon: DrawableResource? = null,
	val title: String = "",
	val nextGame: String = "",
	val mainMenu: String = "",
	val difficulty: Difficulty = Difficulty.MEDIUM,
	val player: Player = Player.CIRCLE,
	val isVisible: Boolean = false,
)
