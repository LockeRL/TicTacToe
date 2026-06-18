package com.locker.feature.gamebotscreen.screen.event

import com.locker.core.models.Difficulty
import com.locker.core.models.Player
import com.locker.feature.core.screen.ScreenEvent

data class UpdateBotSettingsEvent(val difficulty: Difficulty, val userPlayer: Player) : ScreenEvent
