package com.locker.tictactoe.presentation.compose.screens.main.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.locker.tictactoe.presentation.model.AppColors
import com.locker.tictactoe.presentation.theme.AppColors1
import com.locker.tictactoe.presentation.theme.COLOR_PICKED_ANIM_DURATION
import com.locker.tictactoe.presentation.theme.MAX_INNER_COLOR_PERCENT
import com.locker.tictactoe.presentation.theme.MIN_INNER_COLOR_PERCENT
import com.locker.tictactoe.presentation.theme.Size32
import com.locker.tictactoe.presentation.theme.Space2
import kotlinx.coroutines.flow.StateFlow


@Composable
fun ColorsContainer(
    colorsList: List<AppColors>,
    activeIndex: StateFlow<Int>,
    onColorClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val activeColorIndex by activeIndex.collectAsState()
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
            colorsList.forEachIndexed { index, color ->
                ColorContainer(
                    colors = color,
                    onColorClick = { onColorClick(index) },
                    isActive = index == activeColorIndex,
                    modifier = Modifier
                        .size(Size32)
                        .padding(Space2)
                )
            }
        }
    }
}

@Composable
fun ColorContainer(
    colors: AppColors,
    isActive: Boolean,
    onColorClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var innerPercent by rememberSaveable { mutableFloatStateOf(MIN_INNER_COLOR_PERCENT) }
    val innerCirclePercent by animateFloatAsState(
        targetValue = innerPercent,
        label = "animate inner circle size",
        animationSpec = tween(durationMillis = COLOR_PICKED_ANIM_DURATION)
    )

    LaunchedEffect(isActive) {
        innerPercent = if (isActive) MAX_INNER_COLOR_PERCENT else MIN_INNER_COLOR_PERCENT
    }

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(if (isActive) colors.accent else colors.background)
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .fillMaxSize(innerCirclePercent)
                .clickable { onColorClick() }
                .background(if (isActive) colors.background else colors.accent)
                .align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun ColorContainerPreview() {
    Box(modifier = Modifier
        .fillMaxSize(0.5f)
        .aspectRatio(1f)) {
        ColorContainer(
            colors = AppColors1,
            isActive = false,
            onColorClick = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}
