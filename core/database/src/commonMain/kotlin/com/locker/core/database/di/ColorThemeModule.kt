package com.locker.core.database.di

import com.locker.core.database.dao.ColorThemeDao
import com.locker.core.database.daoservice.IColorThemeDaoService
import com.locker.core.database.daoserviceimpl.ColorThemeDaoService
import com.locker.core.database.db.AppDatabase
import org.koin.dsl.module

internal val colorThemeModule = module {
	factory<ColorThemeDao> { get<AppDatabase>().colorThemeDao() }
	factory<IColorThemeDaoService> { ColorThemeDaoService(dao = get()) }

}
