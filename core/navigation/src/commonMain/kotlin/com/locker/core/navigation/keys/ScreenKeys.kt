package com.locker.core.navigation.keys

import com.locker.models.Player
import kotlinx.serialization.Serializable

@Serializable
data object MainScreenNavKey : BaseNavKey

@Serializable
data object GameScreenNavKey : BaseNavKey

@Serializable
data class EndGameScreenNavKey(val winner: Player?) : BaseNavKey
