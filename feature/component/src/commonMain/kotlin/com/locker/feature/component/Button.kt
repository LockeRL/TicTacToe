package com.locker.feature.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.locker.feature.core.theme.TicTacToeTheme

@Composable
fun TicTacToeButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    borderStroke: BorderStroke? = null,
    containerColor: Color = TicTacToeTheme.colors.accent,
    contentColor: Color = TicTacToeTheme.colors.background,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
) {
    val typography = TicTacToeTheme.typography
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        contentPadding = contentPadding,
        border = borderStroke,
        enabled = enabled,
        modifier = modifier
            .height(40.dp),
    ) {
        Text(
            text = text.uppercase(),
            style = typography.bodyMedium,
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
        )
    }
}
