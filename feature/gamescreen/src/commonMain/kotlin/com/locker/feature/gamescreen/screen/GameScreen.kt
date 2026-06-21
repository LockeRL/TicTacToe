package com.locker.feature.gamescreen.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.locker.core.gamelogic.controller.block.GameBlock
import com.locker.core.gamelogic.controller.block.GameField
import com.locker.core.gamelogic.model.BoardState
import com.locker.core.models.Player
import com.locker.feature.component.field.GameBlockContainer
import com.locker.feature.component.modifier.Border
import com.locker.feature.core.screen.LocalFireEvent
import com.locker.feature.core.screen.ProvideScreenEvents
import com.locker.feature.core.theme.DefaultShape
import com.locker.feature.core.theme.FADE_WIN_BLOCK_TIME
import com.locker.feature.core.theme.HALF_ALPHA
import com.locker.feature.core.theme.LOW_ALPHA
import com.locker.feature.core.theme.NEXT_GAME_SCREEN_CHANGE_DELAY_DURATION
import com.locker.feature.core.theme.SUB_FIELD_LINE_LENGTH_PERCENT
import com.locker.feature.core.theme.TicTacToeTheme
import com.locker.feature.gamescreen.screen.event.CellClickEvent
import com.locker.feature.component.field.GameCell
import com.locker.feature.core.theme.ACTIVE_BLOCK_DURATION
import com.locker.feature.gamescreen.screen.view.PlayerTurnIconWithText
import com.locker.feature.gamescreen.screen.model.EndGameScreenState
import com.locker.feature.gamescreen.screen.view.NextGameContent
import org.koin.compose.koinInject

@Composable
fun GameScreen(
	viewModel: GameScreenViewModel = koinInject(),
	modifier: Modifier = Modifier
) {
	ProvideScreenEvents(
		viewModel = viewModel
	) { viewModel ->
		val player = viewModel.activePlayer.collectAsState()
		val endGameScreenState = viewModel.endScreen.collectAsState()
		GameScreenContent(
			field = viewModel.field,
			player = player,
			endGame = endGameScreenState,
			modifier = modifier
		)
	}
}

@Composable
private fun GameScreenContent(
	field: GameField,
	player: State<Player>,
	endGame: State<EndGameScreenState>,
	modifier: Modifier = Modifier,
) {
	val boardState = field.winState.collectAsState()

	Box(
		contentAlignment = Alignment.Center,
		modifier = modifier
			.windowInsetsPadding(WindowInsets.statusBars)
	) {
		AnimatedVisibility(
			visible = boardState.value == BoardState.InProgress,
			enter = fadeIn(animationSpec = tween(FADE_WIN_BLOCK_TIME)),
			exit = fadeOut(
				animationSpec = tween(
					durationMillis = FADE_WIN_BLOCK_TIME,
					delayMillis = NEXT_GAME_SCREEN_CHANGE_DELAY_DURATION
				)
			),
			modifier = Modifier
				.widthIn(max = 400.dp)
				.padding(bottom = 40.dp)
		) {
			GameFieldContent(
				field = field,
				boardState = boardState,
				player = player,
			)
		}

		AnimatedVisibility(
			visible = boardState.value != BoardState.InProgress,
			enter = fadeIn(
				animationSpec = tween(
					durationMillis = FADE_WIN_BLOCK_TIME,
					delayMillis = NEXT_GAME_SCREEN_CHANGE_DELAY_DURATION
				)
			),
			exit = fadeOut(animationSpec = tween(FADE_WIN_BLOCK_TIME)),
			modifier = Modifier.fillMaxWidth()
		) {
			NextGameContent(
				endGame = endGame.value,
			)
		}
	}
}

@Composable
private fun GameFieldContent(
	field: GameField,
	boardState: State<BoardState>,
	player: State<Player>,
	modifier: Modifier = Modifier,
) {
	val colors = TicTacToeTheme.colors
	val fireEvent = LocalFireEvent.current


	Column(
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
		modifier = modifier
	) {
		PlayerTurnIconWithText(
			player = player,
			modifier = Modifier
				.fillMaxWidth()
				.padding(8.dp)
		)

		GameBlockContainer(
			dimensionSize = field.dimension,
			border = Border(strokeWidth = 1.dp, color = colors.accent),
			boardState = boardState.value,
			showAlphaAnimation = false,
			showGameCell = false,
			modifier = Modifier
				.fillMaxWidth()
				.aspectRatio(1f)
		) { i, j ->
			GameFieldBlock(
				block = field[i, j],
				onCellClick = { blockRow, blockColumn ->
					fireEvent(
						CellClickEvent(
							fieldI = i,
							fieldJ = j,
							blockI = blockRow,
							blockJ = blockColumn
						)
					)
				},
				modifier = Modifier
					.fillMaxSize()
					.padding(8.dp)
			)
		}
	}
}

@Composable
fun GameFieldBlock(
	block: GameBlock,
	onCellClick: (Int, Int) -> Unit,
	modifier: Modifier = Modifier
) {
	val colors = TicTacToeTheme.colors

	val isActive by block.isActive.collectAsState()
	val winState by block.winState.collectAsState()
	val activeBlockColor by animateColorAsState(
		targetValue = if (isActive) colors.accent.copy(alpha = LOW_ALPHA) else Color.Transparent,
		animationSpec = tween(durationMillis = ACTIVE_BLOCK_DURATION)
	)

	GameBlockContainer(
		dimensionSize = block.dimension,
		border = Border(
			strokeWidth = (0.5).dp,
			color = colors.additional.copy(HALF_ALPHA),
			percentage = SUB_FIELD_LINE_LENGTH_PERCENT
		),
		boardState = winState,
		modifier = modifier
			.clip(DefaultShape)
			.background(activeBlockColor)
	) { i, j ->
		val cell = block.getCellFlow(i, j).collectAsState()
		GameCell(
			state = cell.value,
			onCellClick = { onCellClick(i, j) },
			modifier = Modifier.fillMaxSize()
		)
	}
}

