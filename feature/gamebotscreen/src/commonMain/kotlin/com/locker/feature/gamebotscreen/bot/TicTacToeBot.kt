package com.locker.feature.gamebotscreen.bot

import com.locker.core.gamelogic.controller.block.GameBlock
import com.locker.core.gamelogic.controller.block.GameField
import com.locker.core.gamelogic.model.BoardState
import com.locker.core.gamelogic.model.CellState
import com.locker.core.models.Player
import com.locker.feature.gamebotscreen.model.Move

abstract class TicTacToeBot {

    abstract fun getMove(field: GameField, activePlayer: Player, activeBlock: Pair<Int, Int>?): Move?

    protected fun getPossibleMoves(field: GameField, activeBlock: Pair<Int, Int>?): List<Move> {
        val moves = mutableListOf<Move>()
        if (activeBlock != null) {
            val (fi, fj) = activeBlock
            val block = field[fi, fj]
            for (bi in 0 until 3) {
                for (bj in 0 until 3) {
                    if (block.getCellState(bi, bj) == CellState.Empty) {
                        moves.add(Move(fi, fj, bi, bj))
                    }
                }
            }
        } else {
            for (fi in 0 until 3) {
                for (fj in 0 until 3) {
                    val block = field[fi, fj]
                    if (block.winState.value == BoardState.InProgress) {
                        for (bi in 0 until 3) {
                            for (bj in 0 until 3) {
                                if (block.getCellState(bi, bj) == CellState.Empty) {
                                    moves.add(Move(fi, fj, bi, bj))
                                }
                            }
                        }
                    }
                }
            }
        }
        return moves
    }

    protected fun canOpponentWinAnyBlock(field: GameField, opponent: Player): Boolean {
        for (fi in 0 until 3) {
            for (fj in 0 until 3) {
                val block = field[fi, fj]
                if (block.winState.value == BoardState.InProgress) {
                    if (canOpponentWinThisBlock(block, opponent)) return true
                }
            }
        }
        return false
    }

    protected fun canOpponentWinThisBlock(block: GameBlock, opponent: Player): Boolean {
        for (bi in 0 until 3) {
            for (bj in 0 until 3) {
                if (block.getCellState(bi, bj) == CellState.Empty) {
                    if (canWinBlock(block, bi, bj, opponent)) return true
                }
            }
        }
        return false
    }

    protected fun canWinBlock(block: GameBlock, bi: Int, bj: Int, player: Player): Boolean {
        // Rows
        for (i in 0 until 3) {
            var count = 0
            var empty = -1
            for (j in 0 until 3) {
                val state = block.getCellState(i, j)
                if (state is CellState.Occupied && state.player == player) count++
                else if (state == CellState.Empty) empty = j
            }
            if (count == 2 && empty == bj && i == bi) return true
        }

        // Columns
        for (j in 0 until 3) {
            var count = 0
            var empty = -1
            for (i in 0 until 3) {
                val state = block.getCellState(i, j)
                if (state is CellState.Occupied && state.player == player) count++
                else if (state == CellState.Empty) empty = i
            }
            if (count == 2 && empty == bi && j == bj) return true
        }

        // Main Diagonal
        var mCount = 0
        var mEmpty = -1
        for (i in 0 until 3) {
            val state = block.getCellState(i, i)
            if (state is CellState.Occupied && state.player == player) mCount++
            else if (state == CellState.Empty) mEmpty = i
        }
        if (mCount == 2 && mEmpty == bi && bi == bj) return true

        // Side Diagonal
        var sCount = 0
        var sEmpty = -1
        for (i in 0 until 3) {
            val state = block.getCellState(i, 2 - i)
            if (state is CellState.Occupied && state.player == player) sCount++
            else if (state == CellState.Empty) sEmpty = i
        }
        if (sCount == 2 && sEmpty == bi && bj == 2 - bi) return true

        return false
    }

    protected fun countPotentialLines(block: GameBlock, player: Player): Int {
        var count = 0
        // Rows
        for (i in 0 until 3) {
            var p = 0; var e = 0
            for (j in 0 until 3) {
                val s = block.getCellState(i, j)
                if (s is CellState.Occupied && s.player == player) p++ else if (s == CellState.Empty) e++
            }
            if (p == 2 && e == 1) count++
        }
        // Columns
        for (j in 0 until 3) {
            var p = 0; var e = 0
            for (i in 0 until 3) {
                val s = block.getCellState(i, j)
                if (s is CellState.Occupied && s.player == player) p++ else if (s == CellState.Empty) e++
            }
            if (p == 2 && e == 1) count++
        }
        return count
    }
}
