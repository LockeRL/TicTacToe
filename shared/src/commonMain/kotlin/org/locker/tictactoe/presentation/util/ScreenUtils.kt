package org.locker.tictactoe.presentation.util

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun screenWidthDp(): Dp {
	val density = LocalDensity.current
	val containerSize = LocalWindowInfo.current.containerSize
	return with(density) { containerSize.width.toDp() }
}

@Composable
fun screenHeightDp(): Dp {
	val density = LocalDensity.current
	val containerSize = LocalWindowInfo.current.containerSize
	return with(density) { containerSize.height.toDp() }
}

@Composable
fun screenWidthPx(): Int = LocalWindowInfo.current.containerSize.width

@Composable
fun screenHeightPx(): Int = LocalWindowInfo.current.containerSize.height

@Composable
fun is600(): Boolean = screenWidthDp() >= 600.dp

@Composable
fun isTablet(): Boolean {
	val screenWidth = screenWidthDp()
	return screenWidth >= 600.dp && screenWidth < 880.dp
}

@Composable
fun is880(): Boolean = screenWidthDp() >= 880.dp

@Composable
fun isWideTablet(): Boolean {
	val screenWidth = screenWidthDp()
	return screenWidth >= 880.dp && screenWidth < 1160.dp
}

@Composable
fun isLandscape(): Boolean = screenWidthPx() > screenHeightPx()

@Composable
fun isExtraWideTablet(): Boolean = screenWidthDp() >= 1160.dp

@Composable
fun statusBarHeightDp(): Dp {
	val density = LocalDensity.current
	val statusBar = WindowInsets.statusBars.getTop(density)
	return with(density) { statusBar.toDp() }
}

@Composable
fun navigationBarHeightDp(): Dp {
	val density = LocalDensity.current
	val navigationBar = WindowInsets.navigationBars.getBottom(density)
	return with(density) { navigationBar.toDp() }
}
