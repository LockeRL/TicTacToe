package com.locker.tictactoe.presentation.compose.screens.main.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import com.locker.tictactoe.R
import com.locker.tictactoe.presentation.theme.Typography

@Composable
fun MainMenuText(
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.tic_x_tac),
            color = color,
            style = Typography.h1,
            fontFamily = FontFamily.Cursive
        )

        Text(
            text = stringResource(id = R.string.toe),
            color = color,
            style = Typography.h1,
            fontFamily = FontFamily.Cursive
        )
    }
}
