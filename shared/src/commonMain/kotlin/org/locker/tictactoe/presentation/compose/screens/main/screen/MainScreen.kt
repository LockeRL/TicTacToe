package org.locker.tictactoe.presentation.compose.screens.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.stringResource
import org.locker.tictactoe.presentation.common.TicTacToeButton
import org.locker.tictactoe.presentation.compose.screens.main.view.MainMenuText
import org.locker.tictactoe.presentation.theme.MENU_BUTTON_PERCENT
import org.locker.tictactoe.presentation.theme.Space16
import org.locker.tictactoe.presentation.theme.TicTacToeTheme
import tictactoe.shared.generated.resources.Res
import tictactoe.shared.generated.resources.play

@Composable
fun MainScreen(
    onStartGameClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            MainMenuText(
                modifier = Modifier.fillMaxWidth()
            )

            TicTacToeButton(
                text = stringResource(Res.string.play),
                onClick = onStartGameClick,
                modifier = Modifier.fillMaxWidth(MENU_BUTTON_PERCENT)
            )
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    TicTacToeTheme {
        MainScreen(
            onStartGameClick = {},
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(Space16)
        )
    }
}
