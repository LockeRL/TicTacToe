package com.locker.feature.core.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

val VariantLightColor = Color(0xFFACACAC)
val VariantDarkColor = Color(0xFF757575)

val RazzmatazzColor = Color(0xFFF92673)

val StrongYellow = Color(0xFFF7DF2F)
val WeakYellow = Color(0xFFDDDA7C)
val WeakOrange = Color(0xFFE8791F)
val StrongOrange = Color(0xFFF06605)

val DefaultBlackBackgroundColor = Color(0xFF101010)
val BlueBlackBackgroundColor = Color(0xFF0D161D)
val GreyBackgroundColor = Color(0xFF343434)
val DefaultWhiteBackgroundColor = Color(0xFFFAFAFA)

val Blue = Color(0xFF57C2F0)
val DarkBlue = Color(0xFF212547)

val Green = Color(0xFF31A131)

val Beige = Color(0xFFABA073)
val DarkOlive = Color(0xFF3b3b36)

val FuzzyBrown = Color(0xFFbb565c)

@Immutable
data class AppColorTheme(
	val background: Color,
	val accent: Color,
	val additional: Color,
	val additionalContainer: Color,
	val accentContainer: Color,
	val id: Int = 0,
) {
	companion object {
		val DEFAULT = AppColorTheme(
			accent = RazzmatazzColor,
			additional = WeakYellow,
			background = DefaultBlackBackgroundColor,
			additionalContainer = VariantLightColor,
			accentContainer = Color.White
		)
	}
}
