package com.locker.tictactoe.navigation

import com.locker.core.navigation.keys.BaseNavKey
import com.locker.core.navigation.keys.GameScreenNavKey
import com.locker.core.navigation.keys.MainScreenNavKey
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

internal data class TopLevelNavItem(
	val selectedIconRes: DrawableResource? = null,
	val unselectedIconRes: DrawableResource? = null,
	val labelRes: StringResource? = null,
)

private val HOME = TopLevelNavItem()
private val GAME = TopLevelNavItem()

internal val TOP_LEVEL_NAV_ITEMS: Map<BaseNavKey, TopLevelNavItem> = mapOf(
	GameScreenNavKey to GAME,
	MainScreenNavKey to HOME
)
