package com.locker.core.gamelogic.controller

import com.locker.core.gamelogic.controller.block.GameField
import com.locker.core.gamelogic.model.BoardState
import com.locker.core.gamelogic.model.SetCellResults
import com.locker.core.models.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GameController(
    val field: GameField
) {
    private val _activePlayer: MutableStateFlow<Player> = MutableStateFlow(Player.CROSS)
    val activePlayer: StateFlow<Player> = _activePlayer

    private var _activeBlock: Pair<Int, Int>? = null
    val activeBlock: Pair<Int, Int>? get() = _activeBlock

    fun onCellClick(fieldI: Int, fieldJ: Int, blockI: Int, blockJ: Int) {
        setFieldState(
            fieldI = fieldI,
            fieldJ = fieldJ,
            blockI = blockI,
            blockJ = blockJ
        )
    }

    fun reset() {
        _activeBlock = null
        _activePlayer.value = Player.CROSS
        field.reset()
    }

    private fun setFieldState(fieldI: Int, fieldJ: Int, blockI: Int, blockJ: Int) {
        if (field.winState.value != BoardState.InProgress)
            return

        if (!isActiveBlock(fieldI, fieldJ))
            return

        val block = field[fieldI, fieldJ]
        val setResult = block.setCellValue(blockI, blockJ, activePlayer.value)

        if (setResult == SetCellResults.END_BLOCK) {
            val fieldRes = field.getAndUpdateBoardState()
            if (fieldRes != BoardState.InProgress) {
                setActiveBlockNull()
                return
            }
        }

        if (setResult == SetCellResults.SUCCESS || setResult == SetCellResults.END_BLOCK) {
            setActivePlayer()
            setActiveBlock(blockI, blockJ)
        }

    }

    private fun isActiveBlock(i: Int, j: Int): Boolean =
        _activeBlock?.let { block -> block.first == i && block.second == j } ?: true


    private fun setActiveBlock(i: Int, j: Int) {
        setPreviousBlockFalse()

        val block = field[i, j]
        if (block.winState.value != BoardState.InProgress) {
            _activeBlock = null
        } else {
            _activeBlock = Pair(i, j)
            field.setActiveFieldState(i, j, true)
        }
    }

    private fun setActiveBlockNull() {
        setPreviousBlockFalse()
        _activeBlock = null
    }

    private fun setPreviousBlockFalse() {
        _activeBlock?.let { block ->
            field.setActiveFieldState(block.first, block.second, false)
        }
    }

    private fun setActivePlayer() {
        _activePlayer.value = if (activePlayer.value == Player.CROSS) Player.CIRCLE else Player.CROSS
    }
}
