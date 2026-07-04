package com.locker.core.gamelogic.bot

import com.locker.core.models.model.Move
import com.locker.core.nativebot.NativeBotBridge

actual fun getNativeMove(board: IntArray, macroBoard: IntArray, activeBlock: Int, player: Int, depth: Int): Move? {
    return try {
        NativeBotBridge.getNativeMoveInternal(board, macroBoard, activeBlock, player, depth)
    } catch (e: Throwable) {
        null
    }
}
