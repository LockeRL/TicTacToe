package com.locker.feature.settingsscreen.screen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.locker.feature.component.TicTacToeButton
import com.locker.feature.core.theme.TicTacToeTheme

@Composable
fun ColorPickerDialog(
	title: String,
	onDismissRequest: () -> Unit,
	onColorSelected: (Color) -> Unit
) {
	val themeColors = TicTacToeTheme.colors
	val typography = TicTacToeTheme.typography
	val gridState = rememberLazyGridState()
	val colors = listOf(
		Color(0xFF000000), Color(0xFFFFFFFF), Color(0xFFFF0000),
		Color(0xFF00FF00), Color(0xFF0000FF), Color(0xFFFFFF00),
		Color(0xFF00FFFF), Color(0xFFFF00FF), Color(0xFFFFA500),
		Color(0xFF808080), Color(0xFF800000), Color(0xFF008000),
		Color(0xFF000080), Color(0xFF808000), Color(0xFF800080),
		Color(0xFF008080), Color(0xFFE91E63), Color(0xFF9C27B0),
		Color(0xFF3F51B5), Color(0xFF00BCD4), Color(0xFF4CAF50),
		Color(0xFFFFEB3B), Color(0xFFFF9800), Color(0xFF795548),
		Color(0xFF1A1A1A), Color(0xFF2E7D32), Color(0xFF1565C0),
		Color(0xFFC62828), Color(0xFFF9A825), Color(0xFF6A1B9A)
	)

	Dialog(onDismissRequest = onDismissRequest) {
		Surface(
			shape = RoundedCornerShape(28.dp),
			color = themeColors.background,
			tonalElevation = 6.dp
		) {
			Column(
				modifier = Modifier.padding(24.dp),
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Text(
					text = title,
					style = typography.titleLarge,
					color = themeColors.accent,
					modifier = Modifier.padding(bottom = 24.dp)
				)

				Box(modifier = Modifier.height(240.dp)) {
					LazyVerticalGrid(
						state = gridState,
						columns = GridCells.Fixed(5),
						horizontalArrangement = Arrangement.spacedBy(12.dp),
						verticalArrangement = Arrangement.spacedBy(12.dp),
						modifier = Modifier.fillMaxSize()
					) {
						items(colors) { color ->
							Box(
								modifier = Modifier
									.aspectRatio(1f)
									.clip(CircleShape)
									.background(color)
									.clickable { onColorSelected(color) }
							)
						}
					}

					if (gridState.canScrollBackward) {
						Box(
							modifier = Modifier
								.fillMaxWidth()
								.height(32.dp)
								.align(Alignment.TopCenter)
								.background(
									Brush.verticalGradient(
										colors = listOf(themeColors.background, Color.Transparent)
									)
								)
						)
					}

					if (gridState.canScrollForward) {
						Box(
							modifier = Modifier
								.fillMaxWidth()
								.height(32.dp)
								.align(Alignment.BottomCenter)
								.background(
									Brush.verticalGradient(
										colors = listOf(Color.Transparent, themeColors.background)
									)
								)
						)
					}
				}

				TicTacToeButton(
					text = "cancel",
					onClick = onDismissRequest,
					modifier = Modifier
						.align(Alignment.End)
						.padding(top = 16.dp)
				)
			}
		}
	}
}
