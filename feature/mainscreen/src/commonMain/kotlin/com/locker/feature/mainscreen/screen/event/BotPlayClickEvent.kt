package com.locker.feature.mainscreen.screen.event

import com.locker.feature.core.screen.ScreenEvent

data class BotPlayClickEvent(val difficulty: String, val symbol: String) : ScreenEvent
