package com.locker.feature.settingsscreen.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.locker.feature.component.TicTacToeButton
import com.locker.feature.core.screen.LocalFireEvent
import com.locker.feature.core.screen.ProvideScreenEvents
import com.locker.feature.core.theme.AppColorTheme
import com.locker.feature.core.theme.TicTacToeTheme
import com.locker.feature.settingsscreen.screen.event.ColorType
import com.locker.feature.settingsscreen.screen.event.SaveThemeEvent
import com.locker.feature.settingsscreen.screen.event.UpdateColorsEvent
import com.locker.feature.settingsscreen.screen.model.SettingsScreenState
import com.locker.feature.settingsscreen.screen.view.ColorPickerDialog
import com.locker.feature.settingsscreen.screen.view.ColorSettingItem
import com.locker.feature.settingsscreen.screen.view.ThemePreview
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
		SettingsScreen(
			strings = strings,
			theme = theme,
			modifier = modifier,
		)
	}
}

@Composable
fun SettingsScreen(
	strings: State<SettingsScreenState>,
	theme: State<AppColorTheme>,
	modifier: Modifier = Modifier,
) {
	val fireEvent = LocalFireEvent.current
	val currentTheme = theme.value
	val strings = strings.value
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
			ThemePreview(theme = theme.value)

			Column(
				verticalArrangement = Arrangement.spacedBy(16.dp),
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				ColorSettingItem(
					label = strings.background,
					color = currentTheme.background,
					modifier = Modifier.fillMaxWidth(),
				) {
					editableColor.value = ColorType.BACKGROUND
				}

				ColorSettingItem(
					label = strings.accent,
					color = currentTheme.accent,
					modifier = Modifier.fillMaxWidth()
				) {
					editableColor.value = ColorType.ACCENT
				}

				ColorSettingItem(
					label = strings.additional,
					color = currentTheme.additional,
					modifier = Modifier.fillMaxWidth(),
				) {
					editableColor.value = ColorType.ADDITIONAL
				}

				ColorSettingItem(
					label = strings.additionalContainer,
					color = currentTheme.additionalContainer,
					modifier = Modifier.fillMaxWidth(),
				) {
					editableColor.value = ColorType.ADDITIONAL_CONTAINER
				}

				ColorSettingItem(
					label = strings.accentContainer,
					color = currentTheme.accentContainer,
					modifier = Modifier.fillMaxWidth(),
				) {
					editableColor.value = ColorType.ACCENT_CONTAINER
				}
			}

			TicTacToeButton(
				text = strings.saveButton,
				onClick = { fireEvent(SaveThemeEvent) },
				modifier = Modifier
					.fillMaxWidth()
			)
		}

		editableColor.value?.let { type ->
			ColorPickerDialog(
				title = stringResource(type.titleRes),
				onDismissRequest = { editableColor.value = null },
				onColorSelected = {
					fireEvent(UpdateColorsEvent(currentTheme.update(colorType = type, color = it)))
					editableColor.value = null
				}
			)
		}
	}
}

private fun AppColorTheme.update(colorType: ColorType, color: Color): AppColorTheme = when(colorType) {
	ColorType.ACCENT -> copy(accent = color)
	ColorType.BACKGROUND -> copy(background = color)
	ColorType.ADDITIONAL -> copy(additional = color)
	ColorType.ADDITIONAL_CONTAINER -> copy(additionalContainer = color)
	ColorType.ACCENT_CONTAINER -> copy(accentContainer = color)
}
