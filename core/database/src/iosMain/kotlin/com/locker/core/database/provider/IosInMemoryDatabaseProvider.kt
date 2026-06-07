package com.locker.core.database.provider

import androidx.room.Room
import androidx.room.RoomDatabase
import com.locker.core.database.db.InMemoryDatabase

class IosInMemoryDatabaseProvider : InMemoryDatabaseProvider() {
	override fun getDatabaseBuilder(): RoomDatabase.Builder<InMemoryDatabase> =
		Room.inMemoryDatabaseBuilder<InMemoryDatabase>()
}
