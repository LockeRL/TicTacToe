package com.locker.core.nativebot

import com.locker.core.models.model.Move

object NativeBotBridge {
    init {
        System.loadLibrary("native_bot")
    }

    external fun getNativeMoveInternal(
        board: IntArray,
        macroBoard: IntArray,
        activeBlock: Int,
        player: Int,
        depth: Int
    ): Move?
}
