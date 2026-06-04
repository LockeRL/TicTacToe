package com.locker.feature.core.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalFireEvent = staticCompositionLocalOf<(ScreenEvent) -> Unit> {
	error("LocalFireEvent not provided")
}

@Composable
fun <VM : BaseViewModel> ProvideScreenEvents(
	viewModel: VM,
	content: @Composable (VM) -> Unit
) {
	CompositionLocalProvider(
		LocalFireEvent provides viewModel::fireEvent
	) {
		content(viewModel)
	}
}
