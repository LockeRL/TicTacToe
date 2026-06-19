package com.locker.feature.settingsscreen.screen.mapper

import com.locker.core.models.AppColors
import com.locker.feature.core.theme.AppColorTheme

fun AppColors.toUi(): AppColorTheme = AppColorTheme(
	background = background,
	accent = accent,
	additional = additional,
	additionalContainer = additionalContainer,
	accentContainer = accentContainer,
	id = id,
)
