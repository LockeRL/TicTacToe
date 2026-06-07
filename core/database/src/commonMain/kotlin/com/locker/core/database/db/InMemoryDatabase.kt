package com.locker.core.database.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.locker.core.database.entity.ColorThemeEntity

@Database(
	entities = [ColorThemeEntity::class],
	version = 1
)
@ConstructedBy(AppInMemoryDataBaseConstructor::class)
abstract class InMemoryDatabase : RoomDatabase() {
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppInMemoryDataBaseConstructor : RoomDatabaseConstructor<InMemoryDatabase> {
	override fun initialize(): InMemoryDatabase
}
