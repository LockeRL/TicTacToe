package com.locker.feature.gamebotscreen.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.locker.core.gamelogic.controller.block.GameBlock
import com.locker.core.gamelogic.controller.block.GameField
import com.locker.core.gamelogic.model.BoardState
import com.locker.feature.component.field.GameBlockContainer
import com.locker.feature.component.field.GameCell
import com.locker.feature.component.modifier.Border
import com.locker.feature.core.screen.LocalFireEvent
import com.locker.feature.core.screen.ProvideScreenEvents
import com.locker.feature.core.theme.DefaultShape
import com.locker.feature.core.theme.DefaultStrokeSize
import com.locker.feature.core.theme.FADE_WIN_BLOCK_TIME
import com.locker.feature.core.theme.HALF_ALPHA
import com.locker.feature.core.theme.LOW_ALPHA
import com.locker.feature.core.theme.NEXT_GAME_SCREEN_CHANGE_DELAY_DURATION
import com.locker.feature.core.theme.SUB_FIELD_LINE_LENGTH_PERCENT
import com.locker.feature.core.theme.Size40
import com.locker.feature.core.theme.Size400
import com.locker.feature.core.theme.SlimStrokeSize
import com.locker.feature.core.theme.Space8
import com.locker.feature.core.theme.TicTacToeTheme
import com.locker.feature.gamebotscreen.screen.event.CellClickEvent
import com.locker.feature.gamebotscreen.screen.model.EndGameScreenState
import com.locker.feature.gamebotscreen.screen.model.HeaderState
import com.locker.feature.gamebotscreen.screen.view.Header
import com.locker.feature.gamebotscreen.screen.view.NextGameContent
import org.koin.compose.koinInject

@Composable
fun GameBotScreen(
	viewModel: GameBotScreenViewModel = koinInject(),
	modifier: Modifier = Modifier
) {
	ProvideScreenEvents(
		viewModel = viewModel
	) { viewmodel ->
		val endGameScreenState = viewmodel.endScreen.collectAsState()
		val header = viewModel.header.collectAsState()

		BotGameScreenContent(
			field = viewmodel.field,
			endGame = endGameScreenState,
			header = header,
			modifier = modifier,
		)
	}
}

@Composable
private fun BotGameScreenContent(
	field: GameField,
	endGame: State<EndGameScreenState>,
	header: State<HeaderState>,
	modifier: Modifier = Modifier,
) {
	val boardState = field.winState.collectAsState()

	Box(
		contentAlignment = Alignment.Center,
		modifier = modifier
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
				.widthIn(max = Size400)
				.padding(bottom = Size40)
		) {
			BotGameFieldContent(
				field = field,
				boardState = boardState,
				header = header,
			)
		}

		if (!header.value.isUserTurn) {
			Box(
				modifier = Modifier
					.fillMaxSize()
					.background(Color.Black.copy(alpha = 0.1f))
					.clickable(enabled = false) {},
				contentAlignment = Alignment.TopCenter
			) {
				CircularProgressIndicator(
					modifier = Modifier.padding(top = 100.dp),
					color = TicTacToeTheme.colors.accent
				)
			}
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
private fun BotGameFieldContent(
	field: GameField,
	boardState: State<BoardState>,
	header: State<HeaderState>,
	modifier: Modifier = Modifier,
) {
	val colors = TicTacToeTheme.colors
	val fireEvent = LocalFireEvent.current


	Column(
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
		modifier = modifier
	) {
		Header(
			header = header.value,
			modifier = Modifier
				.fillMaxWidth()
				.padding(Space8)
		)

		GameBlockContainer(
			dimensionSize = field.dimension,
			border = Border(strokeWidth = DefaultStrokeSize, color = colors.accent),
			boardState = boardState.value,
			showAlphaAnimation = false,
			showGameCell = false,
			modifier = Modifier
				.fillMaxWidth()
				.aspectRatio(1f)
		) { i, j ->
			BotGameFieldBlock(
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
					.padding(Space8)
			)
		}
	}
}

@Composable
fun BotGameFieldBlock(
	block: GameBlock,
	onCellClick: (Int, Int) -> Unit,
	modifier: Modifier = Modifier
) {
	val colors = TicTacToeTheme.colors

	val isActive = block.isActive.collectAsState()
	val winState = block.winState.collectAsState()
	GameBlockContainer(
		dimensionSize = block.dimension,
		border = Border(
			strokeWidth = SlimStrokeSize,
			color = colors.additional.copy(HALF_ALPHA),
			percentage = SUB_FIELD_LINE_LENGTH_PERCENT
		),
		boardState = winState.value,
		modifier = modifier
			.clip(DefaultShape)
			.background(if (isActive.value) colors.accent.copy(alpha = LOW_ALPHA) else Color.Transparent)
	) { i, j ->
		val cell = block.getCellFlow(i, j).collectAsState()
		GameCell(
			state = cell.value,
			onCellClick = { onCellClick(i, j) },
			modifier = Modifier.fillMaxSize()
		)
	}
}
