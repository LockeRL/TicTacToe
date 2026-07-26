package com.locker.feature.settingsscreen.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.locker.feature.component.TicTacToeButton
import com.locker.feature.core.screen.LocalFireEvent
import com.locker.feature.core.screen.ProvideScreenEvents
import com.locker.feature.core.theme.AppColorTheme
import com.locker.feature.core.theme.TicTacToeTheme
import com.locker.feature.core.theme.animate
import com.locker.feature.settingsscreen.screen.event.ColorType
import com.locker.feature.settingsscreen.screen.event.SaveThemeEvent
import com.locker.feature.settingsscreen.screen.event.UpdateColorsEvent
import com.locker.feature.settingsscreen.screen.model.SettingsScreenState
import com.locker.feature.settingsscreen.screen.view.ColorPickerDialog
import com.locker.feature.settingsscreen.screen.view.ColorSettingItem
import com.locker.feature.settingsscreen.screen.view.ThemePreview
import com.locker.feature.settingsscreen.screen.view.UserThemes
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen(
	viewModel: SettingsViewModel = koinViewModel(),
	modifier: Modifier = Modifier,
) {
	val colors = TicTacToeTheme.colors
	ProvideScreenEvents(
		viewModel = viewModel
	) { viewModel ->
		val strings = viewModel.settingsStrings.collectAsState()
		val theme = viewModel.currentEditTheme.collectAsState(initial = colors)
		val userThemes = viewModel.userThemes.collectAsState()

		SettingsScreen(
			strings = strings,
			theme = theme,
			userThemes = userThemes,
			modifier = modifier,
		)
	}
}

@Composable
fun SettingsScreen(
	strings: State<SettingsScreenState>,
	theme: State<AppColorTheme>,
	userThemes: State<List<AppColorTheme>>,
	modifier: Modifier = Modifier,
) {
	val fireEvent = LocalFireEvent.current
	val colors = TicTacToeTheme.colors
	val currentTheme = theme.value.animate()
	val strings = strings.value
	val listState = rememberLazyListState()
	val editableColor = remember { mutableStateOf<ColorType?>(null) }

	Box(
		contentAlignment = Alignment.Center,
		modifier = modifier,
	) {
		Column(
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(32.dp),
			modifier = Modifier
				.padding(16.dp),
		) {
			ThemePreview(theme = currentTheme)

			AnimatedVisibility(
				visible = userThemes.value.isNotEmpty()
			) {
				UserThemes(
					title = strings.userThemes,
					userThemes = userThemes.value,
					modifier = Modifier
						.fillMaxWidth()
				)
			}

			Box(
				modifier = Modifier
					.weight(weight = 1f, fill = false)
			) {
				if (listState.canScrollBackward) {
					Box(
						modifier = Modifier
							.fillMaxWidth()
							.height(16.dp)
							.zIndex(2f)
							.background(
								brush = Brush.verticalGradient(
									0f to colors.background,
									1f to Color.Transparent
								)
							)
							.align(Alignment.TopCenter)
					)
				}

				if (listState.canScrollForward) {
					Box(
						modifier = Modifier
							.fillMaxWidth()
							.height(16.dp)
							.zIndex(2f)
							.background(
								brush = Brush.verticalGradient(
									0f to Color.Transparent,
									1f to colors.background
								)
							)
							.align(Alignment.BottomCenter)
					)
				}

				LazyColumn(
					verticalArrangement = Arrangement.spacedBy(16.dp),
					horizontalAlignment = Alignment.CenterHorizontally,
					state = listState,
					userScrollEnabled = listState.canScrollBackward || listState.canScrollForward,
					modifier = Modifier
						.zIndex(1f)
				) {
					item {
						ColorSettingItem(
							label = strings.background,
							color = currentTheme.background,
							modifier = Modifier.fillMaxWidth(),
						) {
							editableColor.value = ColorType.BACKGROUND
						}
					}

					item {
						ColorSettingItem(
							label = strings.accent,
							color = currentTheme.accent,
							modifier = Modifier.fillMaxWidth()
						) {
							editableColor.value = ColorType.ACCENT
						}
					}

					item {
						ColorSettingItem(
							label = strings.additional,
							color = currentTheme.additional,
							modifier = Modifier.fillMaxWidth(),
						) {
							editableColor.value = ColorType.ADDITIONAL
						}
					}

					item {
						ColorSettingItem(
							label = strings.additionalContainer,
							color = currentTheme.additionalContainer,
							modifier = Modifier.fillMaxWidth(),
						) {
							editableColor.value = ColorType.ADDITIONAL_CONTAINER
						}
					}

					item {
						ColorSettingItem(
							label = strings.accentContainer,
							color = currentTheme.accentContainer,
							modifier = Modifier.fillMaxWidth(),
						) {
							editableColor.value = ColorType.ACCENT_CONTAINER
						}
					}
				}
			}

			TicTacToeButton(
				text = strings.saveButton,
				onClick = { fireEvent(SaveThemeEvent(currentTheme)) },
				modifier = Modifier
					.fillMaxWidth()
			)
		}

		editableColor.value?.let { type ->
			ColorPickerDialog(
				title = stringResource(type.titleRes),
				initialColor = currentTheme.getColorByType(type),
				onDismissRequest = { editableColor.value = null },
				strings = strings,
				onColorSelected = {
					fireEvent(UpdateColorsEvent(currentTheme.update(colorType = type, color = it)))
					editableColor.value = null
				}
			)
		}
	}
}

private fun AppColorTheme.getColorByType(colorType: ColorType): Color =
	when (colorType) {
		ColorType.ACCENT -> accent
		ColorType.BACKGROUND -> background
		ColorType.ADDITIONAL -> additional
		ColorType.ADDITIONAL_CONTAINER -> additionalContainer
		ColorType.ACCENT_CONTAINER -> accentContainer
	}

private fun AppColorTheme.update(colorType: ColorType, color: Color): AppColorTheme =
	when (colorType) {
		ColorType.ACCENT -> copy(accent = color)
		ColorType.BACKGROUND -> copy(background = color)
		ColorType.ADDITIONAL -> copy(additional = color)
		ColorType.ADDITIONAL_CONTAINER -> copy(additionalContainer = color)
		ColorType.ACCENT_CONTAINER -> copy(accentContainer = color)
	}
