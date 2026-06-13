package com.locker.core.database.daoservice

import com.locker.core.models.AppColors
import kotlinx.coroutines.flow.Flow

interface IColorThemeDaoService {
	fun getThemes(): Flow<List<AppColors>>
	fun getCurrentTheme(): Flow<AppColors>
	fun getCurrentThemeIndex(): Flow<Int>
	suspend fun selectTheme(id: Int)
	suspend fun insertTheme(theme: AppColors)
}
