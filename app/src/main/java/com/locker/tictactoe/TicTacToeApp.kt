package com.locker.tictactoe

import android.app.Application
import com.locker.tictactoe.di.fieldModule
import com.locker.tictactoe.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TicTacToeApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)

            modules(
                viewModelModule,
                fieldModule
            )
        }
    }
}
