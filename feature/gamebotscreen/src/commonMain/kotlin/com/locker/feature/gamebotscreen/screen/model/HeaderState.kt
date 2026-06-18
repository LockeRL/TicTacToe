package com.locker.feature.gamebotscreen.screen.model

import org.jetbrains.compose.resources.DrawableResource

data class HeaderState(
	val icon: DrawableResource? = null,
	val playingAs: String = "",
	val subtitle: String = "",
	val isUserTurn: Boolean = true,
)
