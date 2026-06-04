package com.locker.feature.mainscreen.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.locker.core.navigation.Navigator
import com.locker.core.navigation.keys.GameScreenNavKey
import org.koin.compose.koinInject

@Composable
fun MainScreen() {
    val navigator: Navigator = koinInject()
    
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = { navigator.navigate(GameScreenNavKey) }) {
            Text(text = "Go to Game")
        }
    }
}
