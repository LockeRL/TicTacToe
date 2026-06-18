package com.locker.feature.colorpicker.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.locker.feature.colorpicker.Res
import com.locker.feature.colorpicker.ic_palette
import com.locker.feature.component.TicTacToeIconButton
import com.locker.feature.core.theme.AppColorTheme
import com.locker.feature.core.theme.PALETTE_SLIDE_ANIM_DURATION
import com.locker.feature.core.theme.TicTacToeTheme
import com.locker.feature.core.theme.VERY_LOW_STIFFNESS
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun PalettePicker(
	colorsList: List<AppColorTheme>,
	onColorsPick: (Int) -> Unit,
	activeIndex: Int,
	modifier: Modifier = Modifier
) {
	val themeColors = TicTacToeTheme.colors
	val isExpanded = rememberSaveable { mutableStateOf(false) }
	val buttonSize = 48.dp

	val slideInPadding by animateDpAsState(
		targetValue = if (isExpanded.value) buttonSize + 2.dp else buttonSize / 2,
		animationSpec = tween(easing = LinearEasing)
	)

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
				visible = isExpanded.value,
				enter = slideInHorizontally(
					tween(
						PALETTE_SLIDE_ANIM_DURATION,
						easing = FastOutSlowInEasing
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
			icon = painterResource(Res.drawable.ic_palette),
			shape = CircleShape,
			onClick = { isExpanded.value = !isExpanded.value },
			contentDescription = "palette_icon",
			containerColor = if (isExpanded.value) themeColors.accent else themeColors.background,
			contentColor = if (isExpanded.value) themeColors.background else themeColors.additionalContainer,
			modifier = Modifier
				.size(buttonSize)
				.align(Alignment.CenterEnd),
			iconModifier = Modifier
				.size(32.dp)
		)
	}
}

@Composable
fun PalettePicker(
	modifier: Modifier = Modifier,
	colorsViewModel: ColorsViewModel = koinInject()
) {
	val colorsList = colorsViewModel.colorsList.collectAsState()
	val themeIndex = colorsViewModel.currentColorThemeIndex.collectAsState()
	PalettePicker(
		colorsList = colorsList.value,
		onColorsPick = colorsViewModel::setAppColorsIndex,
		activeIndex = themeIndex.value,
		modifier = modifier
	)
}

@Preview(showBackground = true)
@Composable
fun PalettePreview() {
	TicTacToeTheme {
		PalettePicker(
			colorsList = listOf(AppColorTheme.DEFAULT),
			onColorsPick = {},
			activeIndex = 0,
			modifier = Modifier
				.fillMaxWidth()
				.background(Color.Black)
		)
	}
}
