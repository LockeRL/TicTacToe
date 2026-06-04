package com.locker.feature.core.theme

import androidx.compose.ui.graphics.Color
import com.locker.feature.core.model.AppColors

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

val AppColors1 = AppColors(
    accent = RazzmatazzColor,
    additional = WeakYellow,
    background = DefaultBlackBackgroundColor,
    additionalContainer = VariantLightColor,
    accentContainer = Color.White
)

val AppColors2 = AppColors(
    accent = Blue,
    additional = StrongYellow,
    background = BlueBlackBackgroundColor,
    additionalContainer = VariantLightColor,
    accentContainer = Color.White
)

val AppColors3 = AppColors(
    accent = Green,
    additional = StrongOrange,
    background = DefaultBlackBackgroundColor,
    additionalContainer = VariantLightColor,
    accentContainer = Color.White
)

val AppColors4 = AppColors(
    accent = Beige,
    additional = DarkBlue,
    background = DefaultWhiteBackgroundColor,
    additionalContainer = VariantDarkColor,
    accentContainer = Color.Black
)

val AppColors5 = AppColors(
    accent = FuzzyBrown,
    additional = DarkOlive,
    background = DefaultWhiteBackgroundColor,
    additionalContainer = VariantDarkColor,
    accentContainer = Color.Black
)

val AppColors6 = AppColors(
    accent = Beige,
    additional = Blue,
    background = DefaultBlackBackgroundColor,
    additionalContainer = VariantLightColor,
    accentContainer = Color.White
)

val AppColors7 = AppColors(
    accent = WeakOrange,
    additional = Blue,
    background = GreyBackgroundColor,
    additionalContainer = VariantLightColor,
    accentContainer = Color.White
)

val ColorsList: List<AppColors> =
    listOf(AppColors1, AppColors2, AppColors3, AppColors4, AppColors5, AppColors6, AppColors7)
