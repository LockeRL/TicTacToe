package com.locker.feature.gamebotscreen.screen.factory

import com.locker.core.models.Player
import com.locker.feature.gamebotscreen.Res
import com.locker.feature.gamebotscreen.bot_thinking
import com.locker.feature.gamebotscreen.bot_turn
import com.locker.feature.gamebotscreen.playing_as
import com.locker.feature.gamebotscreen.screen.model.HeaderState
import com.locker.feature.gamebotscreen.your_turn
import org.jetbrains.compose.resources.getString

object HeaderStateFactory {
	suspend fun create(
		player: Player,
		isBotThinking: Boolean,
		isUserTurn: Boolean,
	): HeaderState = HeaderState(
		icon = player.icon,
		playingAs = getString(Res.string.playing_as),
		subtitle = getString(
			when {
				isBotThinking -> Res.string.bot_thinking
				isUserTurn -> Res.string.your_turn
				else -> Res.string.bot_turn
			}
		),
		isUserTurn = isUserTurn,
	)
}
