package com.locker.feature.gamebotscreen.screen.view

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
import com.locker.feature.component.DifficultySelector
import com.locker.feature.component.PlayerSelector
import com.locker.feature.component.TicTacToeButton
import com.locker.feature.core.screen.LocalFireEvent
import com.locker.feature.core.theme.MENU_BUTTON_PERCENT
import com.locker.feature.core.theme.Size64
import com.locker.feature.core.theme.Space8
import com.locker.feature.core.theme.TicTacToeTheme
import com.locker.feature.gamebotscreen.screen.event.MainMenuEvent
import com.locker.feature.gamebotscreen.screen.event.NextGameEvent
import com.locker.feature.gamebotscreen.screen.event.UpdateBotSettingsEvent
import com.locker.feature.gamebotscreen.screen.model.EndGameScreenState
import org.jetbrains.compose.resources.painterResource

@Composable
fun NextGameContent(
    endGame: EndGameScreenState,
    modifier: Modifier = Modifier
) {
    val typography = TicTacToeTheme.typography
    val colors = TicTacToeTheme.colors
    val fireEvent = LocalFireEvent.current

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(bottom = 32.dp)
    ) {
        val icon = endGame.icon
        if (icon != null) {
            Icon(
                painter = painterResource(icon),
                tint = colors.accentContainer,
                contentDescription = null,
                modifier = Modifier.size(Size64)
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
        ) {

            Text(
                text = endGame.title,
                color = colors.accentContainer,
                style = typography.titleLarge,
                modifier = Modifier.padding(Space8)
            )

            PlayerSelector(
                selectedPlayer = endGame.player,
                onSymbolSelected = {
                    fireEvent(UpdateBotSettingsEvent(endGame.difficulty, it))
                }
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(Space8),
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

            DifficultySelector(
                selectedDifficulty = endGame.difficulty,
                onDifficultySelected = {
                    fireEvent(UpdateBotSettingsEvent(it, endGame.player))
                }
            )
        }
    }
}
