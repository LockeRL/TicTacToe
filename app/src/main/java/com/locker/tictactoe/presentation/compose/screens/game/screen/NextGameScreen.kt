package com.locker.tictactoe.presentation.compose.screens.game.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.locker.tictactoe.R
import com.locker.tictactoe.presentation.common.TicTacToeButton
import com.locker.tictactoe.presentation.compose.screens.game.view.PlayerIconNullable
import com.locker.tictactoe.presentation.model.Player
import com.locker.tictactoe.presentation.theme.MENU_BUTTON_PERCENT
import com.locker.tictactoe.presentation.theme.Size64
import com.locker.tictactoe.presentation.theme.Space8
import com.locker.tictactoe.presentation.theme.Typography

@Composable
fun NextGameScreen(
    winner: Player?,
    onNextGameClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        PlayerIconNullable(
            player = winner,
            modifier = Modifier.size(Size64)
        )

        Text(
            text = "${stringResource(id = if (winner == null) R.string.draw else R.string.wins)}!",
            color = MaterialTheme.colors.primaryVariant,
            style = Typography.h3,
            modifier = Modifier.padding(Space8)
        )

        TicTacToeButton(
            text = stringResource(id = R.string.next_game),
            onClick = onNextGameClick,
            modifier = Modifier.fillMaxWidth(MENU_BUTTON_PERCENT)
        )
    }
}

@Preview
@Composable
fun NextGameScreenPreview() {
    NextGameScreen(
        winner = null,
        onNextGameClick = {},
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    )
}
