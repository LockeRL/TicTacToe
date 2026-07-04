package com.locker.core.gamelogic.bot

import com.locker.core.gamelogic.controller.block.GameField
import com.locker.core.gamelogic.model.Move
import com.locker.core.models.Player

class EasyBot : TicTacToeBot() {
    override fun getMove(field: GameField, activePlayer: Player, activeBlock: Pair<Int, Int>?): Move? =
        getRandomMove(field = field, activeBlock = activeBlock)
}
