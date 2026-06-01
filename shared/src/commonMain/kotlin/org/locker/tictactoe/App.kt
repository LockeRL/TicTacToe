package org.locker.tictactoe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.locker.tictactoe.presentation.ColorsViewModel
import org.locker.tictactoe.presentation.common.TopAppBar
import org.locker.tictactoe.presentation.compose.navigation.navhost.MainNavHost
import org.locker.tictactoe.presentation.compose.navigation.viewmodel.NavigationViewModel
import org.locker.tictactoe.presentation.theme.Space16
import org.locker.tictactoe.presentation.theme.TicTacToeTheme
import org.koin.compose.koinInject
import org.locker.tictactoe.presentation.util.navigationBarHeightDp
import org.locker.tictactoe.presentation.util.statusBarHeightDp


@Composable
@Preview
fun App(
    navViewModel: NavigationViewModel = koinInject(),
    colorsViewModel: ColorsViewModel = koinInject(),
) {
    val colors by colorsViewModel.appColors.collectAsState()
    TicTacToeTheme(
        appColors = colors
    ) {
        val backgroundColor = MaterialTheme.colorScheme.background
        Scaffold(
            topBar = {
                val backButton by navViewModel.backButton.collectAsState()
                TopAppBar(
                    button = backButton,
                    colorsViewModel = colorsViewModel,
                    modifier = Modifier
                        .background(backgroundColor)
                        .fillMaxWidth()
                )
            },
            containerColor = backgroundColor,
            modifier = Modifier
                .background(backgroundColor)
                .fillMaxSize()
                .padding(
                    top = statusBarHeightDp(),
                    bottom = navigationBarHeightDp()
                )
                .padding(all = Space16)
        ) { innerPadding ->
            MainNavHost(
                innerPadding = innerPadding,
                updateTopAppBar = navViewModel::setBackButton,
                modifier = Modifier
                    .background(backgroundColor)
                    .fillMaxSize()
            )
        }
    }
}
