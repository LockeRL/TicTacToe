package com.locker.core.database.provider

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.locker.core.database.db.AppDatabase

class AndroidAppDatabaseProvider(private val context: Context) : AppDatabaseProvider() {
	override fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
		val appContext = context.applicationContext
		val dbFile = appContext.getDatabasePath("tic_tac_toe_room.db")
		return Room.databaseBuilder<AppDatabase>(
			context = appContext,
			name = dbFile.absolutePath
		)
	}
}
