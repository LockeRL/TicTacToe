package com.locker.feature.gamebot.bot

import com.locker.core.models.Player
import com.locker.feature.gamebot.model.Move
import com.locker.feature.gamescreen.controller.block.GameField

class EasyBot : TicTacToeBot() {
    override fun getMove(field: GameField, activePlayer: Player, activeBlock: Pair<Int, Int>?): Move? {
        val possibleMoves = getPossibleMoves(field, activeBlock)
        return possibleMoves.randomOrNull()
    }
}
