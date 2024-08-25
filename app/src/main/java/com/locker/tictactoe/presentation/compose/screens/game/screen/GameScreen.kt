package com.locker.tictactoe.presentation.compose.screens.game.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.locker.tictactoe.R
import com.locker.tictactoe.presentation.common.Border
import com.locker.tictactoe.presentation.compose.screens.game.view.GameBlockContainer
import com.locker.tictactoe.presentation.compose.screens.game.view.GameCell
import com.locker.tictactoe.presentation.compose.screens.game.view.PlayerTurnIconWithText
import com.locker.tictactoe.presentation.compose.screens.game.viewmodel.GameViewModel
import com.locker.tictactoe.presentation.model.BoardState
import com.locker.tictactoe.presentation.model.CellState
import com.locker.tictactoe.presentation.model.Player
import com.locker.tictactoe.presentation.theme.AccentColor
import com.locker.tictactoe.presentation.theme.AdditionalColor
import com.locker.tictactoe.presentation.theme.DefaultShape
import com.locker.tictactoe.presentation.theme.DefaultStrokeSize
import com.locker.tictactoe.presentation.theme.FADE_WIN_BLOCK_TIME
import com.locker.tictactoe.presentation.theme.HalfAlpha
import com.locker.tictactoe.presentation.theme.LowAlpha
import com.locker.tictactoe.presentation.theme.NEXT_GAME_SCREEN_CHANGE_DELAY_DURATION
import com.locker.tictactoe.presentation.theme.SlimStrokeSize
import com.locker.tictactoe.presentation.theme.Space8
import com.locker.tictactoe.presentation.theme.SubFieldLinePercent
import com.locker.tictactoe.presentation.util.GameBlock
import com.locker.tictactoe.presentation.util.GameField
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import org.koin.compose.koinInject

@Composable
fun GameScreen(
    onInitGameScreen: () -> Unit,
    onEndGameAction: (Player?) -> Unit,
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel = koinInject()
) {
    var isFirstLaunch by rememberSaveable { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        if (isFirstLaunch) {
            isFirstLaunch = false
            onInitGameScreen()
        }
    }
    GameField(
        field = gameViewModel.field,
        gameActivePlayer = gameViewModel.activePlayer,
        onEndGameAction = onEndGameAction,
        onBlockClick = gameViewModel::setFieldState,
        modifier = modifier
    )
}

@Composable
fun GameField(
    field: GameField,
    gameActivePlayer: StateFlow<Player>,
    onEndGameAction: (Player?) -> Unit,
    onBlockClick: (Int, Int, Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var winScreenAlpha by remember { mutableFloatStateOf(1f) }
    val winAlpha by animateFloatAsState(
        targetValue = winScreenAlpha,
        label = stringResource(id = R.string.winner_animate_alpha),
        animationSpec = tween(
            durationMillis = FADE_WIN_BLOCK_TIME,
            delayMillis = NEXT_GAME_SCREEN_CHANGE_DELAY_DURATION
        )
    )

    val boardState by field.winState.collectAsState()
    LaunchedEffect(key1 = boardState) {
        if (boardState != BoardState.InProgress) {
            delay(NEXT_GAME_SCREEN_CHANGE_DELAY_DURATION.toLong())
            winScreenAlpha = 0f
            onEndGameAction(((boardState as? BoardState.Winner)?.winner as? CellState.Occupied)?.player)
        }
    }


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        PlayerTurnIconWithText(
            player = gameActivePlayer,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Space8)
        )

        GameBlockContainer(
            dimensionSize = field.dimensionSize,
            border = Border(DefaultStrokeSize, AccentColor),
            boardState = boardState,
            showAlphaAnimation = false,
            showGameCell = false,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .alpha(winAlpha)
        ) { i, j ->
            GameFieldBlock(
                block = field[i, j],
                onCellClick = { blockRow, blockColumn ->
                    onBlockClick(i, j, blockRow, blockColumn)
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Space8)
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
    val isActive by block.isActive.collectAsState()
    val winState by block.winState.collectAsState()
    GameBlockContainer(
        dimensionSize = block.dimensionSize,
        border = Border(SlimStrokeSize, AdditionalColor.copy(HalfAlpha), SubFieldLinePercent),
        boardState = winState,
        modifier = modifier
            .clip(DefaultShape)
            .background(if (isActive) AccentColor.copy(LowAlpha) else Color.Transparent)
    ) { i, j ->
        val cell by block.getCellFlow(i, j).collectAsState()
        GameCell(
            state = cell,
            onCellClick = {
                onCellClick(i, j)
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
