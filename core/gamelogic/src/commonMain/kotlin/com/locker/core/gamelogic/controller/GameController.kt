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

    init {
	    setAllFieldActivity(true)
    }

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
	    setAllFieldActivity(false)
    }

    private fun setFieldState(fieldI: Int, fieldJ: Int, blockI: Int, blockJ: Int) {
        if (field.winState.value != BoardState.InProgress)
            return

        if (!isActiveBlock(fieldI, fieldJ))
            return

        val block = field[fieldI, fieldJ]
        val setResult = block.setCellValue(i = blockI, j = blockJ, state = activePlayer.value)

        if (setResult == SetCellResults.END_BLOCK) {
            val fieldRes = field.getAndUpdateBoardState()
            if (fieldRes != BoardState.InProgress) {
                setActiveBlockNull()
                return
            }
        }

        if (setResult == SetCellResults.SUCCESS || setResult == SetCellResults.END_BLOCK) {
            setActivePlayer()
            setActiveBlock(i = blockI, j = blockJ)
        }

    }

    private fun isActiveBlock(i: Int, j: Int): Boolean =
        _activeBlock?.let { block -> block.first == i && block.second == j } ?: true


    private fun setActiveBlock(i: Int, j: Int) {
        setPreviousBlockFalse()

        val block = field[i, j]
        if (block.winState.value != BoardState.InProgress) {
            _activeBlock = null
            for (fieldI in 0 until field.dimension) {
                for (fieldJ in 0 until field.dimension) {
                    if (field[fieldI, fieldJ].winState.value == BoardState.InProgress) {
                        field.setActiveFieldState(i = fieldI, j = fieldJ, state = true)
                    }
                }
            }
        } else {
            _activeBlock = Pair(i, j)
            field.setActiveFieldState(i = i, j = j, state = true)
        }
    }

    private fun setActiveBlockNull() {
        setPreviousBlockFalse()
        _activeBlock = null
    }

    private fun setPreviousBlockFalse() {
        val activeBlock = _activeBlock
        if (activeBlock != null) {
            field.setActiveFieldState(i = activeBlock.first, j = activeBlock.second, state = false)
        } else {
	        setAllFieldActivity(false)
        }
    }

    private fun setActivePlayer() {
        _activePlayer.value = if (activePlayer.value == Player.CROSS) Player.CIRCLE else Player.CROSS
    }

    private fun setAllFieldActivity(isActive: Boolean) {
	    for (i in 0 until field.dimension) {
		    for (j in 0 until field.dimension) {
			    field.setActiveFieldState(i = i, j = j, state = isActive)
		    }
	    }
    }
}
