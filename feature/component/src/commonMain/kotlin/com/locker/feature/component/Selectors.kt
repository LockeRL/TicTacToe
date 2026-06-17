package com.locker.feature.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.locker.feature.core.theme.TicTacToeTheme
import com.locker.resources.Res
import com.locker.resources.ic_circle
import com.locker.resources.ic_cross
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun DifficultySelector(
    selectedDifficulty: String,
    onDifficultySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val difficulties = listOf("easy", "medium", "hard")
    val colors = TicTacToeTheme.colors
    val selectedIndex = difficulties.indexOf(selectedDifficulty)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArc(
                color = colors.accent.copy(alpha = 0.1f),
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = true,
                size = Size(size.width, size.height * 2),
                topLeft = Offset(0f, 0f)
            )
        }

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            val width = maxWidth
            val itemWidth = width / 3
            val indicatorOffset by animateFloatAsState(
                targetValue = selectedIndex * itemWidth.value,
                animationSpec = tween(300)
            )

            // Animated indicator
            Box(
                modifier = Modifier
                    .offset(x = indicatorOffset.dp)
                    .size(width = itemWidth, height = 50.dp)
                    .padding(4.dp)
                    .background(colors.accent, RoundedCornerShape(16.dp))
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                difficulties.forEach { difficulty ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .clickable { onDifficultySelected(difficulty) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = difficulty.replaceFirstChar { it.uppercase() },
                            color = if (selectedDifficulty == difficulty) Color.White else colors.accent,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SymbolSelector(
    selectedSymbol: String,
    onSymbolSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        SymbolItem(
            iconRes = Res.drawable.ic_cross,
            isSelected = selectedSymbol == "cross",
            onClick = { onSymbolSelected("cross") }
        )
        SymbolItem(
            iconRes = Res.drawable.ic_circle,
            isSelected = selectedSymbol == "circle",
            onClick = { onSymbolSelected("circle") }
        )
    }
}

@Composable
fun SymbolItem(
    iconRes: DrawableResource,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val colors = TicTacToeTheme.colors
    Box(
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(if (isSelected) colors.accent else colors.accent.copy(alpha = 0.1f))
            .clickable { onClick() }
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = if (isSelected) Color.White else colors.accent,
            modifier = Modifier.fillMaxSize()
        )
    }
}
