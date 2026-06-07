package com.locker.core.database

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.locker.core.database.dao.ColorThemeDao
import com.locker.core.database.entity.ColorThemeEntity
import com.locker.feature.core.model.AppColors
import com.locker.feature.core.theme.ColorsList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository(private val dao: ColorThemeDao) {

    suspend fun initializeIfEmpty() {
        if (dao.getCount() == 0) {
            val entities = ColorsList.mapIndexed { index, colors ->
                ColorThemeEntity(
                    id = index,
                    background = colors.background.toArgb().toLong(),
                    accent = colors.accent.toArgb().toLong(),
                    additional = colors.additional.toArgb().toLong(),
                    additionalContainer = colors.additionalContainer.toArgb().toLong(),
                    accentContainer = colors.accentContainer.toArgb().toLong(),
                    isSelected = index == 0
                )
            }
            dao.insertThemes(entities)
        }
    }

    fun getSelectedColors(): Flow<AppColors?> {
        return dao.getSelectedTheme().map { entity ->
            entity?.let {
                AppColors(
                    background = Color(it.background),
                    accent = Color(it.accent),
                    additional = Color(it.additional),
                    additionalContainer = Color(it.additionalContainer),
                    accentContainer = Color(it.accentContainer)
                )
            }
        }
    }

    fun getAllThemes(): Flow<List<Pair<Int, AppColors>>> {
        return dao.getAllThemes().map { list ->
            list.map { entity ->
                entity.id to AppColors(
                    background = Color(entity.background),
                    accent = Color(entity.accent),
                    additional = Color(entity.additional),
                    additionalContainer = Color(entity.additionalContainer),
                    accentContainer = Color(entity.accentContainer)
                )
            }
        }
    }

    suspend fun selectTheme(themeId: Int) {
        dao.setSelectedTheme(themeId)
    }
}
