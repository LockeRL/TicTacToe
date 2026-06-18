package com.locker.feature.mainscreen.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.locker.core.models.Difficulty
import com.locker.core.models.Player
import com.locker.feature.component.DifficultySelector
import com.locker.feature.component.PlayerSelector
import com.locker.feature.component.TicTacToeButton
import com.locker.feature.core.screen.LocalFireEvent
import com.locker.feature.core.screen.ProvideScreenEvents
import com.locker.feature.core.theme.MENU_BUTTON_PERCENT
import com.locker.feature.core.theme.TicTacToeTheme
import com.locker.feature.mainscreen.screen.event.BotPlayClickEvent
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
	) { vm ->
		val screenState = vm.screenState.collectAsState()
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
	val selectedDifficulty = remember { mutableStateOf(Difficulty.MEDIUM) }
	val selectedSymbol = remember { mutableStateOf(Player.CROSS) }

	Box(modifier = modifier) {
		Column(
			verticalArrangement = Arrangement.SpaceBetween,
			horizontalAlignment = Alignment.CenterHorizontally,
			modifier = Modifier.fillMaxSize().padding(vertical = 48.dp)
		) {
			MainMenuText(
				state = state,
				modifier = Modifier.fillMaxWidth()
			)

			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.spacedBy(24.dp)
			) {
				Text(
					text = "Choose Your Symbol",
					color = TicTacToeTheme.colors.accent,
					fontSize = 18.sp
				)

				PlayerSelector(
					selectedPlayer = selectedSymbol.value,
					onSymbolSelected = { selectedSymbol.value = it }
				)

				TicTacToeButton(
					text = state.playButton,
					onClick = { fireEvent(PlayClickEvent) },
					modifier = Modifier.fillMaxWidth(MENU_BUTTON_PERCENT)
				)

				TicTacToeButton(
					text = "Play vs Bot",
					onClick = {
						fireEvent(
							BotPlayClickEvent(
								selectedDifficulty.value,
								selectedSymbol.value
							)
						)
					},
					modifier = Modifier.fillMaxWidth(MENU_BUTTON_PERCENT)
				)
			}

			DifficultySelector(
				selectedDifficulty = selectedDifficulty.value,
				onDifficultySelected = { selectedDifficulty.value = it },
				modifier = Modifier.padding(bottom = 0.dp)
			)
		}
	}
}
