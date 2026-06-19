package com.locker.tictactoe

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.ui.NavDisplay
import com.locker.core.navigation.Navigator
import com.locker.core.navigation.rememberNavigationState
import com.locker.core.navigation.toEntries
import com.locker.core.navigation.keys.MainScreenNavKey
import com.locker.core.navigation.keys.SettingsScreenNavKey
import com.locker.feature.colorpicker.view.ColorsViewModel
import com.locker.feature.component.TicTacToeIconButton
import com.locker.feature.core.theme.SCREEN_CHANGE_ANIM_DURATION
import com.locker.feature.core.theme.TicTacToeTheme
import com.locker.resources.Res
import com.locker.resources.ic_back
import com.locker.resources.ic_settings
import com.locker.tictactoe.component.TopAppBar
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun App(
	navigator: Navigator = koinInject(),
	colorsViewModel: ColorsViewModel = koinInject(),
) {
	navigator.state = rememberNavigationState(MainScreenNavKey, setOf(MainScreenNavKey))
	val snackbarHostState = remember { SnackbarHostState() }

	val colors by colorsViewModel.currentColorTheme.collectAsState()
	val theme = colors ?: return

	TicTacToeTheme(
		appColors = theme,
	) {
		val colors = TicTacToeTheme.colors
		val backgroundColor = colors.background
		Scaffold(
			contentWindowInsets = WindowInsets(0, 0, 0, 0),
			topBar = {
				val currentKey = navigator.state.currentKey
				TopAppBar(
					colorsViewModel = colorsViewModel,
					navigationContent = {
						if (currentKey == MainScreenNavKey) {
							TicTacToeIconButton(
								icon = painterResource(Res.drawable.ic_settings),
								shape = CircleShape,
								onClick = { navigator.navigate(SettingsScreenNavKey) },
								contentDescription = "Settings",
								contentColor = colors.additionalContainer,
								iconModifier = Modifier.size(32.dp),
								modifier = Modifier.size(48.dp),
							)
						} else {
							TicTacToeIconButton(
								icon = painterResource(Res.drawable.ic_back),
								onClick = navigator::goBack,
								contentDescription = "Back",
								contentColor = colors.additionalContainer,
								iconModifier = Modifier.size(24.dp),
								modifier = Modifier.size(40.dp)
							)
						}
					},
					modifier = Modifier
						.background(backgroundColor)
						.windowInsetsPadding(WindowInsets.statusBars)
						.fillMaxWidth()
				)
			},
			snackbarHost = {
				SnackbarHost(
					modifier = Modifier.windowInsetsPadding(
						WindowInsets.safeDrawing.exclude(
							WindowInsets.ime
						)
					),
					hostState = snackbarHostState,
				)
			},
			containerColor = backgroundColor,
			modifier = Modifier
				.background(backgroundColor)
				.fillMaxSize()
				.padding(all = 16.dp)
		) { innerPadding ->
			//            CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
			NavDisplay(
				modifier = Modifier
					.padding(innerPadding)
					.consumeWindowInsets(innerPadding)
					.windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)),
				entries = navigator.state.toEntries(),
				onBack = navigator::goBack,
				transitionSpec = {
					fadeIn(tween(SCREEN_CHANGE_ANIM_DURATION)) togetherWith fadeOut(tween(SCREEN_CHANGE_ANIM_DURATION))
				},
				popTransitionSpec = {
					fadeIn(tween(SCREEN_CHANGE_ANIM_DURATION)) togetherWith fadeOut(tween(SCREEN_CHANGE_ANIM_DURATION))
				},
				predictivePopTransitionSpec = {
					fadeIn(tween(SCREEN_CHANGE_ANIM_DURATION)) togetherWith fadeOut(tween(SCREEN_CHANGE_ANIM_DURATION))
				}
			)
//            }
		}
	}
}
