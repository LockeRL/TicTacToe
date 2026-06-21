package com.locker.feature.tutorialscreen.screen.factory

import com.locker.feature.tutorialscreen.Res
import com.locker.feature.tutorialscreen.screen.model.TutorialPageType
import com.locker.feature.tutorialscreen.screen.model.TutorialScreenState
import com.locker.feature.tutorialscreen.tutorial_got_it
import com.locker.feature.tutorialscreen.tutorial_next
import com.locker.feature.tutorialscreen.tutorial_title
import org.jetbrains.compose.resources.getString

object TutorialScreenStateFactory {
	suspend fun create(): TutorialScreenState = TutorialScreenState(
		title = getString(Res.string.tutorial_title),
		next = getString(Res.string.tutorial_next),
		gotIt = getString(Res.string.tutorial_got_it),
		pages = listOf(
			TutorialPageType.NEXT_MOVE,
			TutorialPageType.WIN_BLOCK,
			TutorialPageType.DRAW_IN_BLOCK,
			TutorialPageType.FREE_MOVE,
			TutorialPageType.HOW_TO_WIN,
		)
	)
}
