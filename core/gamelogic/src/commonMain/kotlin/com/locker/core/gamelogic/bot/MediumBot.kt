package com.locker.core.gamelogic.bot

import com.locker.core.gamelogic.controller.block.GameBlock
import com.locker.core.gamelogic.controller.block.GameField
import com.locker.core.gamelogic.model.BoardState
import com.locker.core.gamelogic.model.CellState
import com.locker.core.models.model.Move
import com.locker.core.models.Player
import kotlin.math.max
import kotlin.math.min
import kotlin.time.TimeSource

class MediumBot : TicTacToeBot() {
    private val timeSource = TimeSource.Monotonic
    private var startTimeMark = timeSource.markNow()
    private val timeLimitMs = 900L

    override fun getMove(field: GameField, activePlayer: Player, activeBlock: Pair<Int, Int>?): Move? {
        startTimeMark = timeSource.markNow()
        val possibleMoves = getPossibleMoves(field = field, activeBlock = activeBlock)
        if (possibleMoves.isEmpty()) return null

        val opponent = if (activePlayer == Player.CROSS) Player.CIRCLE else Player.CROSS
        var bestScore = Int.MIN_VALUE
        var bestMove = possibleMoves.random()

        // Prioritize moves to improve Minimax pruning
        val sortedMoves = possibleMoves.sortedByDescending { move ->
            var score = 0
            
            // Win/Block heuristics for sorting only
            if (canWinBlock(field[move.fieldI, move.fieldJ], move.blockI, move.blockJ, activePlayer)) score += 100
            if (canWinBlock(field[move.fieldI, move.fieldJ], move.blockI, move.blockJ, opponent)) score += 80

            if (move.blockI == 1 && move.blockJ == 1) score += 15
            if ((move.blockI == 0 || move.blockI == 2) && (move.blockJ == 0 || move.blockJ == 2)) score += 8
            
            val targetBlock = field[move.blockI, move.blockJ]
            if (targetBlock.winState.value == BoardState.InProgress && canOpponentWinThisBlock(targetBlock, opponent)) {
                score -= 40 
            }
            if (targetBlock.winState.value != BoardState.InProgress) {
                score -= 30
            }
            score
        }

        for (move in sortedMoves) {
            if (isTimeUp()) break
            
            // We use a deeper search. Depth 6 is usually viable with Alpha-Beta and good sorting.
            val score = minimax(
	            field = field,
	            move = move,
	            depth = 6,
	            alpha = Int.MIN_VALUE,
	            beta = Int.MAX_VALUE,
	            isMaximizing = false,
	            botPlayer = activePlayer
            )
            if (score > bestScore) {
                bestScore = score
                bestMove = move
            }
        }

        return bestMove
    }

    private fun minimax(
        field: GameField,
        move: Move,
        depth: Int,
        alpha: Int,
        beta: Int,
        isMaximizing: Boolean,
        botPlayer: Player
    ): Int {
        // Simplified check: Does this move end the game?
        // In a real implementation we would simulate the board change.
        // Here we use evaluate which considers the current field.
        
        if (depth == 0 || isTimeUp()) {
            return evaluate(field, botPlayer)
        }

        var currentAlpha = alpha
        var currentBeta = beta

        val targetBlock = field[move.blockI, move.blockJ]
        val nextActiveBlock = if (targetBlock.winState.value == BoardState.InProgress) {
            Pair(move.blockI, move.blockJ)
        } else {
            null
        }

        val possibleMoves = getPossibleMoves(field, nextActiveBlock)
        if (possibleMoves.isEmpty()) return evaluate(field, botPlayer)

        if (isMaximizing) {
            var maxEval = Int.MIN_VALUE
            // Branching factor control
            val movesToTry = if (nextActiveBlock == null) possibleMoves.take(12) else possibleMoves
            for (m in movesToTry) { 
                val eval = minimax(field, m, depth - 1, currentAlpha, currentBeta, false, botPlayer)
                maxEval = max(maxEval, eval)
                currentAlpha = max(currentAlpha, eval)
                if (currentBeta <= currentAlpha) break
            }
            return maxEval
        } else {
            var minEval = Int.MAX_VALUE
            val movesToTry = if (nextActiveBlock == null) possibleMoves.take(12) else possibleMoves
            for (m in movesToTry) {
                val eval = minimax(field, m, depth - 1, currentAlpha, currentBeta, true, botPlayer)
                minEval = min(minEval, eval)
                currentBeta = min(currentBeta, eval)
                if (currentBeta <= currentAlpha) break
            }
            return minEval
        }
    }

    private fun evaluate(field: GameField, botPlayer: Player): Int {
        val opponent = if (botPlayer == Player.CROSS) Player.CIRCLE else Player.CROSS
        var score = 0

        val winState = field.winState.value
        if (winState is BoardState.Winner) {
            val winner = (winState.winner as? CellState.Occupied)?.player
            if (winner == botPlayer) return 1000000
            if (winner == opponent) return -1000000
        }

        // Global board evaluation
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                val block = field[i, j]
                val blockWin = block.winState.value
                val multiplier = if (i == 1 && j == 1) 3.0 else 1.2
                
                if (blockWin is BoardState.Winner) {
                    val winner = (blockWin.winner as? CellState.Occupied)?.player
                    if (winner == botPlayer) score += (10000 * multiplier).toInt()
                    else score -= (10000 * multiplier).toInt()
                } else if (blockWin is BoardState.Draw) {
                    // Draw is neutral, but slightly negative if we are winning or positive if losing.
                    // Keep it simple for now.
                } else {
                    // Evaluate non-finished blocks
                    score += (evaluateBlock(block, botPlayer) * multiplier).toInt()
                }
            }
        }
        
        // Bonus for having potential lines on the macro-board
        // (Simplified: not implementing full macro-line detection here)

        return score
    }

    private fun evaluateBlock(block: GameBlock, player: Player): Int {
        val opponent = if (player == Player.CROSS) Player.CIRCLE else Player.CROSS
        var score = 0
        
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                val state = block.getCellState(i, j)
                if (state is CellState.Occupied) {
                    var cellValue = 10
                    if (i == 1 && j == 1) cellValue = 50 
                    else if ((i == 0 || i == 2) && (j == 0 || j == 2)) cellValue = 25
                    
                    if (state.player == player) score += cellValue
                    else score -= cellValue
                }
            }
        }
        
        score += countPotentialLines(block, player) * 150
        score -= countPotentialLines(block, opponent) * 400 // Heavily penalize opponent's potential
        
        return score
    }

    private fun isTimeUp() = startTimeMark.elapsedNow().inWholeMilliseconds > timeLimitMs
}
