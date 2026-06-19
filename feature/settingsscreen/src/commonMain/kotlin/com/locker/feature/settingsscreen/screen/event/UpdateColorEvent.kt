package com.locker.feature.settingsscreen.screen.event

import com.locker.feature.core.screen.ScreenEvent
import com.locker.feature.core.theme.AppColorTheme
import com.locker.feature.settingsscreen.Res
import com.locker.feature.settingsscreen.accent_color
import com.locker.feature.settingsscreen.accent_container_color
import com.locker.feature.settingsscreen.additional_color
import com.locker.feature.settingsscreen.additional_container_color
import com.locker.feature.settingsscreen.background_color
import org.jetbrains.compose.resources.StringResource

data class UpdateColorsEvent(val theme: AppColorTheme) : ScreenEvent

enum class ColorType(val titleRes: StringResource) {
	ACCENT(Res.string.accent_color),
	BACKGROUND(Res.string.background_color),
	ADDITIONAL(Res.string.additional_color),
	ADDITIONAL_CONTAINER(Res.string.additional_container_color),
	ACCENT_CONTAINER(Res.string.accent_container_color)
}
