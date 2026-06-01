package org.locker.tictactoe.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.locker.tictactoe.presentation.theme.DefaultShape

@Composable
fun TicTacToeIconButton(
    icon: DrawableResource,
    onClick: () -> Unit,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentColor: Color = Color.White,
    containerColor: Color = MaterialTheme.colorScheme.background,
    borderStroke: BorderStroke? = null,
    shape: Shape = DefaultShape,
    iconModifier: Modifier = Modifier.fillMaxSize(),
) {
    Button(
        onClick = onClick,
        shape = shape,
        border = borderStroke,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        contentPadding = PaddingValues(),
        elevation = null,
        modifier = modifier.clip(shape)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = contentDescription,
            modifier = iconModifier.clip(shape)
        )
    }
}
