package com.locker.tictactoe.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.locker.tictactoe.presentation.common.TopAppBar
import com.locker.tictactoe.presentation.compose.navigation.navhost.MainNavHost
import com.locker.tictactoe.presentation.compose.navigation.viewmodel.NavigationViewModel
import com.locker.tictactoe.presentation.theme.Space16
import com.locker.tictactoe.presentation.theme.TicTacToeTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val navViewModel: NavigationViewModel by viewModel()
    private val colorsViewModel: ColorsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val colors by colorsViewModel.appColors.collectAsState()
            TicTacToeTheme(
                appColors = colors
            ) {
                val backgroundColor = MaterialTheme.colors.background
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
                    backgroundColor = backgroundColor,
                    modifier = Modifier
                        .background(backgroundColor)
                        .fillMaxSize()
                        .padding(Space16)
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
    }
}
