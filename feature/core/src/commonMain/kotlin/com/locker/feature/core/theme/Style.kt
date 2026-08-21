package com.locker.feature.core.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.locker.feature.core.isTablet

val DefaultShape: Shape
	@Composable
	get() = RoundedCornerShape(if (isTablet()) 20.dp else 12.dp)
