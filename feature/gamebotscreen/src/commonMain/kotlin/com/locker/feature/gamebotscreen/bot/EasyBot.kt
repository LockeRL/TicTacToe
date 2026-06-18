package com.locker.feature.gamebotscreen.bot

import com.locker.core.gamelogic.controller.block.GameField
import com.locker.core.models.Player
import com.locker.feature.gamebotscreen.model.Move

class EasyBot : TicTacToeBot() {
    override fun getMove(field: GameField, activePlayer: Player, activeBlock: Pair<Int, Int>?): Move? {
        val possibleMoves = getPossibleMoves(field, activeBlock)
        return possibleMoves.randomOrNull()
    }
}
