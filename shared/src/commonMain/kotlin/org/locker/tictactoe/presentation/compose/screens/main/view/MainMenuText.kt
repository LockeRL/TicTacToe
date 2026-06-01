package org.locker.tictactoe.presentation.compose.screens.main.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import org.jetbrains.compose.resources.stringResource
import org.locker.tictactoe.presentation.theme.Typography
import tictactoe.shared.generated.resources.Res
import tictactoe.shared.generated.resources.tic_x_tac
import tictactoe.shared.generated.resources.toe

@Composable
fun MainMenuText(
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = stringResource(Res.string.tic_x_tac),
            color = colors.primary,
            style = Typography.displayLarge,
            fontFamily = FontFamily.Cursive
        )

        Text(
            text = stringResource(Res.string.toe),
            color = colors.primary,
            style = Typography.displayLarge,
            fontFamily = FontFamily.Cursive
        )
    }
}
