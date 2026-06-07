package com.locker.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.locker.core.database.entity.ColorThemeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorThemeDao {
    @Query("SELECT * FROM ColorThemeEntity")
    fun getAllThemes(): Flow<List<ColorThemeEntity>>

    @Query("SELECT * FROM ColorThemeEntity WHERE isSelected = 1 LIMIT 1")
    fun getSelectedTheme(): Flow<ColorThemeEntity?>

    @Insert
    suspend fun insertThemes(themes: List<ColorThemeEntity>)

    @Query("UPDATE ColorThemeEntity SET isSelected = 0")
    suspend fun deselectAll()

    @Query("UPDATE ColorThemeEntity SET isSelected = 1 WHERE id = :themeId")
    suspend fun selectTheme(themeId: Int)

    @Transaction
    suspend fun setSelectedTheme(themeId: Int) {
        deselectAll()
        selectTheme(themeId)
    }

    @Query("SELECT COUNT(*) FROM ColorThemeEntity")
    suspend fun getCount(): Int
}
