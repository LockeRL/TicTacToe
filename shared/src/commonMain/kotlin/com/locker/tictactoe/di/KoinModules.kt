package com.locker.tictactoe.di

import com.locker.core.data.di.repositoryModule
import com.locker.core.database.di.databaseModule
import com.locker.core.navigation.di.navigationModule
import com.locker.feature.colorpicker.di.colorPickerModule
import com.locker.feature.gamebotscreen.di.botGameModule
import com.locker.feature.gamescreen.di.gameScreenModule
import com.locker.feature.mainscreen.di.mainScreenModule
import org.koin.core.module.Module

internal val coreModules: List<Module> = listOf(
	navigationModule,
	databaseModule,
	colorPickerModule,
	repositoryModule,
)

internal val screenModules: List<Module> = listOf(
	mainScreenModule,
	gameScreenModule,
	botGameModule,
)

expect val platformModules: List<Module>
