package com.locker.tictactoe.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.locker.feature.colorpicker.view.ColorsViewModel
import com.locker.feature.colorpicker.view.PalettePicker

@Composable
fun TopAppBar(
    colorsViewModel: ColorsViewModel,
    modifier: Modifier = Modifier,
    navigationContent: @Composable () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = modifier
    ) {
        navigationContent()

        PalettePicker(
            colorsViewModel = colorsViewModel,
            modifier = Modifier.weight(1f)
        )
    }
}
