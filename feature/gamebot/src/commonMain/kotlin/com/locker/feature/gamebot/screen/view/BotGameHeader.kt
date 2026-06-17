package com.locker.feature.gamebot.screen.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.locker.core.models.Player
import com.locker.feature.core.theme.Size48
import com.locker.feature.core.theme.TicTacToeTheme
import com.locker.feature.gamescreen.screen.view.PlayerIcon
import com.locker.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun BotGameHeader(
    activePlayer: Player,
    userPlayer: Player,
    isBotThinking: Boolean,
    modifier: Modifier = Modifier
) {
    val colors = TicTacToeTheme.colors
    val typography = TicTacToeTheme.typography

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.height(Size48)
        ) {
            Text(
                text = "${stringResource(Res.string.playing_as)}: ",
                color = colors.accentContainer,
                style = typography.titleMedium
            )
            Box(modifier = Modifier.size(32.dp)) {
                PlayerIcon(
                    player = userPlayer,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Text(
            text = if (isBotThinking) {
                stringResource(Res.string.bot_thinking)
            } else if (activePlayer == userPlayer) {
                stringResource(Res.string.your_turn)
            } else {
                stringResource(Res.string.bot_turn)
            },
            color = if (activePlayer == userPlayer) colors.accent else colors.accentContainer,
            style = typography.titleLarge,
            fontSize = 24.sp
        )
    }
}
