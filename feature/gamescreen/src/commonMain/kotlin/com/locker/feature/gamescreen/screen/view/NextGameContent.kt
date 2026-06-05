package com.locker.feature.gamescreen.screen.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.locker.feature.component.TicTacToeButton
import com.locker.feature.core.screen.LocalFireEvent
import com.locker.feature.core.theme.MENU_BUTTON_PERCENT
import com.locker.feature.core.theme.Size64
import com.locker.feature.core.theme.Space8
import com.locker.feature.core.theme.TicTacToeTheme
import com.locker.feature.gamescreen.Res
import com.locker.feature.gamescreen.main_menu
import com.locker.feature.gamescreen.next_game
import com.locker.feature.gamescreen.screen.event.MainMenuEvent
import com.locker.feature.gamescreen.screen.event.NextGameEvent
import com.locker.feature.gamescreen.screen.model.EndGameScreenState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun NextGameContent(
	endGame: EndGameScreenState,
	modifier: Modifier = Modifier
) {
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
				tint = TicTacToeTheme.colors.accentContainer,
				contentDescription = null,
				modifier = Modifier.size(Size64)
			)
		}

		Text(
			text = endGame.title,
			color = MaterialTheme.colorScheme.primaryContainer,
			style = typography.titleLarge,
			modifier = Modifier.padding(Space8)
		)

		Column(
			verticalArrangement = Arrangement.spacedBy(Space8),
			horizontalAlignment = Alignment.CenterHorizontally,
			modifier = Modifier.fillMaxWidth(),
		) {
			TicTacToeButton(
				text = stringResource(Res.string.next_game),
				onClick = { fireEvent(NextGameEvent) },
				modifier = Modifier.fillMaxWidth(MENU_BUTTON_PERCENT)
			)

			TicTacToeButton(
				text = stringResource(Res.string.main_menu),
				onClick = { fireEvent(MainMenuEvent) },
				modifier = Modifier.fillMaxWidth(MENU_BUTTON_PERCENT)
			)
		}
	}
}
