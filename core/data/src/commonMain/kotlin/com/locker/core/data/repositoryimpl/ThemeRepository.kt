package com.locker.core.data.repositoryimpl

import com.locker.core.data.repository.IThemeRepository
import com.locker.core.database.daoservice.IColorThemeDaoService
import com.locker.core.models.AppColors
import kotlinx.coroutines.flow.Flow

internal class ThemeRepository(
	private val colorsDaoService: IColorThemeDaoService
) : IThemeRepository {
	override fun getColorThemes(): Flow<List<AppColors>> =
		colorsDaoService.getThemes()

	override fun getCurrentColorTheme(): Flow<AppColors> =
		colorsDaoService.getCurrentTheme()

	override fun getCurrentColorThemeIndex(): Flow<Int> =
		colorsDaoService.getCurrentThemeIndex()

	override suspend fun selectColorTheme(id: Int) {
		colorsDaoService.selectTheme(id)
	}

	override suspend fun insertColorTheme(colors: AppColors) {
		colorsDaoService.insertTheme(colors)
	}
}
