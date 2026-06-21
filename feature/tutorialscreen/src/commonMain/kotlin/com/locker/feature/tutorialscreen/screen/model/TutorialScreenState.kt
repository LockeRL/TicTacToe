package com.locker.feature.tutorialscreen.screen.model

import com.locker.feature.tutorialscreen.Res
import com.locker.feature.tutorialscreen.tutorial_draw_in_block_desc
import com.locker.feature.tutorialscreen.tutorial_draw_in_block_title
import com.locker.feature.tutorialscreen.tutorial_free_move_desc
import com.locker.feature.tutorialscreen.tutorial_free_move_title
import com.locker.feature.tutorialscreen.tutorial_how_to_win_desc
import com.locker.feature.tutorialscreen.tutorial_how_to_win_title
import com.locker.feature.tutorialscreen.tutorial_next_move_desc
import com.locker.feature.tutorialscreen.tutorial_next_move_title
import com.locker.feature.tutorialscreen.tutorial_win_block_desc
import com.locker.feature.tutorialscreen.tutorial_win_block_title
import org.jetbrains.compose.resources.StringResource

data class TutorialScreenState(
    val title: String = "",
    val next: String = "",
    val gotIt: String = "",
    val pages: List<TutorialPageType> = emptyList()
)

enum class TutorialPageType(val title: StringResource, val description: StringResource) {
    NEXT_MOVE(title = Res.string.tutorial_next_move_title, description = Res.string.tutorial_next_move_desc),
    FREE_MOVE(title = Res.string.tutorial_free_move_title, description = Res.string.tutorial_free_move_desc),
    WIN_BLOCK(title = Res.string.tutorial_win_block_title, description = Res.string.tutorial_win_block_desc),
    DRAW_IN_BLOCK(title = Res.string.tutorial_draw_in_block_title, description = Res.string.tutorial_draw_in_block_desc),
    HOW_TO_WIN(title = Res.string.tutorial_how_to_win_title, description = Res.string.tutorial_how_to_win_desc),
}
