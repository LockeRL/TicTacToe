package com.locker.core.data.repository

import com.locker.core.models.AppColors
import kotlinx.coroutines.flow.Flow

interface IThemeRepository {
	fun getColorThemes(): Flow<List<AppColors>>
	fun getUserThemes(): Flow<List<AppColors>>
	fun getCurrentColorTheme(): Flow<AppColors>
	suspend fun selectColorTheme(id: Int)
	suspend fun insertColorTheme(colors: AppColors)
	suspend fun deleteColorTheme(id: Int)
}
