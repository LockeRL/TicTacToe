package com.locker.feature.gamescreen.screen.view

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.locker.feature.core.theme.TicTacToeTheme
import com.locker.feature.gamescreen.controller.model.Player
import org.jetbrains.compose.resources.painterResource

@Composable
fun PlayerIconWithState(
    player: State<Player>,
    modifier: Modifier = Modifier
) {
    PlayerIcon(
        player = player.value,
        modifier = modifier
    )
}

@Composable
fun PlayerIcon(
    player: Player,
    modifier: Modifier = Modifier
) {
    Icon(
        painter = painterResource(player.icon),
        tint = TicTacToeTheme.colors.accentContainer,
        contentDescription = null,
        modifier = modifier
    )
}
