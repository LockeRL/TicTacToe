package com.locker.feature.settingsscreen.screen.event

import com.locker.feature.core.screen.ScreenEvent
import com.locker.feature.core.theme.AppColorTheme

data class SaveThemeEvent(val theme: AppColorTheme) : ScreenEvent
