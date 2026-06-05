package com.locker.feature.mainscreen.di

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.locker.core.navigation.keys.MainScreenNavKey
import com.locker.feature.mainscreen.screen.MainScreen
import com.locker.feature.mainscreen.screen.MainScreenViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation

@OptIn(KoinExperimentalAPI::class)
val mainScreenModule = module {
	viewModel { MainScreenViewModel(navigator = get()) }

	navigation<MainScreenNavKey> {
		MainScreen(modifier = Modifier.fillMaxSize())
	}
}
