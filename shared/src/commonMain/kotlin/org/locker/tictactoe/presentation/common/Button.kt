package org.locker.tictactoe.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.locker.tictactoe.presentation.theme.RoundCornerPercent
import org.locker.tictactoe.presentation.theme.TicTacToeTheme
import org.locker.tictactoe.presentation.theme.Typography

@Composable
fun TicTacToeButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    borderStroke: BorderStroke? = null,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.background,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(RoundCornerPercent),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        contentPadding = contentPadding,
        border = borderStroke,
        enabled = enabled,
        modifier = modifier.height(40.dp)
    ) {
        Text(
            text = text.uppercase(),
            style = Typography.bodyMedium
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
