package com.locker.feature.settingsscreen.di

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import com.locker.core.navigation.keys.SettingsScreenNavKey
import com.locker.feature.settingsscreen.screen.SettingsScreen
import com.locker.feature.settingsscreen.screen.SettingsViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation

@OptIn(KoinExperimentalAPI::class)
val settingsScreenModule = module {
    viewModel { SettingsViewModel(themeRepository = get()) }

    navigation<SettingsScreenNavKey> {
        SettingsScreen(modifier = Modifier.fillMaxWidth())
    }
}
