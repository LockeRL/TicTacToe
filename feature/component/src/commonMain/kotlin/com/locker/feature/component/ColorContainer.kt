package com.locker.feature.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.locker.feature.core.theme.AppColorTheme
import com.locker.feature.core.theme.COLOR_PICKED_ANIM_DURATION
import com.locker.feature.core.theme.MAX_INNER_COLOR_PERCENT
import com.locker.feature.core.theme.MIN_INNER_COLOR_PERCENT
import com.locker.feature.core.theme.TicTacToeTheme

@Composable
fun ColorsRow(
	colorsList: List<AppColorTheme>,
	onColorClick: (Int) -> Unit,
	activeIndex: Int,
	modifier: Modifier = Modifier,
	scrollState: ScrollState = rememberScrollState(),
) {
	ColorsRow(
		colorsList = colorsList,
		onColorClick = { _, index ->
			onColorClick(index)
		},
		isActive = { _, index ->
			index == activeIndex
		},
		modifier = modifier,
		scrollState = scrollState,
	)
}

@Composable
fun ColorsRow(
	colorsList: List<AppColorTheme>,
	onColorClick: (Int) -> Unit,
	modifier: Modifier = Modifier,
	scrollState: ScrollState = rememberScrollState(),
) {
	val colors = TicTacToeTheme.colors
	ColorsRow(
		colorsList = colorsList,
		onColorClick = { id, _ ->
			onColorClick(id)
		},
		isActive = { id, _ ->
			id == colors.id
		},
		modifier = modifier,
		scrollState = scrollState,
	)
}

@Composable
private fun ColorsRow(
	colorsList: List<AppColorTheme>,
	onColorClick: (Int, Int) -> Unit,
	isActive: (Int, Int) -> Boolean,
	modifier: Modifier = Modifier,
	scrollState: ScrollState = rememberScrollState(),
) {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.End,
		modifier = modifier.horizontalScroll(scrollState)
	) {
		colorsList.forEachIndexed { index, color ->
			ColorContainer(
				colors = color,
				onColorClick = { onColorClick(color.id, index) },
				isActive = isActive(color.id, index),
				modifier = Modifier
					.size(32.dp)
					.padding(2.dp),
			)
		}
	}
}

@Composable
fun ColorContainer(
	colors: AppColorTheme,
	isActive: Boolean,
	onColorClick: () -> Unit,
	modifier: Modifier = Modifier
) {
	val innerCirclePercent by animateFloatAsState(
		targetValue = if (isActive) MAX_INNER_COLOR_PERCENT else MIN_INNER_COLOR_PERCENT,
		label = "animate inner circle size",
		animationSpec = tween(durationMillis = COLOR_PICKED_ANIM_DURATION)
	)

	Box(
		modifier = modifier
			.clip(CircleShape)
			.background(if (isActive) colors.accent else colors.background),
	) {
		Box(
			modifier = Modifier
				.clip(CircleShape)
				.fillMaxSize(innerCirclePercent)
				.clickable { onColorClick() }
				.background(if (isActive) colors.background else colors.accent)
				.align(Alignment.Center),
		)
	}
}

@Preview
@Composable
fun ColorContainerPreview() {
	Box(
		modifier = Modifier
			.size(100.dp)
			.aspectRatio(1f),
	) {
		ColorContainer(
			colors = AppColorTheme.DEFAULT,
			isActive = false,
			onColorClick = {},
			modifier = Modifier
				.fillMaxSize(),
		)
	}
}
