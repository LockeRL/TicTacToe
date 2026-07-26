package com.locker.feature.gamescreen.screen.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.locker.feature.component.TicTacToeButton
import com.locker.feature.core.screen.LocalFireEvent
import com.locker.feature.core.theme.MENU_BUTTON_PERCENT
import com.locker.feature.core.theme.TicTacToeTheme
import com.locker.feature.gamescreen.screen.event.MainMenuEvent
import com.locker.feature.gamescreen.screen.event.NextGameEvent
import com.locker.feature.gamescreen.screen.model.EndGameScreenState
import org.jetbrains.compose.resources.painterResource

@Composable
fun NextGameContent(
	endGame: EndGameScreenState,
	modifier: Modifier = Modifier
) {
	val colors = TicTacToeTheme.colors
	val typography = TicTacToeTheme.typography
	val fireEvent = LocalFireEvent.current
	Column(
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
		modifier = modifier
	) {
		if (endGame.icon != null) {
			Icon(
				painter = painterResource(endGame.icon),
				tint = colors.accentContainer,
				contentDescription = null,
				modifier = Modifier.size(64.dp)
			)
		}

		Text(
			text = endGame.title,
			color = colors.accentContainer,
			style = typography.titleLarge,
			modifier = Modifier.padding(
				top = 8.dp,
				bottom = 32.dp,
			)
		)

		Column(
			verticalArrangement = Arrangement.spacedBy(8.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
			modifier = Modifier.fillMaxWidth(),
		) {
			TicTacToeButton(
				text = endGame.nextGame,
				onClick = { fireEvent(NextGameEvent) },
				modifier = Modifier.fillMaxWidth(MENU_BUTTON_PERCENT)
			)

			TicTacToeButton(
				text = endGame.mainMenu,
				onClick = { fireEvent(MainMenuEvent) },
				modifier = Modifier.fillMaxWidth(MENU_BUTTON_PERCENT)
			)
		}
	}
}
