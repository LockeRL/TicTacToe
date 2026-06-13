package com.locker.core.models

import androidx.compose.ui.graphics.Color
import com.locker.feature.core.theme.Beige
import com.locker.feature.core.theme.Blue
import com.locker.feature.core.theme.BlueBlackBackgroundColor
import com.locker.feature.core.theme.DarkBlue
import com.locker.feature.core.theme.DarkOlive
import com.locker.feature.core.theme.DefaultBlackBackgroundColor
import com.locker.feature.core.theme.DefaultWhiteBackgroundColor
import com.locker.feature.core.theme.FuzzyBrown
import com.locker.feature.core.theme.Green
import com.locker.feature.core.theme.GreyBackgroundColor
import com.locker.feature.core.theme.RazzmatazzColor
import com.locker.feature.core.theme.StrongOrange
import com.locker.feature.core.theme.StrongYellow
import com.locker.feature.core.theme.VariantDarkColor
import com.locker.feature.core.theme.VariantLightColor
import com.locker.feature.core.theme.WeakOrange
import com.locker.feature.core.theme.WeakYellow

data class AppColors(
	val background: Color,
	val accent: Color,
	val additional: Color,
	val additionalContainer: Color,
	val accentContainer: Color
) {
	companion object {
		val Default = AppColors(
			accent = RazzmatazzColor,
			additional = WeakYellow,
			background = DefaultBlackBackgroundColor,
			additionalContainer = VariantLightColor,
			accentContainer = Color.White
		)

		private val AppColors2 = AppColors(
			accent = Blue,
			additional = StrongYellow,
			background = BlueBlackBackgroundColor,
			additionalContainer = VariantLightColor,
			accentContainer = Color.White
		)

		private val AppColors3 = AppColors(
			accent = Green,
			additional = StrongOrange,
			background = DefaultBlackBackgroundColor,
			additionalContainer = VariantLightColor,
			accentContainer = Color.White
		)

		private val AppColors4 = AppColors(
			accent = Beige,
			additional = DarkBlue,
			background = DefaultWhiteBackgroundColor,
			additionalContainer = VariantDarkColor,
			accentContainer = Color.Black
		)

		private val AppColors5 = AppColors(
			accent = FuzzyBrown,
			additional = DarkOlive,
			background = DefaultWhiteBackgroundColor,
			additionalContainer = VariantDarkColor,
			accentContainer = Color.Black
		)

		private val AppColors6 = AppColors(
			accent = Beige,
			additional = Blue,
			background = DefaultBlackBackgroundColor,
			additionalContainer = VariantLightColor,
			accentContainer = Color.White
		)

		private val AppColors7 = AppColors(
			accent = WeakOrange,
			additional = Blue,
			background = GreyBackgroundColor,
			additionalContainer = VariantLightColor,
			accentContainer = Color.White
		)

		val ColorsList: List<AppColors> =
			listOf(Default, AppColors2, AppColors3, AppColors4, AppColors5, AppColors6, AppColors7)
	}
}
