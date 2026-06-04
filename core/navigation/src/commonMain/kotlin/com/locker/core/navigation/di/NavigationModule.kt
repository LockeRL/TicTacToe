package com.locker.core.navigation.di

import com.locker.core.navigation.Navigator
import org.koin.core.module.Module
import org.koin.dsl.module

val navigationModule: Module = module {
    single { Navigator() }
}
