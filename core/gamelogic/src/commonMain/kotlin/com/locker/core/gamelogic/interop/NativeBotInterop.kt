package com.locker.core.gamelogic.interop

import com.locker.core.models.model.Move

expect fun getNativeMove(board: IntArray, macroBoard: IntArray, activeBlock: Int, player: Int, depth: Int): Move?
