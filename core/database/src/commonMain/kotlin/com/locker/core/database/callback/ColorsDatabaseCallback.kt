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
				INSERT INTO ${ColorThemeEntity::class.simpleName} (id, background, accent, additional, additionalContainer, accentContainer, isSelected)
				VALUES ${getValuesString()};
			""".trimIndent()
		)
	}

	private fun getValuesString(): String = ColorsList.foldIndexed("") { index, acc, colors ->
		acc + "${transformColor(index = index, colors = colors)}${if (index < ColorsList.size - 1) ", " else ""}"
	}

	private fun transformColor(index: Int, colors: AppColors): String =
		"($index, ${colors.background.toLong()}, ${colors.accent.toLong()}, ${colors.additional.toLong()}, " +
			"${colors.additionalContainer.toLong()}, ${colors.accentContainer.toLong()}, ${index == 0})"
}
