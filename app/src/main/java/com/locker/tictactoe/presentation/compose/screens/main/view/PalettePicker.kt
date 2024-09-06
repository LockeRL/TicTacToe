package com.locker.tictactoe.presentation.compose.screens.main.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.locker.tictactoe.R
import com.locker.tictactoe.presentation.ColorsViewModel
import com.locker.tictactoe.presentation.common.TicTacToeIconButton
import com.locker.tictactoe.presentation.model.AppColors
import com.locker.tictactoe.presentation.theme.AppColors1
import com.locker.tictactoe.presentation.theme.AppColors2
import com.locker.tictactoe.presentation.theme.PALETTE_SLIDE_ANIM_DURATION
import com.locker.tictactoe.presentation.theme.Size32
import com.locker.tictactoe.presentation.theme.Size48
import com.locker.tictactoe.presentation.theme.Space2
import com.locker.tictactoe.presentation.theme.TicTacToeTheme
import com.locker.tictactoe.presentation.theme.VERY_LOW_STIFFNESS
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.compose.koinInject

@Composable
fun PalettePicker(
    colorsList: List<AppColors>,
    onColorsPick: (Int) -> Unit,
    activeIndex: StateFlow<Int>,
    modifier: Modifier = Modifier
) {
    val themeColors = MaterialTheme.colors
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    var slideInPaddingValue by remember { mutableStateOf(Size48 / 2) }
    val slideInPadding by animateDpAsState(
        targetValue = slideInPaddingValue,
        label = stringResource(id = R.string.colors_slider_animate_padding),
        animationSpec = tween(easing = LinearEasing)
    )

    LaunchedEffect(key1 = isExpanded) {
        slideInPaddingValue = if (isExpanded) Size48 + Space2 else Size48 / 2
    }

    Box(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterEnd)
                .padding(end = slideInPadding)
        ) {
            AnimatedVisibility(
                visible = isExpanded,
                enter = slideInHorizontally(
                    tween(
                        PALETTE_SLIDE_ANIM_DURATION,
                        easing = LinearEasing
                    )
                ) { it },
                exit = slideOutHorizontally(spring(stiffness = VERY_LOW_STIFFNESS)) { it } +
                        shrinkHorizontally(
                            animationSpec = spring(stiffness = VERY_LOW_STIFFNESS),
                            shrinkTowards = Alignment.Start
                        ),
                modifier = Modifier.weight(1f, false)
            ) {
                ColorsContainer(
                    colorsList = colorsList,
                    onColorClick = onColorsPick,
                    activeIndex = activeIndex
                )
            }
        }

        TicTacToeIconButton(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_palette),
            shape = CircleShape,
            onClick = { isExpanded = !isExpanded },
            contentDescription = stringResource(id = R.string.ic_palette),
            containerColor = if (isExpanded) themeColors.primary else themeColors.background,
            contentColor = if (isExpanded) themeColors.background else themeColors.secondaryVariant,
            modifier = Modifier
                .size(Size48)
                .align(Alignment.CenterEnd),
            iconModifier = Modifier.size(Size32)
        )
    }
}

@Composable
fun PalettePicker(
    modifier: Modifier = Modifier,
    colorsViewModel: ColorsViewModel = koinInject()
) {
    PalettePicker(
        colorsList = colorsViewModel.colorsList,
        onColorsPick = colorsViewModel::setAppColorsIndex,
        activeIndex = colorsViewModel.activeColorIndex,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun PalettePreview() {
    TicTacToeTheme {
        PalettePicker(
            colorsList = listOf(
                AppColors1,
                AppColors2
            ),
            onColorsPick = {},
            activeIndex = MutableStateFlow(0),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
        )
    }
}
