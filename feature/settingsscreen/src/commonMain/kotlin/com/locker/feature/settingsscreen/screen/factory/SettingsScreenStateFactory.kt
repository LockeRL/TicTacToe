package com.locker.feature.settingsscreen.screen.factory

import com.locker.feature.settingsscreen.Res
import com.locker.feature.settingsscreen.accent_color
import com.locker.feature.settingsscreen.accent_container_color
import com.locker.feature.settingsscreen.additional_color
import com.locker.feature.settingsscreen.additional_container_color
import com.locker.feature.settingsscreen.background_color
import com.locker.feature.settingsscreen.save_button
import com.locker.feature.settingsscreen.screen.model.SettingsScreenState
import org.jetbrains.compose.resources.getString

object SettingsScreenStateFactory {
	suspend fun create(): SettingsScreenState = SettingsScreenState(
		background = getString(Res.string.background_color),
		accent = getString(Res.string.accent_color),
		additional = getString(Res.string.additional_color),
		additionalContainer = getString(Res.string.additional_container_color),
		accentContainer = getString(Res.string.accent_container_color),
		saveButton = getString(Res.string.save_button),
	)
}
