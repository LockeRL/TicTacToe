package com.locker.tictactoe.presentation.compose.screens.game.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.locker.tictactoe.R
import com.locker.tictactoe.presentation.model.Player
import com.locker.tictactoe.presentation.theme.Size48
import com.locker.tictactoe.presentation.theme.Typography
import kotlinx.coroutines.flow.StateFlow

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
            text = "${stringResource(id = R.string.turn)}:",
            color = MaterialTheme.colors.primaryVariant,
            style = Typography.h4
        )
        PlayerIconWithState(
            playerState = player,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
        )
    }
}
