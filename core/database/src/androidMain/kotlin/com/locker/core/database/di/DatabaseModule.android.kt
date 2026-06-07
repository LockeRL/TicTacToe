package com.locker.core.database.di

import com.locker.core.database.provider.AndroidAppDatabaseProvider
import com.locker.core.database.provider.AndroidInMemoryDatabaseProvider
import com.locker.core.database.provider.AppDatabaseProvider
import com.locker.core.database.provider.InMemoryDatabaseProvider
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformDatabaseModule: Module = module {
    single<InMemoryDatabaseProvider> { AndroidInMemoryDatabaseProvider(get()) }
    single<AppDatabaseProvider> { AndroidAppDatabaseProvider(get()) }
}
