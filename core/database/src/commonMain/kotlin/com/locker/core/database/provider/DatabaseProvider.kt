package com.locker.core.database.provider

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.locker.core.database.callback.ColorsDatabaseCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

abstract class DatabaseProvider<T : RoomDatabase> {
	protected abstract fun getDatabaseBuilder(): RoomDatabase.Builder<T>

	fun getDatabase(): T = getDatabaseBuilder()
		.setDriver(BundledSQLiteDriver())
		.setQueryCoroutineContext(Dispatchers.IO)
		.addCallback(ColorsDatabaseCallback)
		.build()
}
