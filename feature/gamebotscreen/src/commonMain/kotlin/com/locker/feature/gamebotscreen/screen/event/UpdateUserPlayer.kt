package com.locker.feature.gamebotscreen.screen.event

import com.locker.core.models.Player
import com.locker.feature.core.screen.ScreenEvent

data class UpdateUserPlayer(val player: Player) : ScreenEvent
