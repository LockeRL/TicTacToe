package com.locker.feature.tutorialscreen.di

import com.locker.core.navigation.keys.TutorialScreenNavKey
import com.locker.feature.tutorialscreen.screen.TutorialScreen
import com.locker.feature.tutorialscreen.screen.TutorialScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation

val tutorialScreenModule = module {
    viewModelOf(::TutorialScreenViewModel)

    navigation<TutorialScreenNavKey> {
        TutorialScreen()
    }
}
