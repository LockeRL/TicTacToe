package com.locker.core.models

import com.locker.resources.Res
import com.locker.resources.easy_bot
import com.locker.resources.hard_bot
import com.locker.resources.medium_bot
import org.jetbrains.compose.resources.StringResource


enum class Difficulty(val title: StringResource) {
    EASY(Res.string.easy_bot),
    MEDIUM(Res.string.medium_bot),
    HARD(Res.string.hard_bot),
}
