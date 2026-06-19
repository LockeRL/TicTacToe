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

	override fun getUserThemes(): Flow<List<AppColors>> =
		colorsDaoService.getUserThemes()

	override fun getCurrentColorTheme(): Flow<AppColors> =
		colorsDaoService.getCurrentTheme()

	override suspend fun selectColorTheme(id: Int) {
		colorsDaoService.selectTheme(id)
	}

	override suspend fun insertColorTheme(colors: AppColors) {
		colorsDaoService.insertTheme(colors)
	}

	override suspend fun deleteColorTheme(id: Int) {
		colorsDaoService.deleteTheme(id)
		if (colorsDaoService.getCurrentThemeId() == null) {
			colorsDaoService.selectTheme(1)
		}
	}
}
