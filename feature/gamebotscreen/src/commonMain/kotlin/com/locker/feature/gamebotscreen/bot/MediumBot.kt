package com.locker.feature.gamebotscreen.bot

import com.locker.core.gamelogic.controller.block.GameField
import com.locker.core.gamelogic.model.BoardState
import com.locker.core.models.Player
import com.locker.feature.gamebotscreen.model.Move
import kotlin.time.TimeSource

class MediumBot : TicTacToeBot() {
    private val timeSource = TimeSource.Monotonic
    private val timeLimitMs = 500L

    override fun getMove(field: GameField, activePlayer: Player, activeBlock: Pair<Int, Int>?): Move? {
        val startTimeMark = timeSource.markNow()
        val opponent = if (activePlayer == Player.CROSS) Player.CIRCLE else Player.CROSS
        val possibleMoves = getPossibleMoves(field, activeBlock)
        if (possibleMoves.isEmpty()) return null

        // 1. Win a block if possible
        possibleMoves.find { canWinBlock(field[it.fieldI, it.fieldJ], it.blockI, it.blockJ, activePlayer) }?.let { return it }

        if (startTimeMark.elapsedNow().inWholeMilliseconds > timeLimitMs) return possibleMoves.random()

        // 2. Block opponent from winning a block
        possibleMoves.find { canWinBlock(field[it.fieldI, it.fieldJ], it.blockI, it.blockJ, opponent) }?.let { return it }

        if (startTimeMark.elapsedNow().inWholeMilliseconds > timeLimitMs) return possibleMoves.random()

        // 3. Filter moves that don't send opponent to a dangerous block
        val opponentCanWinAny = canOpponentWinAnyBlock(field, opponent)
        val safeMoves = possibleMoves.filter { move ->
            if (startTimeMark.elapsedNow().inWholeMilliseconds > timeLimitMs) return@filter true
            val nextBlock = field[move.blockI, move.blockJ]
            if (nextBlock.winState.value != BoardState.InProgress) {
                !opponentCanWinAny
            } else {
                !canOpponentWinThisBlock(nextBlock, opponent)
            }
        }

        val candidates = safeMoves.ifEmpty { possibleMoves }

        // 4. Prefer center (1,1) then corners
        val center = candidates.find { it.blockI == 1 && it.blockJ == 1 }
        if (center != null) return center

        val corners = candidates.filter { (it.blockI == 0 || it.blockI == 2) && (it.blockJ == 0 || it.blockJ == 2) }
        if (corners.isNotEmpty()) return corners.random()

        return candidates.random()
    }
}
