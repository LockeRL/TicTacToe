package org.locker.tictactoe.presentation.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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

fun Modifier.clickableWithoutIndication(
    interactionSource: MutableInteractionSource? = null,
    onClick: () -> Unit
): Modifier = then(
    Modifier.clickable(
        interactionSource = interactionSource,
        indication = null,
        onClick = onClick
    )
)
