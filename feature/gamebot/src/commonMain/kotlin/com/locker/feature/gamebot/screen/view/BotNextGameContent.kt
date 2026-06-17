package com.locker.feature.gamebot.screen.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.locker.core.models.Difficulty
import com.locker.core.models.Player
import com.locker.feature.component.DifficultySelector
import com.locker.feature.component.SymbolSelector
import com.locker.feature.component.TicTacToeButton
import com.locker.feature.core.screen.LocalFireEvent
import com.locker.feature.core.theme.MENU_BUTTON_PERCENT
import com.locker.feature.core.theme.Size64
import com.locker.feature.core.theme.Space8
import com.locker.feature.core.theme.TicTacToeTheme
import com.locker.feature.gamebot.screen.event.UpdateBotSettingsEvent
import com.locker.feature.gamescreen.screen.event.MainMenuEvent
import com.locker.feature.gamescreen.screen.event.NextGameEvent
import com.locker.feature.gamescreen.screen.model.EndGameScreenState
import com.locker.resources.Res
import com.locker.resources.main_menu
import com.locker.resources.next_game
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun BotNextGameContent(
    endGame: EndGameScreenState,
    currentDifficulty: Difficulty,
    currentUserPlayer: Player,
    modifier: Modifier = Modifier
) {
    val typography = TicTacToeTheme.typography
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

        Spacer(modifier = Modifier.height(16.dp))

        SymbolSelector(
            selectedSymbol = if (currentUserPlayer == Player.CROSS) "cross" else "circle",
            onSymbolSelected = { 
                val player = if (it == "cross") Player.CROSS else Player.CIRCLE
                fireEvent(UpdateBotSettingsEvent(currentDifficulty, player))
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

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

        Spacer(modifier = Modifier.height(32.dp))

        DifficultySelector(
            selectedDifficulty = currentDifficulty.name.lowercase(),
            onDifficultySelected = {
                val difficulty = try {
                    Difficulty.valueOf(it.uppercase())
                } catch (_: Exception) {
                    currentDifficulty
                }
                fireEvent(UpdateBotSettingsEvent(difficulty, currentUserPlayer))
            }
        )
    }
}
