package com.locker.core.database.di

import com.locker.core.database.provider.AppDatabaseProvider
import com.locker.core.database.provider.InMemoryDatabaseProvider
import com.locker.core.database.provider.IosAppDatabaseProvider
import com.locker.core.database.provider.IosInMemoryDatabaseProvider
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformDatabaseModule: Module = module {
	single<AppDatabaseProvider> { IosAppDatabaseProvider() }
	single<InMemoryDatabaseProvider> { IosInMemoryDatabaseProvider() }
}
