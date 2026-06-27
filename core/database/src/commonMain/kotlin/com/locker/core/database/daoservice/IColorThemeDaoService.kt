package com.locker.core.database.daoservice

import com.locker.core.models.AppColors
import kotlinx.coroutines.flow.Flow

interface IColorThemeDaoService {
	fun getThemes(): Flow<List<AppColors>>
	fun getUserThemes(): Flow<List<AppColors>>
	fun getCurrentTheme(): Flow<AppColors>
	suspend fun getCurrentThemeId(): Int?
	suspend fun selectTheme(id: Int)
	suspend fun insertTheme(theme: AppColors): Int
	suspend fun deleteTheme(id: Int)
}
