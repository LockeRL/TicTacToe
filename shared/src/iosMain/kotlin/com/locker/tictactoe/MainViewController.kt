package com.locker.tictactoe

import androidx.compose.ui.window.ComposeUIViewController
import com.locker.tictactoe.di.initKoin

fun MainViewController() = ComposeUIViewController(
	configure = { initKoin() }
) { App() }
