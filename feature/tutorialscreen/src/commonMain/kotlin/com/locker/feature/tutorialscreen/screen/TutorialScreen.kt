package com.locker.feature.tutorialscreen.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.locker.feature.component.TicTacToeButton
import com.locker.feature.core.screen.LocalFireEvent
import com.locker.feature.core.screen.ProvideScreenEvents
import com.locker.feature.core.theme.TicTacToeTheme
import com.locker.feature.tutorialscreen.screen.event.TutorialBackEvent
import com.locker.feature.tutorialscreen.screen.model.TutorialPageType
import com.locker.feature.tutorialscreen.screen.model.TutorialScreenState
import com.locker.feature.tutorialscreen.screen.view.TutorialDrawInBlock
import com.locker.feature.tutorialscreen.screen.view.TutorialFreeMove
import com.locker.feature.tutorialscreen.screen.view.TutorialHowToWin
import com.locker.feature.tutorialscreen.screen.view.TutorialNextMove
import com.locker.feature.tutorialscreen.screen.view.TutorialWinBlock
import org.koin.compose.koinInject
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
fun TutorialScreen(
	viewModel: TutorialScreenViewModel = koinInject(),
	modifier: Modifier = Modifier
) {
	ProvideScreenEvents(
		viewModel = viewModel
	) { viewModel ->
		val state = viewModel.screenState.collectAsState()

		TutorialScreenContent(
			screenState = state,
			modifier = modifier
		)
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TutorialScreenContent(
	screenState: State<TutorialScreenState>,
	modifier: Modifier = Modifier
) {
	val fireEvent = LocalFireEvent.current
	val state = screenState.value
	val pagerState = rememberPagerState { state.pages.size }
	val scope = rememberCoroutineScope()

	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		modifier = modifier
			.fillMaxSize()
			.padding(16.dp),
	) {
		Text(
			text = state.title,
			style = TicTacToeTheme.typography.headlineMedium,
			color = TicTacToeTheme.colors.accentContainer,
			modifier = Modifier.padding(vertical = 24.dp)
		)

		HorizontalPager(
			state = pagerState,
			modifier = Modifier.weight(1f).fillMaxWidth()
		) { pageIndex ->
			val page = state.pages.getOrNull(pageIndex)
			if (page != null) {
				TutorialPageContent(
					pageType = page
				)
			}
		}

		Spacer(modifier = Modifier.height(16.dp))

		PageIndicator(
			pageCount = state.pages.size,
			currentPage = pagerState.currentPage
		)

		Spacer(modifier = Modifier.height(24.dp))

		TicTacToeButton(
			text = if (pagerState.currentPage == state.pages.size - 1) state.gotIt else state.next,
			onClick = {
				if (pagerState.currentPage < state.pages.size - 1) {
					if (!pagerState.isScrollInProgress) {
						scope.launch {
							pagerState.animateScrollToPage(pagerState.currentPage + 1)
						}
					}
				} else {
					fireEvent(TutorialBackEvent)
				}
			},
			modifier = Modifier
				.fillMaxWidth(0.7f)
				.padding(bottom = 32.dp)
		)
	}
}

@Composable
private fun PageIndicator(
	pageCount: Int,
	currentPage: Int,
	modifier: Modifier = Modifier
) {
	val colors = TicTacToeTheme.colors
	Row(
		horizontalArrangement = Arrangement.spacedBy(8.dp),
		verticalAlignment = Alignment.CenterVertically,
		modifier = modifier
	) {
		repeat(pageCount) { index ->
			val isSelected = index == currentPage
			Box(
				modifier = Modifier
					.size(if (isSelected) 10.dp else 8.dp)
					.clip(CircleShape)
					.background(color = if (isSelected) colors.accent else colors.accent.copy(alpha = 0.3f))
			)
		}
	}
}

@Composable
fun TutorialPageContent(
	pageType: TutorialPageType,
	modifier: Modifier = Modifier
) {
	val colors = TicTacToeTheme.colors
	val typography = TicTacToeTheme.typography

	Column(
		modifier = modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(24.dp)
	) {
		Text(
			text = stringResource(pageType.title),
			style = typography.headlineSmall,
			color = colors.accent,
			textAlign = TextAlign.Center
		)

		Text(
			text = stringResource(pageType.description),
			style = typography.bodyLarge,
			color = colors.accentContainer.copy(alpha = 0.8f),
			textAlign = TextAlign.Center,
			modifier = Modifier
				.padding(horizontal = 16.dp)
		)

		Box(
			modifier = Modifier
				.fillMaxWidth(),
			contentAlignment = Alignment.Center
		) {
			when (pageType) {
				TutorialPageType.NEXT_MOVE -> TutorialNextMove()
				TutorialPageType.FREE_MOVE -> TutorialFreeMove()
				TutorialPageType.WIN_BLOCK -> TutorialWinBlock()
				TutorialPageType.DRAW_IN_BLOCK -> TutorialDrawInBlock()
				TutorialPageType.HOW_TO_WIN -> TutorialHowToWin()
			}
		}
	}
}
