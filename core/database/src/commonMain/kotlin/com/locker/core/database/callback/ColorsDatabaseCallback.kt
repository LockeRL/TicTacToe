package com.locker.core.database.callback

import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.locker.core.database.entity.ColorThemeEntity
import com.locker.core.database.mapper.toLong
import com.locker.core.models.AppColors
import com.locker.core.models.AppColors.Companion.ColorsList

object ColorsDatabaseCallback : RoomDatabase.Callback() {
	override fun onCreate(connection: SQLiteConnection) {
		connection.execSQL(
			"""
				INSERT INTO ${ColorThemeEntity::class.simpleName} ${getColumnNames()}
				VALUES ${getValuesString()};
			""".trimIndent()
		)
	}

	private fun getColumnNames(): String = listOf(
		ColorThemeEntity::id,
		ColorThemeEntity::background,
		ColorThemeEntity::accent,
		ColorThemeEntity::additional,
		ColorThemeEntity::additionalContainer,
		ColorThemeEntity::accentContainer,
		ColorThemeEntity::isSelected
	).joinToString(separator = ", ", prefix = "(", postfix = ")") { it.name }

	private fun getValuesString(): String =
		ColorsList.mapIndexed { index, colors -> colors.toDbString(index) }.joinToString(separator = ", ")

	private fun AppColors.toDbString(index: Int): String =
		"($index, ${background.toLong()}, ${accent.toLong()}, ${additional.toLong()}, " +
			"${additionalContainer.toLong()}, ${accentContainer.toLong()}, ${index == 0})"
}
