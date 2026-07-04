package com.locker.core.gamelogic.interop

import com.locker.core.models.model.Move
import com.locker.core.gamelogic.native.get_best_move
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toCValues
import kotlinx.cinterop.useContents

@OptIn(ExperimentalForeignApi::class)
actual fun getNativeMove(board: IntArray, macroBoard: IntArray, activeBlock: Int, player: Int, depth: Int): Move? {
	memScoped {
		val boardPtr = board.toCValues().getPointer(this)
		val macroPtr = macroBoard.toCValues().getPointer(this)

		// Hardcoded 1000ms limit
		val nativeMove = get_best_move(boardPtr, macroPtr, activeBlock, player, depth, 1000)

		return nativeMove.useContents {
			if (fieldI == -1) null
			else Move(
				fieldI = fieldI,
				fieldJ = fieldJ,
				blockI = blockI,
				blockJ = blockJ
			)
		}
	}
}
