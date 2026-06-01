package org.locker.tictactoe.di

import com.locker.tictactoe.presentation.util.CellClickEventHandler
import com.locker.tictactoe.presentation.util.GameField
import com.locker.tictactoe.presentation.util.GameLogic
import org.koin.dsl.module

val fieldModule = module {
    factory { GameField() }
    factory { GameLogic(get(), get()) }
    single { CellClickEventHandler() }
}
