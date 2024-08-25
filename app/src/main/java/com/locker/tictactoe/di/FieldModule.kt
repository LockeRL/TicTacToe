package com.locker.tictactoe.di

import com.locker.tictactoe.presentation.util.GameField
import org.koin.dsl.module

val fieldModule = module {
    factory { GameField() }
}
