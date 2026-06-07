package com.locker.core.database.provider

import androidx.room.Room
import androidx.room.RoomDatabase
import com.locker.core.database.db.AppDatabase
import com.locker.core.database.db.AppDatabaseConstructor
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

class IosAppDatabaseProvider : AppDatabaseProvider() {
	override fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
		val dbFilePath = documentDirectory() + "/tic_tac_toe_room.db"
		return Room.databaseBuilder<AppDatabase>(
			name = dbFilePath,
			factory = { AppDatabaseConstructor.initialize() }
		)
	}

	@OptIn(ExperimentalForeignApi::class)
	private fun documentDirectory(): String {
		val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
			directory = NSDocumentDirectory,
			inDomain = NSUserDomainMask,
			appropriateForURL = null,
			create = false,
			error = null,
		)
		return requireNotNull(documentDirectory?.path)
	}
}
