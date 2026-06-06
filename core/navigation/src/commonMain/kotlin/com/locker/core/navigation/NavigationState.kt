package com.locker.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import com.locker.core.navigation.keys.BaseNavKey
import org.koin.compose.navigation3.koinEntryProvider
import org.koin.core.annotation.KoinExperimentalAPI


@Composable
fun rememberNavigationState(
    startKey: BaseNavKey,
    topLevelKeys: Set<BaseNavKey>,
): NavigationState {
    val topLevelStack = rememberNavBackStack(startKey)
    val subStacks = topLevelKeys.associateWith { key -> rememberNavBackStack(key) }

    return remember(startKey, topLevelKeys) {
        NavigationState(
            startKey = startKey,
            topLevelStack = topLevelStack,
            subStacks = subStacks,
        )
    }
}

@Composable
fun rememberNavBackStack(vararg elements: BaseNavKey): NavBackStack<BaseNavKey> {
    return rememberSerializable(serializer = NavBackStackSerializer) {
        NavBackStack(*elements)
    }
}

class NavigationState(
    val startKey: BaseNavKey,
    val topLevelStack: NavBackStack<BaseNavKey>,
    val subStacks: Map<BaseNavKey, NavBackStack<BaseNavKey>>,
) {
    val currentTopLevelKey: BaseNavKey by derivedStateOf { topLevelStack.last() }

    val topLevelKeys: Set<BaseNavKey>
        get() = subStacks.keys

    val currentSubStack: NavBackStack<BaseNavKey>
        get() = subStacks[currentTopLevelKey] ?: error("Sub stack for $currentTopLevelKey does not exist")

    val currentKey: BaseNavKey by derivedStateOf { currentSubStack.last() }
}

@OptIn(KoinExperimentalAPI::class)
@Composable
fun NavigationState.toEntries(
    entryProvider: (Any) -> NavEntry<Any> = koinEntryProvider(),
): SnapshotStateList<NavEntry<Any>> {
    val decoratedEntries = subStacks.mapValues { (_, backStack) ->
        rememberDecoratedNavEntries(
            backStack = backStack,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
            entryProvider = entryProvider,
        )
    }

    return topLevelStack
        .flatMap { decoratedEntries[it] ?: emptyList() }
        .toMutableStateList()
}
