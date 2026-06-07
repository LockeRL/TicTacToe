package com.locker.core.database.di

import com.locker.core.database.db.AppDatabase
import com.locker.core.database.SettingsRepository
import com.locker.core.database.db.InMemoryDatabase
import com.locker.core.database.provider.AppDatabaseProvider
import com.locker.core.database.provider.InMemoryDatabaseProvider
import org.koin.core.module.Module
import org.koin.dsl.module

val databaseModule: Module = module {
    includes(platformDatabaseModule)
    single<AppDatabase> { get<AppDatabaseProvider>().getDatabase() }
    single<InMemoryDatabase> { get<InMemoryDatabaseProvider>().getDatabase() }


    single { get<AppDatabase>().colorThemeDao() }
    single { SettingsRepository(get()) }
}

expect val platformDatabaseModule: Module
