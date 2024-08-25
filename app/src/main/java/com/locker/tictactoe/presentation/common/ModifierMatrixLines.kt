package com.locker.tictactoe.presentation.common

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.sqrt

private val DIAGONAL_DIFF_COEFFICIENT = sqrt(2f)

fun Modifier.matrixLine(
    border: Border,
    dimensionSize: Int,
    column: Int? = null,
    row: Int? = null,
    sideDiagonal: Boolean = false,
    mainDiagonal: Boolean = false
): Modifier =
    drawBehind {
        row?.let {
            drawRowLine(border, dimensionSize, it, border.percentage)
        }

        column?.let {
            drawColumnLine(border, dimensionSize, it, border.percentage)
        }

        if (sideDiagonal)
            drawSideDiagonal(border, border.percentage)

        if (mainDiagonal)
            drawMainDiagonal(border, border.percentage)
    }


private fun DrawScope.drawRowLine(
    border: Border,
    dimensionSize: Int,
    row: Int,
    percentage: Float = 1f
) {
    val strokeWidthPx = border.strokeWidth.toPx()
    if (strokeWidthPx == 0f) return
    if (row < 0 || row >= dimensionSize) return
    if (percentage > 1f || percentage < 0f) return
    drawPath(
        Path().apply {
            val width = size.width
            val start = width * (1f - percentage) / 2
            val end = start + width * percentage

            val height = size.height
            val blockHeight = height / dimensionSize
            val curHeight = blockHeight * (row + 0.5f) - strokeWidthPx / 2
            moveTo(start, curHeight)
            lineTo(start, curHeight + strokeWidthPx)
            lineTo(end, curHeight + strokeWidthPx)
            lineTo(end, curHeight)
            close()
        },
        color = border.color
    )
}

private fun DrawScope.drawColumnLine(
    border: Border,
    dimensionSize: Int,
    row: Int,
    percentage: Float = 1f
) {
    val strokeWidthPx = border.strokeWidth.toPx()
    if (strokeWidthPx == 0f) return
    if (row < 0 || row >= dimensionSize) return
    if (percentage > 1f || percentage < 0f) return
    drawPath(
        Path().apply {
            val height = size.height
            val start = height * (1f - percentage) / 2
            val end = start + height * percentage

            val width = size.width
            val blockWidth = width / dimensionSize
            val curWidth = blockWidth * (row + 0.5f) - strokeWidthPx / 2
            moveTo(curWidth, start)
            lineTo(curWidth + strokeWidthPx, start)
            lineTo(curWidth +strokeWidthPx, end)
            lineTo(curWidth, end)
            close()
        },
        color = border.color
    )
}

private fun DrawScope.drawMainDiagonal(
    border: Border,
    percentage: Float = 1f
) {
    val strokeWidthPx = border.strokeWidth.toPx() / DIAGONAL_DIFF_COEFFICIENT
    if (strokeWidthPx == 0f) return
    if (percentage > 1f || percentage < 0f) return
    drawPath(
        Path().apply {
            val height = size.height
            val startHeight = height * (1f - percentage) / 2
            val endHeight = startHeight + height * percentage

            val width = size.width
            val startWidth = width * (1f - percentage) / 2
            val endWidth = startWidth + width * percentage

            moveTo(startWidth, startHeight)
            lineTo(startWidth + strokeWidthPx, startHeight)
            lineTo(endWidth, endHeight - strokeWidthPx)
            lineTo(endWidth, endHeight)
            lineTo(endWidth - strokeWidthPx, endHeight)
            lineTo(startWidth, startHeight + strokeWidthPx)
            close()
        },
        color = border.color
    )
}

private fun DrawScope.drawSideDiagonal(
    border: Border,
    percentage: Float = 1f
) {
    val strokeWidthPx = border.strokeWidth.toPx()  / DIAGONAL_DIFF_COEFFICIENT
    if (strokeWidthPx == 0f) return
    if (percentage > 1f || percentage < 0f) return

    drawPath(
        Path().apply {
            val height = size.height
            val startHeight = height * (1f - percentage) / 2
            val endHeight = startHeight + height * percentage

            val width = size.width
            val startWidth = width * (1f - percentage) / 2
            val endWidth = startWidth + width * percentage

            moveTo(endWidth, startHeight)
            lineTo(endWidth, startHeight + strokeWidthPx)
            lineTo(startWidth + strokeWidthPx, endHeight)
            lineTo(startWidth, endHeight)
            lineTo(startWidth, endHeight - strokeWidthPx)
            lineTo(endWidth - strokeWidthPx, startHeight)
            close()
        },
        color = border.color
    )
}
