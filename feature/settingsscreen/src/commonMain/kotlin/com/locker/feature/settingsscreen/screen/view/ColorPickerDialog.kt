package com.locker.feature.settingsscreen.screen.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.locker.feature.component.TicTacToeButton
import com.locker.feature.core.theme.TicTacToeTheme
import com.locker.feature.settingsscreen.screen.model.SettingsScreenState
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@Composable
fun ColorPickerDialog(
	title: String,
	strings: SettingsScreenState,
	onDismissRequest: () -> Unit,
	onColorSelected: (Color) -> Unit
) {
	val themeColors = TicTacToeTheme.colors
	val typography = TicTacToeTheme.typography
	
	val initialHsv = remember { colorToHsv(themeColors.accent) }
	var currentHue by remember { mutableFloatStateOf(initialHsv.first) }
	var currentSaturation by remember { mutableFloatStateOf(initialHsv.second) }
	var currentBrightness by remember { mutableFloatStateOf(initialHsv.third) }

	val selectedColor = hsvToColor(currentHue, currentSaturation, currentBrightness)

	Dialog(onDismissRequest = onDismissRequest) {
		Surface(
			shape = RoundedCornerShape(28.dp),
			color = themeColors.background,
			tonalElevation = 6.dp
		) {
			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				modifier = Modifier.padding(24.dp),
			) {
				Text(
					text = title,
					style = typography.titleLarge,
					color = themeColors.accent,
					modifier = Modifier.padding(bottom = 24.dp)
				)

				Row(
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.SpaceEvenly,
					modifier = Modifier
						.fillMaxWidth()
						.height(200.dp),
				) {
					ColorWheel(
						hue = currentHue,
						saturation = currentSaturation,
						brightness = currentBrightness,
						onColorChanged = { h, s ->
							currentHue = h
							currentSaturation = s
						},
						modifier = Modifier.size(180.dp),
					)

					Box(
						modifier = Modifier
							.size(60.dp)
							.clip(CircleShape)
							.background(selectedColor)
					)
				}

				Column(
					modifier = Modifier
						.fillMaxWidth()
						.padding(top = 16.dp)
				) {
					Text(
						text = strings.brightnessColor,
						style = typography.bodyMedium,
						color = themeColors.accent
					)

					Slider(
						value = currentBrightness,
						onValueChange = { currentBrightness = it },
						colors = SliderDefaults.colors(
							thumbColor = themeColors.accent,
							activeTrackColor = themeColors.accent,
							inactiveTrackColor = themeColors.additionalContainer
						),
						modifier = Modifier.fillMaxWidth()
					)
				}

				Row(
					horizontalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.End),
					modifier = Modifier
						.fillMaxWidth()
						.padding(top = 24.dp),
				) {
					TicTacToeButton(
						text = strings.cancelColor,
						onClick = onDismissRequest,
						containerColor = Color.Transparent,
						contentColor = themeColors.accent
					)

					TicTacToeButton(
						text = strings.selectColor,
						onClick = {
							onColorSelected(selectedColor)
						}
					)
				}
			}
		}
	}
}

@Composable
fun ColorWheel(
	modifier: Modifier = Modifier,
	hue: Float,
	saturation: Float,
	brightness: Float,
	onColorChanged: (Float, Float) -> Unit
) {
	var center by remember { mutableStateOf(Offset.Zero) }
	var radius by remember { mutableStateOf(0f) }

	Canvas(
		modifier = modifier
			.pointerInput(Unit) {
				detectTapGestures { offset ->
					val (h, s) = getHsvAtOffset(offset, center, radius)
					onColorChanged(h, s)
				}
			}
			.pointerInput(Unit) {
				detectDragGestures { change, _ ->
					change.consume()
					val (h, s) = getHsvAtOffset(change.position, center, radius)
					onColorChanged(h, s)
				}
			}
	) {
		center = size.center
		radius = size.minDimension / 2

		rotate(-90f, center) {
			drawCircle(
				brush = Brush.sweepGradient(
					colors = listOf(
						Color.Red, Color.Yellow, Color.Green, Color.Cyan, Color.Blue, Color.Magenta, Color.Red
					),
					center = center
				),
				radius = radius
			)

			drawCircle(
				brush = Brush.radialGradient(
					colors = listOf(Color.White, Color.Transparent),
					center = center,
					radius = radius
				),
				radius = radius
			)
		}
		
		drawCircle(
			color = Color.Black.copy(alpha = 1f - brightness),
			radius = radius,
			center = center
		)

		val angleRad = (hue - 90f) * PI.toFloat() / 180f
		val dist = saturation * radius
		val selectorOffset = Offset(
			x = center.x + dist * cos(angleRad),
			y = center.y + dist * sin(angleRad)
		)

		drawCircle(
			color = Color.White,
			radius = 8.dp.toPx(),
			center = selectorOffset,
			style = Stroke(width = 2.dp.toPx())
		)
		drawCircle(
			color = Color.Black,
			radius = 6.dp.toPx(),
			center = selectorOffset,
			style = Stroke(width = 1.dp.toPx())
		)
	}
}

private fun getHsvAtOffset(offset: Offset, center: Offset, radius: Float): Pair<Float, Float> {
	val dx = offset.x - center.x
	val dy = offset.y - center.y
	val distance = sqrt(dx * dx + dy * dy)

	var angle = atan2(dy, dx) * 180f / PI.toFloat()
	if (angle < 0) angle += 360f

	val hue = (angle + 90f) % 360f
	val saturation = (distance / radius).coerceIn(0f, 1f)
	return Pair(hue, saturation)
}

private fun hsvToColor(hue: Float, saturation: Float, value: Float): Color {
	val c = value * saturation
	val x = c * (1f - kotlin.math.abs((hue / 60f) % 2f - 1f))
	val m = value - c
	val (r, g, b) = when {
		hue < 60 -> Triple(c, x, 0f)
		hue < 120 -> Triple(x, c, 0f)
		hue < 180 -> Triple(0f, c, x)
		hue < 240 -> Triple(0f, x, c)
		hue < 300 -> Triple(x, 0f, c)
		else -> Triple(c, 0f, x)
	}
	return Color(r + m, g + m, b + m)
}

private fun colorToHsv(color: Color): Triple<Float, Float, Float> {
	val r = color.red
	val g = color.green
	val b = color.blue
	val max = maxOf(r, maxOf(g, b))
	val min = minOf(r, minOf(g, b))
	val d = max - min
	val h = when (max) {
		min -> 0f
		r -> (60 * ((g - b) / d) + 360) % 360
		g -> (60 * ((b - r) / d) + 120) % 360
		else -> (60 * ((r - g) / d) + 240) % 360
	}
	val s = if (max == 0f) 0f else d / max
	val v = max
	return Triple(h, s, v)
}
