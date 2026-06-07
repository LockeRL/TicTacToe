package com.locker.core.database.provider

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.locker.core.database.db.InMemoryDatabase

class AndroidInMemoryDatabaseProvider(private val context: Context) : InMemoryDatabaseProvider() {
	override fun getDatabaseBuilder(): RoomDatabase.Builder<InMemoryDatabase> {
		val appContext = context.applicationContext
		return Room.inMemoryDatabaseBuilder(
			context = appContext,
			klass = InMemoryDatabase::class.java
		)
	}
}
