package org.locker.tictactoe

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.locker.tictactoe.di.initKoin

class TicTacToeApp : Application() {
	override fun onCreate() {
		super.onCreate()

		initKoin {
			androidContext(this@TicTacToeApp)
			androidLogger()
		}
	}
}
