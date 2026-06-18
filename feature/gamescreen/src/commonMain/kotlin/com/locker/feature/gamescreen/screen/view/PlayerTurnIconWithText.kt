package com.locker.feature.gamescreen.screen.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.locker.core.models.Player
import com.locker.feature.component.field.PlayerIconWithState
import com.locker.feature.core.theme.TicTacToeTheme
import com.locker.feature.gamescreen.Res
import com.locker.feature.gamescreen.turn
import org.jetbrains.compose.resources.stringResource

@Composable
fun PlayerTurnIconWithText(
    player: State<Player>,
    modifier: Modifier
) {
    val typography = TicTacToeTheme.typography
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.height(48.dp)
    ) {
        Text(
            text = "${stringResource(Res.string.turn)}:",
            color = TicTacToeTheme.colors.accentContainer,
            style = typography.titleLarge
        )
        PlayerIconWithState(
            player = player,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
        )
    }
}
