package com.locker.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.locker.core.database.entity.ColorThemeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorThemeDao {
	@Query("SELECT * FROM ColorThemeEntity")
	fun getAllThemes(): Flow<List<ColorThemeEntity>>

	@Query("SELECT * FROM ColorThemeEntity WHERE isSystem = false")
	fun getUserThemes(): Flow<List<ColorThemeEntity>>

	@Query("SELECT * FROM ColorThemeEntity WHERE isSelected = true LIMIT 1")
	fun getSelectedTheme(): Flow<ColorThemeEntity?>

	@Query("SELECT id FROM ColorThemeEntity WHERE isSelected = true LIMIT 1")
	suspend fun getSelectedThemeId(): Int?

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertThemes(themes: List<ColorThemeEntity>)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertTheme(theme: ColorThemeEntity)

	@Query("UPDATE ColorThemeEntity SET isSelected = false")
	suspend fun deselectAll()

	@Query("UPDATE ColorThemeEntity SET isSelected = true WHERE id = :themeId")
	suspend fun selectTheme(themeId: Int)

	@Query("DELETE FROM ColorThemeEntity WHERE id = :themeId")
	suspend fun deleteTheme(themeId: Int)

	@Transaction
	suspend fun setSelectedTheme(themeId: Int) {
		deselectAll()
		selectTheme(themeId)
	}

	@Query("SELECT COUNT(*) FROM ColorThemeEntity")
	suspend fun getCount(): Int
}
