package com.locker.feature.component.field

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.locker.core.models.Player
import com.locker.feature.core.theme.TicTacToeTheme
import org.jetbrains.compose.resources.painterResource

@Composable
fun PlayerIconWithState(
    player: State<Player>,
    modifier: Modifier = Modifier
) {
    PlayerIcon(
        player = player.value,
        modifier = modifier,
    )
}

@Composable
fun PlayerIcon(
    player: Player,
    modifier: Modifier = Modifier,
    color: Color? = null,
) {
    Icon(
        painter = painterResource(player.icon),
        tint = color ?: player.color(),
        contentDescription = null,
        modifier = modifier,
    )
}

@Composable
fun Player.color(): Color {
    val colors = TicTacToeTheme.colors
    return if (this == Player.CROSS) colors.accent else colors.additional
}
