package org.locker.tictactoe.presentation.compose.screens.game.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.locker.tictactoe.presentation.model.Player
import org.locker.tictactoe.presentation.theme.Size48
import org.locker.tictactoe.presentation.theme.Typography
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.stringResource
import tictactoe.shared.generated.resources.Res
import tictactoe.shared.generated.resources.turn

@Composable
fun PlayerTurnIconWithText(
    player: StateFlow<Player>,
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.height(Size48)
    ) {
        Text(
            text = "${stringResource(Res.string.turn)}:",
            color = MaterialTheme.colorScheme.primaryContainer,
            style = Typography.titleLarge
        )
        PlayerIconWithState(
            playerState = player,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
        )
    }
}
