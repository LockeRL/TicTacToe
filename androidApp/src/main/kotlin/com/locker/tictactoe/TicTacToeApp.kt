package com.locker.tictactoe

import android.app.Application
import com.locker.tictactoe.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class TicTacToeApp : Application() {
	override fun onCreate() {
		super.onCreate()

		initKoin {
			androidContext(this@TicTacToeApp)
			androidLogger()
		}
	}
}
