package com.locker.feature.component.modifier

import androidx.compose.ui.Modifier

fun Modifier.conditional(
	condition: Boolean,
	ifTrue: Modifier.() -> Modifier,
	ifFalse: (Modifier.() -> Modifier)? = null
): Modifier = when {
	condition -> then(ifTrue(Modifier))
	ifFalse != null -> then(ifFalse(Modifier))
	else -> this
}
