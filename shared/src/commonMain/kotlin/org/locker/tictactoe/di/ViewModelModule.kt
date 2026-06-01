package org.locker.tictactoe.di

import com.locker.tictactoe.presentation.ColorsViewModel
import org.locker.tictactoe.presentation.compose.navigation.viewmodel.GameNavigationViewModel
import org.locker.tictactoe.presentation.compose.navigation.viewmodel.NavigationViewModel
import org.locker.tictactoe.presentation.compose.screens.game.viewmodel.GameViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { NavigationViewModel() }
    viewModel { GameNavigationViewModel() }
    viewModel { GameViewModel(get(), get()) }
    viewModel { ColorsViewModel() }
}
