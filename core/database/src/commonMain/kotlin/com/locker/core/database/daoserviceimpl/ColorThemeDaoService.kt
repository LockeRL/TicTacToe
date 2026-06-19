package com.locker.core.database.daoserviceimpl

import com.locker.core.database.dao.ColorThemeDao
import com.locker.core.database.daoservice.IColorThemeDaoService
import com.locker.core.database.mapper.toDomain
import com.locker.core.database.mapper.toEntity
import com.locker.core.models.AppColors
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class ColorThemeDaoService(
	private val dao: ColorThemeDao,
	private val dispatchers: CoroutineDispatcher = Dispatchers.IO,
) : IColorThemeDaoService {
	override fun getThemes(): Flow<List<AppColors>> =
		dao.getAllThemes().map { list ->
			list.map { it.toDomain() }
		}.flowOn(dispatchers)

	override fun getUserThemes(): Flow<List<AppColors>> =
		dao.getUserThemes().map { list ->
			list.map { it.toDomain() }
		}.flowOn(dispatchers)

	override fun getCurrentTheme(): Flow<AppColors> =
		dao.getSelectedTheme().map { it?.toDomain() ?: AppColors.Default }.flowOn(dispatchers)

	override suspend fun getCurrentThemeId(): Int? =
		withContext(dispatchers) {
			dao.getSelectedThemeId()
		}

	override suspend fun selectTheme(id: Int) {
		withContext(dispatchers) {
			dao.setSelectedTheme(id)
		}
	}

	override suspend fun insertTheme(theme: AppColors) {
		withContext(dispatchers) {
			dao.insertTheme(theme.toEntity())
		}
	}

	override suspend fun deleteTheme(id: Int) {
		withContext(dispatchers) {
			dao.deleteTheme(id)
		}
	}
}
