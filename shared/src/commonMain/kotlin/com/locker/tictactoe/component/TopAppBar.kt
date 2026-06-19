package com.locker.tictactoe.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.locker.feature.colorpicker.view.ColorsViewModel
import com.locker.feature.colorpicker.view.PalettePicker
import com.locker.feature.component.TicTacToeIconButton
import com.locker.feature.core.theme.TicTacToeTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun TopAppBar(
    colorsViewModel: ColorsViewModel,
    iconRes: DrawableResource,
    onIconClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier
) {
    val colors = TicTacToeTheme.colors
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = modifier
    ) {
        TicTacToeIconButton(
            icon = painterResource(iconRes),
            shape = CircleShape,
            onClick = onIconClick,
            contentDescription = "TopAppBarIcon",
            contentColor = colors.additionalContainer,
            iconModifier = iconModifier,
            modifier = Modifier.size(48.dp),
        )

        Spacer(modifier = Modifier.width(8.dp))

        PalettePicker(
            colorsViewModel = colorsViewModel,
            modifier = Modifier.weight(1f)
        )
    }
}
