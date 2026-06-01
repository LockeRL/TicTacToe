package org.locker.tictactoe.presentation.compose.screens.game.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.stringResource
import org.locker.tictactoe.presentation.common.TicTacToeButton
import org.locker.tictactoe.presentation.compose.screens.game.view.PlayerIcon
import org.locker.tictactoe.presentation.model.Player
import org.locker.tictactoe.presentation.theme.MENU_BUTTON_PERCENT
import org.locker.tictactoe.presentation.theme.Size64
import org.locker.tictactoe.presentation.theme.Space8
import org.locker.tictactoe.presentation.theme.Typography
import tictactoe.shared.generated.resources.Res
import tictactoe.shared.generated.resources.draw
import tictactoe.shared.generated.resources.next_game
import tictactoe.shared.generated.resources.wins

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
        if (winner != null) {
            PlayerIcon(
                player = winner,
                modifier = Modifier.size(Size64)
            )
        }

        Text(
            text = "${stringResource(if (winner == null) Res.string.draw else Res.string.wins)}!",
            color = MaterialTheme.colorScheme.primaryContainer,
            style = Typography.titleLarge,
            modifier = Modifier.padding(Space8)
        )

        TicTacToeButton(
            text = stringResource(Res.string.next_game),
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
