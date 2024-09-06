package com.locker.tictactoe.presentation.compose.screens.game.view

import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.locker.tictactoe.R
import com.locker.tictactoe.presentation.model.Player
import kotlinx.coroutines.flow.StateFlow

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
        imageVector = ImageVector.vectorResource(
            id = when(player) {
                Player.CIRCLE -> R.drawable.ic_circle
                Player.CROSS -> R.drawable.ic_cross
            }
        ),
        tint = MaterialTheme.colors.primaryVariant,
        contentDescription = stringResource(
            id = when(player) {
                Player.CIRCLE -> R.string.ic_circle
                Player.CROSS -> R.string.ic_circle
            }
        ),
        modifier = modifier
    )
}

@Composable
fun PlayerIconNullable(
    player: Player?,
    modifier: Modifier = Modifier
) {
    if (player != null)
        PlayerIcon(
            player = player,
            modifier = modifier
        )
}
