package com.locker.tictactoe.presentation.compose.screens.main.view

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.locker.tictactoe.presentation.model.AppColors
import com.locker.tictactoe.presentation.theme.Size32
import com.locker.tictactoe.presentation.theme.Space2

@Composable
fun ColorContainer(
    colors: AppColors,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color(colors.backgroundColor))
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .fillMaxSize(0.6f)
                .background(Color(colors.uiColor))
                .align(Alignment.Center)
        )
    }
}

@Composable
fun ColorsContainer(
    colorsList: List<AppColors>,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(Color.Black)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.horizontalScroll(scrollState)
        ) {
            colorsList.forEach { color ->
                ColorContainer(
                    colors = color,
                    modifier = Modifier
                        .size(Size32)
                        .padding(Space2)
                )
            }
        }
    }
}
