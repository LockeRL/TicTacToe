package com.locker.core.navigation

import com.locker.core.navigation.keys.BaseNavKey
import kotlin.concurrent.Volatile


class Navigator {
    lateinit var state: NavigationState

    // Return true not to navigate
    @Volatile
    var consumeBackPress: (() -> Boolean)? = null

    fun navigate(key: BaseNavKey) {
        consumeBackPress = null
        when (key) {
            state.currentTopLevelKey -> clearSubStack()
            in state.topLevelKeys -> goToTopLevel(key)
            else -> goToKey(key)
        }
    }

    fun goBack() {
        val backHandlerResult: Boolean? = consumeBackPress?.invoke()

        if (backHandlerResult != true) {
            defaultGoBack()
        }
    }

    private fun defaultGoBack() {
        when (state.currentKey) {
            state.startKey -> error("You cannot go back from the start route")
            state.currentTopLevelKey -> state.topLevelStack.removeLastOrNull()
            else -> state.currentSubStack.removeLastOrNull()
        }
    }

    private fun goToKey(key: BaseNavKey) {
        state.currentSubStack.apply {
            // Remove it if it's already in the stack so it's added at the end.
            remove(key)
            add(key)
        }
    }

    private fun goToTopLevel(key: BaseNavKey) {
        state.topLevelStack.apply {
            if (key == state.startKey) {
                // This is the start key. Clear the stack so it's added as the only key.
                clear()
            } else {
                // Remove it if it's already in the stack so it's added at the end.
                remove(key)
            }
            add(key)
        }
    }

    private fun clearSubStack() {
        state.currentSubStack.run {
            if (size > 1) subList(1, size).clear()
        }
    }
}
