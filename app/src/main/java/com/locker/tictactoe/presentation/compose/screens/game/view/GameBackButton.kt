package com.locker.tictactoe.presentation.compose.screens.game.view

import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.locker.tictactoe.R
import com.locker.tictactoe.presentation.common.TicTacToeIconButton
import com.locker.tictactoe.presentation.model.BackButton
import com.locker.tictactoe.presentation.theme.Size24
import com.locker.tictactoe.presentation.theme.Size40

fun gameBackButton(
    onClick: () -> Unit,
): BackButton = BackButton {
    TicTacToeIconButton(
        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
        onClick = onClick,
        contentColor = MaterialTheme.colors.secondaryVariant,
        contentDescription = stringResource(id = R.string.ic_back_arrow_name),
        iconModifier = Modifier.size(Size24),
        modifier = Modifier.size(Size40)
    )
}
