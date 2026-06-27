package com.locker.feature.tutorialscreen.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
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
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TutorialScreen(
	viewModel: TutorialScreenViewModel = koinViewModel(),
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
	val colors = TicTacToeTheme.colors
	val typography = TicTacToeTheme.typography
	val fireEvent = LocalFireEvent.current
	val state = screenState.value
	val pagerState = rememberPagerState { state.pages.size }
	val scope = rememberCoroutineScope()

	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(24.dp),
		modifier = modifier
			.fillMaxSize()
			.padding(16.dp),
	) {
		Text(
			text = state.title,
			style = typography.headlineMedium,
			color = colors.accentContainer,
		)

		Box(
			modifier = Modifier
			.weight(weight = 1f, fill = false)
		) {
			Box(
				modifier = Modifier
					.fillMaxHeight()
					.width(16.dp)
					.background(
						brush = Brush.horizontalGradient(
							0f to colors.background,
							1f to Color.Transparent
						)
					)
					.align(Alignment.CenterStart)
					.zIndex(2f)
			)

			Box(
				modifier = Modifier
					.fillMaxHeight()
					.width(16.dp)
					.background(
						brush = Brush.horizontalGradient(
							0f to Color.Transparent,
							1f to colors.background
						)
					)
					.align(Alignment.CenterEnd)
					.zIndex(2f)
			)

			HorizontalPager(
				state = pagerState,
				modifier = Modifier
					.fillMaxSize()
					.zIndex(1f)
			) { pageIndex ->
				val page = state.pages.getOrNull(pageIndex)
				if (page != null) {
					TutorialPageContent(
						pageType = page,
						modifier = Modifier
							.fillMaxSize()
					)
				}
			}
		}

		PageIndicator(
			pageCount = state.pages.size,
			currentPage = pagerState.currentPage
		)

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
		modifier = modifier,
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

		val pageModifier = Modifier
			.weight(1f, false)

		when (pageType) {
			TutorialPageType.NEXT_MOVE -> TutorialNextMove(modifier = pageModifier)
			TutorialPageType.FREE_MOVE -> TutorialFreeMove(modifier = pageModifier)
			TutorialPageType.WIN_BLOCK -> TutorialWinBlock(modifier = pageModifier)
			TutorialPageType.DRAW_IN_BLOCK -> TutorialDrawInBlock(modifier = pageModifier)
			TutorialPageType.HOW_TO_WIN -> TutorialHowToWin(modifier = pageModifier)
		}
	}
}
