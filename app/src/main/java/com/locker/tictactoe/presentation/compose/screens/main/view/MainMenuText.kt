package com.locker.tictactoe.presentation.compose.screens.main.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import com.locker.tictactoe.R
import com.locker.tictactoe.presentation.theme.Typography

@Composable
fun MainMenuText(
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colors

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.tic_x_tac),
            color = colors.primary,
            style = Typography.h1,
            fontFamily = FontFamily.Cursive
        )

        Text(
            text = stringResource(id = R.string.toe),
            color = colors.primary,
            style = Typography.h1,
            fontFamily = FontFamily.Cursive
        )
    }
}
