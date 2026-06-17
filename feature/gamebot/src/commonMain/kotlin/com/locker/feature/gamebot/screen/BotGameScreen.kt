package com.locker.feature.gamebot.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.locker.core.models.Difficulty
import com.locker.core.models.Player
import com.locker.feature.component.modifier.Border
import com.locker.feature.core.screen.LocalFireEvent
import com.locker.feature.core.screen.ProvideScreenEvents
import com.locker.feature.core.theme.*
import com.locker.feature.gamebot.screen.view.BotGameHeader
import com.locker.feature.gamebot.screen.view.BotNextGameContent
import com.locker.feature.gamescreen.controller.block.GameBlock
import com.locker.feature.gamescreen.controller.block.GameField
import com.locker.feature.gamescreen.controller.model.BoardState
import com.locker.feature.gamescreen.screen.event.CellClickEvent
import com.locker.feature.gamescreen.screen.event.GameEndEvent
import com.locker.feature.gamescreen.screen.model.EndGameScreenState
import com.locker.feature.gamescreen.screen.view.GameBlockContainer
import com.locker.feature.gamescreen.screen.view.GameCell
import org.koin.compose.koinInject

@Composable
fun BotGameScreen(
    viewModel: BotGameScreenViewModel = koinInject(),
    modifier: Modifier = Modifier
) {
    ProvideScreenEvents(
        viewModel = viewModel
    ) { vm ->
        val player = vm.activePlayer.collectAsState()
        val endGameScreenState = vm.endScreen.collectAsState()
        val isThinking by vm.isBotThinking.collectAsState()
        val difficulty by vm.difficulty.collectAsState()
        val userPlayer by vm.userPlayer.collectAsState()
        
        BotGameScreenContent(
            field = vm.field,
            player = player,
            endGame = endGameScreenState,
            isThinking = isThinking,
            difficulty = difficulty,
            userPlayer = userPlayer,
            modifier = modifier
        )
    }
}

@Composable
private fun BotGameScreenContent(
    field: GameField,
    player: State<Player>,
    endGame: State<EndGameScreenState>,
    isThinking: Boolean,
    difficulty: Difficulty,
    userPlayer: Player,
    modifier: Modifier = Modifier,
) {
    val fireEvent = LocalFireEvent.current
    val boardState = field.winState.collectAsState()

    LaunchedEffect(boardState.value) {
        if (boardState.value != BoardState.InProgress) {
            fireEvent(GameEndEvent)
        }
    }

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
                .widthIn(max = Size400)
                .padding(bottom = Size40)
        ) {
            BotGameFieldContent(
                field = field,
                boardState = boardState,
                activePlayer = player,
                userPlayer = userPlayer,
                isBotThinking = isThinking
            )
        }

        if (isThinking) {
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
            BotNextGameContent(
                endGame = endGame.value,
                currentDifficulty = difficulty,
                currentUserPlayer = userPlayer
            )
        }
    }
}

@Composable
private fun BotGameFieldContent(
    field: GameField,
    boardState: State<BoardState>,
    activePlayer: State<Player>,
    userPlayer: Player,
    isBotThinking: Boolean,
    modifier: Modifier = Modifier,
) {
    val colors = TicTacToeTheme.colors
    val fireEvent = LocalFireEvent.current


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        BotGameHeader(
            activePlayer = activePlayer.value,
            userPlayer = userPlayer,
            isBotThinking = isBotThinking,
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

    val isActive by block.isActive.collectAsState()
    val winState by block.winState.collectAsState()
    GameBlockContainer(
        dimensionSize = block.dimension,
        border = Border(
            strokeWidth = SlimStrokeSize,
            color = colors.additional.copy(HALF_ALPHA),
            percentage = SUB_FIELD_LINE_LENGTH_PERCENT
        ),
        boardState = winState,
        modifier = modifier
            .clip(DefaultShape)
            .background(if (isActive) colors.accent.copy(alpha = LOW_ALPHA) else Color.Transparent)
    ) { i, j ->
        val cell = block.getCellFlow(i, j).collectAsState()
        GameCell(
            state = cell.value,
            onCellClick = { onCellClick(i, j) },
            modifier = Modifier.fillMaxSize()
        )
    }
}
