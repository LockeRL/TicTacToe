package com.locker.tictactoe.presentation.compose.screens.main.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.locker.tictactoe.R
import com.locker.tictactoe.presentation.model.AppColors
import com.locker.tictactoe.presentation.theme.PALETTE_SLIDE_ANIM_DURATION
import com.locker.tictactoe.presentation.theme.Size36
import com.locker.tictactoe.presentation.theme.Size48
import com.locker.tictactoe.presentation.theme.TicTacToeTheme
import com.locker.tictactoe.presentation.theme.VERY_LOW_STIFFNESS
import com.locker.tictactoe.presentation.theme.White60

@Composable
fun PalettePicker(
    colorsList: List<AppColors>,
    modifier: Modifier = Modifier
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = modifier
    ) {
        AnimatedVisibility(
            visible = isExpanded,
            enter = slideInHorizontally(tween(PALETTE_SLIDE_ANIM_DURATION)) { it },
            exit = slideOutHorizontally(spring(stiffness = VERY_LOW_STIFFNESS)) { it } +
                    shrinkHorizontally(
                        animationSpec = spring(stiffness = VERY_LOW_STIFFNESS),
                        shrinkTowards = Alignment.Start
                    ),
            modifier = Modifier.weight(1f, false)
        ) {
            ColorsContainer(
                colorsList = colorsList
            )
        }

        Button(
            onClick = {
                isExpanded = !isExpanded
                println(isExpanded)
            },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                contentColor = Color.White
            ),
            contentPadding = PaddingValues(),
            modifier = Modifier.size(Size48)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_palette),
                contentDescription = null,
                tint = White60,
                modifier = Modifier.size(Size36)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PalettePreview() {
    TicTacToeTheme {
        PalettePicker(
            colorsList = listOf(
                AppColors(),
                AppColors(),
                AppColors(),
                AppColors(),
                AppColors(),
                AppColors(),
                AppColors(),
                AppColors(),
                AppColors(),
                AppColors(),
                AppColors(),
                AppColors(),
                AppColors(),
                AppColors(),
                AppColors(),
                AppColors()
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
        )
    }
}
