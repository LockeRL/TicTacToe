package com.locker.feature.gamebot.bot

import com.locker.core.models.Player
import com.locker.feature.gamebot.model.Move
import com.locker.feature.gamescreen.controller.block.GameField
import com.locker.feature.gamescreen.controller.model.BoardState

class MediumBot : TicTacToeBot() {
    override fun getMove(field: GameField, activePlayer: Player, activeBlock: Pair<Int, Int>?): Move? {
        val opponent = if (activePlayer == Player.CROSS) Player.CIRCLE else Player.CROSS
        val possibleMoves = getPossibleMoves(field, activeBlock)
        if (possibleMoves.isEmpty()) return null

        // 1. Win a block if possible
        possibleMoves.find { canWinBlock(field[it.fieldI, it.fieldJ], it.blockI, it.blockJ, activePlayer) }?.let { return it }

        // 2. Block opponent from winning a block
        possibleMoves.find { canWinBlock(field[it.fieldI, it.fieldJ], it.blockI, it.blockJ, opponent) }?.let { return it }

        // 3. Filter moves that don't send opponent to a dangerous block
        val safeMoves = possibleMoves.filter { move ->
            val nextBlock = field[move.blockI, move.blockJ]
            if (nextBlock.winState.value != BoardState.InProgress) {
                !canOpponentWinAnyBlock(field, opponent)
            } else {
                !canOpponentWinThisBlock(nextBlock, opponent)
            }
        }

        val candidates = if (safeMoves.isNotEmpty()) safeMoves else possibleMoves

        // 4. Prefer center (1,1) then corners
        val center = candidates.find { it.blockI == 1 && it.blockJ == 1 }
        if (center != null) return center

        val corners = candidates.filter { (it.blockI == 0 || it.blockI == 2) && (it.blockJ == 0 || it.blockJ == 2) }
        if (corners.isNotEmpty()) return corners.random()

        return candidates.random()
    }
}
