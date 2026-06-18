package com.locker.core.gamelogic.controller.block

import com.locker.core.gamelogic.model.BoardState
import com.locker.core.gamelogic.model.CellState
import com.locker.core.gamelogic.model.SetCellResults
import com.locker.core.models.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GameBlock : GameMatrix<MutableStateFlow<CellState>>({ MutableStateFlow(CellState.Empty) }) {
	private val _isActive: MutableStateFlow<Boolean> = MutableStateFlow(false)
	val isActive: StateFlow<Boolean> = _isActive

	fun setActiveState(state: Boolean) {
		_isActive.value = state
	}

	fun setCellValue(i: Int, j: Int, state: Player): SetCellResults =
		when {
			winState.value != BoardState.InProgress -> SetCellResults.ALREADY_WIN
			getCellState(i, j) is CellState.Occupied -> SetCellResults.ALREADY_SET
			else -> {
				get(i, j).value = CellState.Occupied(state)
				if (getAndUpdateBoardState() != BoardState.InProgress)
					SetCellResults.END_BLOCK
				else
					SetCellResults.SUCCESS
			}
		}

	fun getCellFlow(i: Int, j: Int): StateFlow<CellState> = get(i, j)

	override fun getCellStateFromItem(item: MutableStateFlow<CellState>): CellState = item.value

	override fun resetItem(item: MutableStateFlow<CellState>) {
		item.value = CellState.Empty
	}
}
