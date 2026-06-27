package com.locker.feature.mainscreen.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.locker.core.models.Difficulty
import com.locker.core.models.Player
import com.locker.feature.component.DifficultySelector
import com.locker.feature.component.PlayerSelector
import com.locker.feature.component.TicTacToeButton
import com.locker.feature.core.screen.LocalFireEvent
import com.locker.feature.core.screen.ProvideScreenEvents
import com.locker.feature.core.theme.MENU_BUTTON_PERCENT
import com.locker.feature.core.theme.MENU_BUTTON_SMALL_PERCENT
import com.locker.feature.core.theme.TicTacToeTheme
import com.locker.feature.mainscreen.screen.event.BotPlayClickEvent
import com.locker.feature.mainscreen.screen.event.PlayClickEvent
import com.locker.feature.mainscreen.screen.event.RulesClickEvent
import com.locker.feature.mainscreen.screen.model.MainScreenState
import com.locker.feature.mainscreen.screen.view.MainMenuText
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen(
	viewModel: MainScreenViewModel = koinViewModel(),
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
	val colors = TicTacToeTheme.colors
	val selectedDifficulty = remember { mutableStateOf(Difficulty.MEDIUM) }
	val selectedSymbol = remember { mutableStateOf(Player.CROSS) }

	Box(
		contentAlignment = Alignment.Center,
		modifier = modifier
	) {
		Column(
			verticalArrangement = Arrangement.spacedBy(48.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
			modifier = Modifier
				.fillMaxWidth()
		) {
			MainMenuText(
				state = state,
				modifier = Modifier.fillMaxWidth()
			)

			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.spacedBy(40.dp)
			) {
				Column(
					horizontalAlignment = Alignment.CenterHorizontally,
					verticalArrangement = Arrangement.spacedBy(24.dp)
				) {
					PlayerSelector(
						selectedPlayer = selectedSymbol.value,
						onSymbolSelected = { selectedSymbol.value = it }
					)

					DifficultySelector(
						selectedDifficulty = selectedDifficulty.value,
						onDifficultySelected = { selectedDifficulty.value = it },
						modifier = Modifier.fillMaxWidth(MENU_BUTTON_PERCENT)
					)

					TicTacToeButton(
						text = state.playVsBot,
						onClick = {
							fireEvent(
								BotPlayClickEvent(
									difficulty = selectedDifficulty.value,
									player = selectedSymbol.value
								)
							)
						},
						modifier = Modifier.fillMaxWidth(MENU_BUTTON_PERCENT)
					)

					TicTacToeButton(
						text = state.playVsFriend,
						onClick = { fireEvent(PlayClickEvent) },
						modifier = Modifier.fillMaxWidth(MENU_BUTTON_PERCENT)
					)
				}

				TicTacToeButton(
					text = state.rules,
					onClick = { fireEvent(RulesClickEvent) },
					modifier = Modifier.fillMaxWidth(MENU_BUTTON_SMALL_PERCENT),
					containerColor = Color.Transparent,
					contentColor = colors.additional,
					borderStroke = BorderStroke(width = 2.dp, color = colors.additional)
				)
			}
		}
	}
}
