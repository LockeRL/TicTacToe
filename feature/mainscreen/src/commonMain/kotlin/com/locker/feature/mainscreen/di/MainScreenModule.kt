package com.locker.feature.mainscreen.di

import com.locker.core.navigation.keys.MainScreenNavKey
import com.locker.feature.mainscreen.screen.MainScreen
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation

@OptIn(KoinExperimentalAPI::class)
val mainScreenModule = module {
	navigation<MainScreenNavKey> {
		MainScreen()
	}
}
