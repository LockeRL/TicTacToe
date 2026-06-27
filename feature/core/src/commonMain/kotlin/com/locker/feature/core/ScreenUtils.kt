package com.locker.feature.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
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

@Immutable
data class DeviceConfig(
	val isTablet: Boolean,
	val isWideTablet: Boolean,
	val isExtraWideTablet: Boolean,
	val isLandscape: Boolean,
	val isLandscapeAndNotExtraWide: Boolean,
	val isExtraWideOrLandscape: Boolean,
	val is880: Boolean,
)

val LocalDeviceConfig = compositionLocalOf<DeviceConfig> {
	error("No DeviceConfig provided")
}

@Composable
fun createDeviceConfig(): DeviceConfig {
	val isExtraWideTablet = isExtraWideTablet()
	val isLandscape = isLandscape()
	return DeviceConfig(
		isTablet = isTablet(),
		isWideTablet = isWideTablet(),
		isExtraWideTablet = isExtraWideTablet,
		isLandscape = isLandscape,
		isLandscapeAndNotExtraWide = isLandscape() && !isExtraWideTablet,
		isExtraWideOrLandscape = isExtraWideTablet || isLandscape,
		is880 = is880(),
	)
}
