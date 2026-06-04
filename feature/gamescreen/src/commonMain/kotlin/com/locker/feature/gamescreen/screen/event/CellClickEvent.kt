package com.locker.feature.gamescreen.screen.event

import com.locker.feature.core.screen.ScreenEvent

data class CellClickEvent(
    val fieldI: Int,
    val fieldJ: Int,
    val blockI: Int,
    val blockJ: Int
) : ScreenEvent
