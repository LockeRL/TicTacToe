package com.locker.feature.gamescreen.controller

import com.locker.feature.gamescreen.controller.block.GameField
import com.locker.feature.gamescreen.controller.handler.CellClickEventHandler
import com.locker.feature.gamescreen.controller.model.Player
import com.locker.feature.gamescreen.controller.model.BoardState
import com.locker.feature.gamescreen.controller.model.SetCellResults
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach

class GameController(
    val field: GameField,
    eventsHandler: CellClickEventHandler
) {
    private val _activePlayer: MutableStateFlow<Player> = MutableStateFlow(Player.CROSS)
    val activePlayer: StateFlow<Player> = _activePlayer

    private var activeBlock: Pair<Int, Int>? = null

    val eventsFlow = eventsHandler.eventFlow.onEach { cell ->
        setFieldState(
            fieldI = cell.fieldI,
            fieldJ = cell.fieldJ,
            blockI = cell.blockI,
            blockJ = cell.blockJ
        )
    }

    fun reset() {
        activeBlock = null
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
        activeBlock?.let { block -> block.first == i && block.second == j } ?: true


    private fun setActiveBlock(i: Int, j: Int) {
        setPreviousBlockFalse()

        val block = field[i, j]
        if (block.winState.value != BoardState.InProgress) {
            activeBlock = null
        } else {
            activeBlock = Pair(i, j)
            field.setActiveFieldState(i, j, true)
        }
    }

    private fun setActiveBlockNull() {
        setPreviousBlockFalse()
        activeBlock = null
    }

    private fun setPreviousBlockFalse() {
        activeBlock?.let { block ->
            field.setActiveFieldState(block.first, block.second, false)
        }
    }

    private fun setActivePlayer() {
        _activePlayer.value = if (activePlayer.value == Player.CROSS) Player.CIRCLE else Player.CROSS
    }
}
