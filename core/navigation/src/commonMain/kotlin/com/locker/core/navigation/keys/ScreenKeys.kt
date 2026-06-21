package com.locker.core.navigation.keys

import com.locker.core.navigation.keys.navmodel.DifficultyNavModel
import com.locker.core.navigation.keys.navmodel.PlayerNavModel
import kotlinx.serialization.Serializable

@Serializable
data object MainScreenNavKey : BaseNavKey

@Serializable
data object TutorialScreenNavKey : BaseNavKey

@Serializable
data object GameScreenNavKey : BaseNavKey

@Serializable
data object SettingsScreenNavKey : BaseNavKey

@Serializable
data class BotGameScreenNavKey(val difficulty: DifficultyNavModel, val playerSymbol: PlayerNavModel) : BaseNavKey
