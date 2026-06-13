package com.locker.core.data.repository

import com.locker.core.models.AppColors
import kotlinx.coroutines.flow.Flow

interface IThemeRepository {
	fun getColorThemes(): Flow<List<AppColors>>
	fun getCurrentColorTheme(): Flow<AppColors>
	fun getCurrentColorThemeIndex(): Flow<Int>
	suspend fun selectColorTheme(id: Int)
	suspend fun insertColorTheme(colors: AppColors)
}
