package org.locker.tictactoe.presentation.compose.screens.game.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
import org.locker.tictactoe.presentation.common.Border
import org.locker.tictactoe.presentation.compose.screens.game.events.CellClickEvent
import org.locker.tictactoe.presentation.compose.screens.game.view.GameBlockContainer
import org.locker.tictactoe.presentation.compose.screens.game.view.GameCell
import org.locker.tictactoe.presentation.compose.screens.game.view.PlayerTurnIconWithText
import org.locker.tictactoe.presentation.compose.screens.game.viewmodel.GameViewModel
import org.locker.tictactoe.presentation.model.BoardState
import org.locker.tictactoe.presentation.model.CellState
import org.locker.tictactoe.presentation.model.Player
import org.locker.tictactoe.presentation.theme.DefaultShape
import org.locker.tictactoe.presentation.theme.DefaultStrokeSize
import org.locker.tictactoe.presentation.theme.FADE_WIN_BLOCK_TIME
import org.locker.tictactoe.presentation.theme.HALF_ALPHA
import org.locker.tictactoe.presentation.theme.LOW_ALPHA
import org.locker.tictactoe.presentation.theme.NEXT_GAME_SCREEN_CHANGE_DELAY_DURATION
import org.locker.tictactoe.presentation.theme.SlimStrokeSize
import org.locker.tictactoe.presentation.theme.Space8
import org.locker.tictactoe.presentation.theme.SUB_FIELD_LINE_LENGTH_PERCENT
import com.locker.tictactoe.presentation.util.GameBlock
import com.locker.tictactoe.presentation.util.GameField
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import tictactoe.shared.generated.resources.Res
import tictactoe.shared.generated.resources.winner_animate_alpha

@Composable
fun GameScreen(
    onEndGameAction: (Player?) -> Unit,
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel = koinInject()
) {
    var isFirstLaunch by rememberSaveable { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        if (isFirstLaunch)
            isFirstLaunch = false
    }

    GameFieldContent(
        field = gameViewModel.logic.field,
        gameActivePlayer = gameViewModel.logic.activePlayer,
        onEndGameAction = onEndGameAction,
        onBlockClick = gameViewModel::clickCell,
        modifier = modifier
    )
}

@Composable
fun GameFieldContent(
    field: GameField,
    gameActivePlayer: StateFlow<Player>,
    onEndGameAction: (Player?) -> Unit,
    onBlockClick: (CellClickEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    var winScreenAlpha by remember { mutableFloatStateOf(1f) }
    val winAlpha by animateFloatAsState(
        targetValue = winScreenAlpha,
        label = stringResource(Res.string.winner_animate_alpha),
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
            val winner = ((boardState as? BoardState.Winner)?.winner as? CellState.Occupied)?.player
            onEndGameAction(winner)
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
            dimensionSize = field.dimension,
            border = Border(DefaultStrokeSize, colors.primary),
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
                    onBlockClick(CellClickEvent(i, j, blockRow, blockColumn))
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
    val colors = MaterialTheme.colorScheme

    val isActive by block.isActive.collectAsState()
    val winState by block.winState.collectAsState()
    GameBlockContainer(
        dimensionSize = block.dimension,
        border = Border(
	        strokeWidth = SlimStrokeSize,
	        color = colors.secondary.copy(HALF_ALPHA),
	        percentage = SUB_FIELD_LINE_LENGTH_PERCENT
        ),
        boardState = winState,
        modifier = modifier
            .clip(DefaultShape)
            .background(if (isActive) colors.primary.copy(alpha = LOW_ALPHA) else Color.Transparent)
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
