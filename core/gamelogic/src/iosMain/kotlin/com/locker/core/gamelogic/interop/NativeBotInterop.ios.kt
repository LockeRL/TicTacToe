package com.locker.core.gamelogic.interop

import com.locker.core.models.model.Move
import com.locker.core.gamelogic.native.get_best_move
import kotlinx.cinterop.*

@OptIn(ExperimentalForeignApi::class)
actual fun getNativeMove(board: IntArray, macroBoard: IntArray, activeBlock: Int, player: Int, depth: Int): Move? {
    memScoped {
        val boardPtr = board.toCValues().getPointer(this)
        val macroPtr = macroBoard.toCValues().getPointer(this)
        
        // Hardcoded 1000ms limit
        val nativeMove = get_best_move(boardPtr, macroPtr, activeBlock, player, depth, 1000)
        
        if (nativeMove.fieldI == -1) return null
        
        return Move(
            fieldI = nativeMove.fieldI,
            fieldJ = nativeMove.fieldJ,
            blockI = nativeMove.blockI,
            blockJ = nativeMove.blockJ
        )
    }
}
