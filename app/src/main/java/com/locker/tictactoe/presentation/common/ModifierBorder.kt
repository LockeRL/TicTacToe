package com.locker.tictactoe.presentation.common

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp

data class Border(val strokeWidth: Dp, val color: Color, val percentage: Float = 1f)

@Stable
fun Modifier.border(
    start: Border? = null,
    top: Border? = null,
    end: Border? = null,
    bottom: Border? = null
): Modifier =
    drawBehind {
        start?.let {
            drawStartBorder(it, shareTop = top != null, shareBottom = bottom != null, percentage = it.percentage)
        }
        top?.let {
            drawTopBorder(it, shareStart = start != null, shareEnd = end != null, percentage = it.percentage)
        }
        end?.let {
            drawEndBorder(it, shareTop = top != null, shareBottom = bottom != null, percentage = it.percentage)
        }
        bottom?.let {
            drawBottomBorder(border = it, shareStart = start != null, shareEnd = end != null, percentage = it.percentage)
        }
    }

private fun DrawScope.drawTopBorder(
    border: Border,
    shareStart: Boolean = true,
    shareEnd: Boolean = true,
    percentage: Float = 1f
) {
    val strokeWidthPx = border.strokeWidth.toPx()
    if (strokeWidthPx == 0f) return
    if (percentage > 1f || percentage < 0f) return
    drawPath(
        Path().apply {
            val width = size.width
            val start = width * (1f - percentage) / 2
            val end = start + width * percentage
            moveTo(start, 0f)
            lineTo(start + if (shareStart) strokeWidthPx else 0f, strokeWidthPx)
            lineTo( end - if (shareEnd) strokeWidthPx else 0f, strokeWidthPx)
            lineTo(end, 0f)
            close()
        },
        color = border.color
    )
}

private fun DrawScope.drawBottomBorder(
    border: Border,
    shareStart: Boolean,
    shareEnd: Boolean,
    percentage: Float = 1f
) {
    val strokeWidthPx = border.strokeWidth.toPx()
    if (strokeWidthPx == 0f) return
    if (percentage > 1f || percentage < 0f) return
    drawPath(
        Path().apply {
            val width = size.width
            val start = width * (1f - percentage) / 2
            val end = start + width * percentage
            val height = size.height
            moveTo(start, height)
            lineTo(start + if (shareStart) strokeWidthPx else 0f, height - strokeWidthPx)
            lineTo(end - if (shareEnd) strokeWidthPx else 0f, height - strokeWidthPx)
            lineTo(end, height)
            close()
        },
        color = border.color
    )
}

private fun DrawScope.drawStartBorder(
    border: Border,
    shareTop: Boolean = true,
    shareBottom: Boolean = true,
    percentage: Float = 1f
) {
    val strokeWidthPx = border.strokeWidth.toPx()
    if (strokeWidthPx == 0f) return
    if (percentage > 1f || percentage < 0f) return
    drawPath(
        Path().apply {
            val height = size.height
            val start = height * (1f - percentage) / 2
            val end = start + height * percentage
            moveTo(0f, start)
            lineTo(strokeWidthPx, start + if (shareTop) strokeWidthPx else 0f)
            lineTo(strokeWidthPx, end - if (shareBottom) strokeWidthPx else 0f)
            lineTo(0f, end)
            close()
        },
        color = border.color
    )
}

private fun DrawScope.drawEndBorder(
    border: Border,
    shareTop: Boolean = true,
    shareBottom: Boolean = true,
    percentage: Float = 1f
) {
    val strokeWidthPx = border.strokeWidth.toPx()
    if (strokeWidthPx == 0f) return
    if (percentage > 1f || percentage < 0f) return
    drawPath(
        Path().apply {
            val width = size.width
            val height = size.height
            val start = height * (1f - percentage) / 2
            val end = start + height * percentage
            moveTo(width, start)
            lineTo(width - strokeWidthPx, start + if (shareTop) strokeWidthPx else 0f)
            lineTo(width - strokeWidthPx, end - if (shareBottom) strokeWidthPx else 0f)
            lineTo(width, end)
            close()
        },
        color = border.color
    )
}
