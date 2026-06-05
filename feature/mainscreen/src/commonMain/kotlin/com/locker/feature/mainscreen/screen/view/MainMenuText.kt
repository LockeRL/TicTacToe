package com.locker.feature.mainscreen.screen.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import com.locker.feature.core.theme.TicTacToeTheme
import com.locker.feature.mainscreen.screen.model.MainScreenState

@Composable
fun MainMenuText(
    state: MainScreenState,
    modifier: Modifier = Modifier
) {
    val colors = TicTacToeTheme.colors
    val typography = TicTacToeTheme.typography

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = state.firstTitle,
            color = colors.accent,
            style = typography.displayLarge,
            fontFamily = FontFamily.Cursive
        )

        Text(
            text = state.secondTitle,
            color = colors.accent,
            style = typography.displayLarge,
            fontFamily = FontFamily.Cursive
        )
    }
}
