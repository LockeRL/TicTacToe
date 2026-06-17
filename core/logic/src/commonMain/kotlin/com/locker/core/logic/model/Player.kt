package com.locker.core.logic.model

import com.locker.resources.Res
import com.locker.resources.ic_circle
import com.locker.resources.ic_cross
import org.jetbrains.compose.resources.DrawableResource

enum class Player(val icon: DrawableResource) {
    CROSS(Res.drawable.ic_cross),
    CIRCLE(Res.drawable.ic_circle)
}
