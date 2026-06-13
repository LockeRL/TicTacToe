package com.locker.core.data.di

import com.locker.core.data.repository.IThemeRepository
import com.locker.core.data.repositoryimpl.ThemeRepository
import org.koin.dsl.module

val repositoryModule = module {
	factory<IThemeRepository> { ThemeRepository(get()) }
}
