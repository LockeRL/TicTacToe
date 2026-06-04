package com.locker.core.navigation.keys


import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable


@Serializable
sealed interface BaseNavKey : NavKey {
}
