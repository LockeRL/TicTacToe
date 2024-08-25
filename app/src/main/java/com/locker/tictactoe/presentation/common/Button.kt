package com.locker.tictactoe.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.locker.tictactoe.presentation.theme.AccentColor
import com.locker.tictactoe.presentation.theme.RoundCornerPercent
import com.locker.tictactoe.presentation.theme.TicTacToeTheme
import com.locker.tictactoe.presentation.theme.Typography

@Composable
fun TicTacToeButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    borderStroke: BorderStroke? = null,
    containerColor: Color = AccentColor,
    contentColor: Color = Color.Black,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(RoundCornerPercent),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = containerColor,
            contentColor = contentColor
        ),
        contentPadding = contentPadding,
        border = borderStroke,
        enabled = enabled,
        modifier = modifier.height(40.dp)
    ) {
        Text(
            text = text.uppercase(),
            style = Typography.button
        )
    }
}

@Preview
@Composable
fun ButtonPreview() {
    TicTacToeTheme {
        TicTacToeButton(
            text = "Play",
            onClick = {},
            modifier = Modifier.fillMaxWidth(0.5f)
        )
    }
}
