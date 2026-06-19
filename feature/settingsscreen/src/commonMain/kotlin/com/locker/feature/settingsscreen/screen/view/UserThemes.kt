package com.locker.feature.settingsscreen.screen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.locker.feature.component.ColorsRow
import com.locker.feature.component.TicTacToeIconButton
import com.locker.feature.core.screen.LocalFireEvent
import com.locker.feature.core.theme.AppColorTheme
import com.locker.feature.core.theme.TicTacToeTheme
import com.locker.feature.settingsscreen.screen.event.DeleteThemeEvent
import com.locker.feature.settingsscreen.screen.event.UpdateColorsEvent
import com.locker.resources.Res
import com.locker.resources.ic_delete
import org.jetbrains.compose.resources.painterResource

@Composable
fun UserThemes(
	title: String,
	userThemes: List<AppColorTheme>,
	modifier: Modifier = Modifier
) {
	val fireEvent = LocalFireEvent.current
	val colors = TicTacToeTheme.colors
	val typography = TicTacToeTheme.typography

	val selectedIndex = remember { mutableIntStateOf(-1) }

	Row(
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween,
		modifier = modifier
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.spacedBy(16.dp),
			modifier = Modifier
				.weight(weight = 1f, fill = false),
		) {
			Text(
				text = title,
				color = colors.accent,
				style = typography.titleMedium,
			)

			ColorsRowWithGradient(
				userThemes = userThemes,
				selectedIndex = selectedIndex,
				modifier = Modifier
					.weight(weight = 1f, fill = false),
			)
		}

		TicTacToeIconButton(
			icon = painterResource(Res.drawable.ic_delete),
			shape = CircleShape,
			onClick = {
				fireEvent(DeleteThemeEvent(selectedIndex.intValue))
				selectedIndex.intValue = -1
			},
			contentDescription = "DeleteTheme",
			contentColor = colors.additionalContainer,
			iconModifier = Modifier.size(24.dp),
			modifier = Modifier.size(40.dp)
		)
	}
}

@Composable
private fun ColorsRowWithGradient(
	userThemes: List<AppColorTheme>,
	selectedIndex: MutableIntState,
	modifier: Modifier = Modifier
) {
	val fireEvent = LocalFireEvent.current
	val scrollState = rememberScrollState()
	val backgroundColor = TicTacToeTheme.colors.background

	Box(
		modifier = modifier
			.height(IntrinsicSize.Min)
	) {
		ColorsRow(
			colorsList = userThemes,
			scrollState = scrollState,
			onColorClick = {
				selectedIndex.intValue = it
				fireEvent(UpdateColorsEvent(userThemes[it]))
			},
			activeIndex = selectedIndex.intValue,
		)

		if (scrollState.canScrollBackward) {
			Box(
				modifier = Modifier
					.align(Alignment.CenterStart)
					.width(16.dp)
					.fillMaxHeight()
					.background(
						brush = Brush.horizontalGradient(
							colors = listOf(backgroundColor, Color.Transparent)
						)
					)
			)
		}

		if (scrollState.canScrollForward) {
			Box(
				modifier = Modifier
					.align(Alignment.CenterEnd)
					.width(16.dp)
					.fillMaxHeight()
					.background(
						brush = Brush.horizontalGradient(
							colors = listOf(Color.Transparent, backgroundColor)
						)
					)
			)
		}
	}
}
