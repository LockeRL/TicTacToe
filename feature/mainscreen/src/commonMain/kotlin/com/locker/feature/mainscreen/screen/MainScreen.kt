package com.locker.feature.mainscreen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.locker.feature.component.TicTacToeButton
import com.locker.feature.core.screen.LocalFireEvent
import com.locker.feature.core.screen.ProvideScreenEvents
import com.locker.feature.core.theme.MENU_BUTTON_PERCENT
import com.locker.feature.core.theme.Space16
import com.locker.feature.core.theme.TicTacToeTheme
import com.locker.feature.mainscreen.screen.event.PlayClickEvent
import com.locker.feature.mainscreen.screen.model.MainScreenState
import com.locker.feature.mainscreen.screen.view.MainMenuText
import org.koin.compose.koinInject

@Composable
fun MainScreen(
	viewModel: MainScreenViewModel = koinInject(),
	modifier: Modifier = Modifier
) {
	ProvideScreenEvents(
		viewModel = viewModel
	) { viewModel ->
		val screenState = viewModel.screenState.collectAsState()
		MainScreenContent(
			state = screenState.value,
			modifier = modifier
		)
	}
}

@Composable
fun MainScreenContent(
	state: MainScreenState,
	modifier: Modifier = Modifier
) {
	val fireEvent = LocalFireEvent.current
	Box(modifier = modifier) {
		Column(
			verticalArrangement = Arrangement.SpaceEvenly,
			horizontalAlignment = Alignment.CenterHorizontally,
			modifier = Modifier.fillMaxSize()
		) {
			MainMenuText(
				state = state,
				modifier = Modifier.fillMaxWidth()
			)

			TicTacToeButton(
				text = state.playButton,
				onClick = { fireEvent(PlayClickEvent) },
				modifier = Modifier.fillMaxWidth(MENU_BUTTON_PERCENT)
			)
		}
	}
}

@Preview
@Composable
fun MainScreenPreview() {
	TicTacToeTheme {
		CompositionLocalProvider(LocalFireEvent provides { _ -> }) {
			MainScreenContent(
				state = MainScreenState(
					firstTitle = "aboba",
					secondTitle = "abobus",
					playButton = "poop"
				),
				modifier = Modifier
					.fillMaxSize()
					.background(Color.Black)
					.padding(Space16)
			)
		}
	}
}
