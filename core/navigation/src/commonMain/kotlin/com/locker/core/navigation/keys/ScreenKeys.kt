package com.locker.core.navigation.keys

import com.locker.core.models.Difficulty
import com.locker.core.navigation.keys.navmodel.PlayerNavModel
import kotlinx.serialization.Serializable

@Serializable
data object MainScreenNavKey : BaseNavKey

@Serializable
data object GameScreenNavKey : BaseNavKey

@Serializable
data class BotGameScreenNavKey(val difficulty: Difficulty, val playerSymbol: PlayerNavModel) : BaseNavKey
