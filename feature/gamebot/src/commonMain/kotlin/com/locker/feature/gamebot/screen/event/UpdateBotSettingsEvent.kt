package com.locker.feature.gamebot.screen.event

import com.locker.feature.core.screen.ScreenEvent
import com.locker.feature.gamebot.model.Difficulty
import com.locker.feature.gamescreen.controller.model.Player

data class UpdateBotSettingsEvent(val difficulty: Difficulty, val userPlayer: Player) : ScreenEvent
