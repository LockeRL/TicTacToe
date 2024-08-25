package com.locker.tictactoe.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.locker.tictactoe.presentation.compose.screens.main.view.PalettePicker
import com.locker.tictactoe.presentation.model.AppColors
import com.locker.tictactoe.presentation.model.BackButton
import com.locker.tictactoe.presentation.theme.Size4

@Composable
fun TopAppBar(
    button: BackButton?,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.size(Size4))

        button?.backButton?.invoke()

        PalettePicker(
            colorsList = listOf(AppColors(), AppColors()),
            modifier = Modifier.weight(1f)
        )
    }
}
