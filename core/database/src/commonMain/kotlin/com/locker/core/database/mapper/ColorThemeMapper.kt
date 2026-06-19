package com.locker.core.database.mapper

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.locker.core.database.entity.ColorThemeEntity
import com.locker.core.models.AppColors

fun ColorThemeEntity.toDomain(): AppColors = AppColors(
	background = Color(background),
	accent = Color(accent),
	additional = Color(additional),
	additionalContainer = Color(additionalContainer),
	accentContainer = Color(accentContainer),
	id = id,
	isSystem = isSystem
)

fun AppColors.toEntity(): ColorThemeEntity = ColorThemeEntity(
	id = id,
	background = background.toLong(),
	accent = accent.toLong(),
	additional = additional.toLong(),
	additionalContainer = additionalContainer.toLong(),
	accentContainer = accentContainer.toLong(),
	isSystem = isSystem
)

fun Color.toLong(): Long = toArgb().toLong()
