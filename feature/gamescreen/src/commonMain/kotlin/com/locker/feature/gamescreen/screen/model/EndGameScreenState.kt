package com.locker.feature.gamescreen.screen.model

import org.jetbrains.compose.resources.DrawableResource

data class EndGameScreenState(
	val icon: DrawableResource? = null,
	val title: String = "",
	val nextGame: String = "",
	val mainMenu: String = "",
	val isVisible: Boolean = false,
)
