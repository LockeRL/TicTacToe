package org.locker.tictactoe.di

import org.koin.core.module.Module

val sharedModules = listOf(
	viewModelModule,
	fieldModule
)

expect val platformModules: List<Module>
