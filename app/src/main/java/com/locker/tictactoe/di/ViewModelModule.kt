package com.locker.tictactoe.di

import com.locker.tictactoe.presentation.ColorsViewModel
import com.locker.tictactoe.presentation.compose.navigation.viewmodel.GameNavigationViewModel
import com.locker.tictactoe.presentation.compose.navigation.viewmodel.NavigationViewModel
import com.locker.tictactoe.presentation.compose.screens.game.viewmodel.GameViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { NavigationViewModel() }
    viewModel { GameNavigationViewModel() }
    viewModel { GameViewModel(get()) }
    viewModel { ColorsViewModel() }
}
