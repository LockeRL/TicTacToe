package com.locker.core.gamelogic.bot

import com.locker.core.gamelogic.controller.block.GameField
import com.locker.core.gamelogic.model.BoardState
import com.locker.core.gamelogic.model.CellState
import com.locker.core.models.model.Move
import com.locker.core.models.Player

expect fun getNativeMove(board: IntArray, macroBoard: IntArray, activeBlock: Int, player: Int, depth: Int): Move?

class HardBot : TicTacToeBot() {
    override fun getMove(field: GameField, activePlayer: Player, activeBlock: Pair<Int, Int>?): Move? {
        val board = IntArray(81)
        val macroBoard = IntArray(9)
        
        for (fi in 0 until 3) {
            for (fj in 0 until 3) {
                val block = field[fi, fj]
                val blockIdx = fi * 3 + fj
                
                // Fill macro board
                macroBoard[blockIdx] = when (val state = block.winState.value) {
                    is BoardState.Winner -> {
                        val winner = (state.winner as? CellState.Occupied)?.player
                        if (winner == Player.CROSS) 1 else 2
                    }
                    is BoardState.Draw -> 3
                    else -> 0
                }
                
                // Fill cells
                for (bi in 0 until 3) {
                    for (bj in 0 until 3) {
                        board[blockIdx * 9 + (bi * 3 + bj)] = when (val s = block.getCellState(bi, bj)) {
                            is CellState.Occupied -> if (s.player == Player.CROSS) 1 else 2
                            else -> 0
                        }
                    }
                }
            }
        }
        
        val activeBlockIdx = activeBlock?.let { it.first * 3 + it.second } ?: -1
        val playerInt = if (activePlayer == Player.CROSS) 1 else 2
        
        // Depth 8 is quite deep for Ultimate Tic Tac Toe and should be very strong in C
        return getNativeMove(board, macroBoard, activeBlockIdx, playerInt, 8)
    }
}
