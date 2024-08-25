package com.locker.tictactoe.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import com.locker.tictactoe.presentation.theme.DefaultShape
import com.locker.tictactoe.presentation.theme.Space0

@Composable
fun TicTacToeIconButton(
    imageVector: ImageVector,
    onClick: () -> Unit,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentColor: Color = Color.White,
    containerColor: Color = Color.Transparent,
    borderStroke: BorderStroke? = null,
    shape: Shape = DefaultShape,
    iconModifier: Modifier = Modifier.fillMaxSize(),
) {
    Button(
        onClick = onClick,
        shape = shape,
        border = borderStroke,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = containerColor,
            contentColor = contentColor
        ),
        contentPadding = PaddingValues(Space0),
        modifier = modifier
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = iconModifier
        )
    }
}
