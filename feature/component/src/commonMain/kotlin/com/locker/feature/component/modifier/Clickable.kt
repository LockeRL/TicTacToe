package com.locker.feature.component.modifier

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier

fun Modifier.clickableWithoutIndication(
	interactionSource: MutableInteractionSource? = null,
	onClick: () -> Unit
): Modifier = then(
	Modifier.clickable(
		interactionSource = interactionSource,
		indication = null,
		onClick = onClick,
	)
)
