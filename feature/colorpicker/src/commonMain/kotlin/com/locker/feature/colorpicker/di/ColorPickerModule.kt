package com.locker.feature.colorpicker.di

import com.locker.feature.colorpicker.view.ColorsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val colorPickerModule = module {
	viewModel { ColorsViewModel() }
}
