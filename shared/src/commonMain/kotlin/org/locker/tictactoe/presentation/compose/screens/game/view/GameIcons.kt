package org.locker.tictactoe.presentation.compose.screens.game.view

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.locker.tictactoe.presentation.model.Player
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tictactoe.shared.generated.resources.Res
import tictactoe.shared.generated.resources.ic_circle
import tictactoe.shared.generated.resources.ic_cross

@Composable
fun PlayerIconWithState(
    playerState: StateFlow<Player>,
    modifier: Modifier = Modifier
) {
    val player by playerState.collectAsState()
    PlayerIcon(
        player = player,
        modifier = modifier
    )
}

@Composable
fun PlayerIcon(
    player: Player,
    modifier: Modifier = Modifier
) {
    Icon(
        painter = painterResource(
            when(player) {
                Player.CIRCLE -> Res.drawable.ic_circle
                Player.CROSS -> Res.drawable.ic_cross
            }
        ),
        tint = MaterialTheme.colorScheme.primaryContainer,
        contentDescription = stringResource(
            when(player) {
                Player.CIRCLE -> Res.string.ic_circle
                Player.CROSS -> Res.string.ic_cross
            }
        ),
        modifier = modifier
    )
}
