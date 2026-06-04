package com.locker.feature.gamescreen.screen.event

import com.locker.feature.core.screen.ScreenEvent
import com.locker.models.Player

data class GameEnd(val winner: Player?) : ScreenEvent
