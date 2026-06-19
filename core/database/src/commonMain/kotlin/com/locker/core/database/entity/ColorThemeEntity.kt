package com.locker.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ColorThemeEntity(
	@PrimaryKey(autoGenerate = true) val id: Int = 0,
	val background: Long,
	val accent: Long,
	val additional: Long,
	val additionalContainer: Long,
	val accentContainer: Long,
	val isSelected: Boolean = false,
	val isSystem: Boolean = false,
)
