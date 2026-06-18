package com.locker.feature.gamebotscreen.screen.event

import com.locker.core.models.Difficulty
import com.locker.feature.core.screen.ScreenEvent

data class UpdateBotDifficulty(val difficulty: Difficulty) : ScreenEvent
