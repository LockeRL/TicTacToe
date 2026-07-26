package com.locker.feature.gamebotscreen.screen.model

import com.locker.core.models.Player

data class HeaderState(
	val player: Player? = null,
	val playingAs: String = "",
	val subtitle: String = "",
	val isUserTurn: Boolean = true,
)
