package com.locker.feature.gamebotscreen.screen.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.locker.feature.core.theme.Size48
import com.locker.feature.core.theme.TicTacToeTheme
import com.locker.feature.gamebotscreen.screen.model.HeaderState
import org.jetbrains.compose.resources.painterResource

@Composable
fun Header(
    header: HeaderState,
    modifier: Modifier = Modifier
) {
    val colors = TicTacToeTheme.colors
    val typography = TicTacToeTheme.typography

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.height(Size48)
        ) {
            Text(
                text = header.playingAs,
                color = colors.accentContainer,
                style = typography.titleMedium
            )

            if (header.icon != null) {
                Icon(
                    painter = painterResource(header.icon),
                    tint = colors.accentContainer,
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                )
            }
        }

        Text(
            text = header.subtitle,
            color = if (header.isUserTurn) colors.accent else colors.accentContainer,
            style = typography.titleLarge,
            fontSize = 24.sp
        )
    }
}
