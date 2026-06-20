package com.locker.feature.settingsscreen.screen.factory

import com.locker.feature.settingsscreen.Res
import com.locker.feature.settingsscreen.accent_color
import com.locker.feature.settingsscreen.accent_container_color
import com.locker.feature.settingsscreen.additional_color
import com.locker.feature.settingsscreen.additional_container_color
import com.locker.feature.settingsscreen.background_color
import com.locker.feature.settingsscreen.brightness_color
import com.locker.feature.settingsscreen.cancel_color
import com.locker.feature.settingsscreen.save_button
import com.locker.feature.settingsscreen.screen.model.SettingsScreenState
import com.locker.feature.settingsscreen.select_color
import com.locker.feature.settingsscreen.user_themes
import org.jetbrains.compose.resources.getString

object SettingsScreenStateFactory {
	suspend fun create(): SettingsScreenState = SettingsScreenState(
		background = getString(Res.string.background_color),
		accent = getString(Res.string.accent_color),
		additional = getString(Res.string.additional_color),
		additionalContainer = getString(Res.string.additional_container_color),
		accentContainer = getString(Res.string.accent_container_color),
		saveButton = getString(Res.string.save_button),
		userThemes = getString(Res.string.user_themes),
		selectColor = getString(Res.string.select_color),
		cancelColor = getString(Res.string.cancel_color),
		brightnessColor = getString(Res.string.brightness_color),
	)
}
