package com.locker.feature.mainscreen.screen.event

import com.locker.core.models.Difficulty
import com.locker.core.models.Player
import com.locker.feature.core.screen.ScreenEvent

data class BotPlayClickEvent(val difficulty: Difficulty, val player: Player) : ScreenEvent
