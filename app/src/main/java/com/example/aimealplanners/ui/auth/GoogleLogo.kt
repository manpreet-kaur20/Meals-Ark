package com.example.aimealplanners.ui.auth

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun GoogleLogo(modifier: Modifier = Modifier.size(20.dp)) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val strokeWidth = width * 0.2f
        val center = Offset(width / 2f, height / 2f)
        val radius = (width - strokeWidth) / 2f

        // Red arc (top right & top)
        drawArc(
            color = Color(0xFFEA4335),
            startAngle = -45f,
            sweepAngle = 100f,
            useCenter = false,
            style = Stroke(width = strokeWidth),
            size = Size(radius * 2, radius * 2),
            topLeft = Offset(center.x - radius, center.y - radius)
        )

        // Yellow arc (bottom left)
        drawArc(
            color = Color(0xFFFBBC05),
            startAngle = 135f,
            sweepAngle = 75f,
            useCenter = false,
            style = Stroke(width = strokeWidth),
            size = Size(radius * 2, radius * 2),
            topLeft = Offset(center.x - radius, center.y - radius)
        )

        // Green arc (bottom & bottom right)
        drawArc(
            color = Color(0xFF34A853),
            startAngle = 45f,
            sweepAngle = 90f,
            useCenter = false,
            style = Stroke(width = strokeWidth),
            size = Size(radius * 2, radius * 2),
            topLeft = Offset(center.x - radius, center.y - radius)
        )

        // Blue arc & horizontal bar (right)
        drawArc(
            color = Color(0xFF4285F4),
            startAngle = -45f,
            sweepAngle = -90f,
            useCenter = false,
            style = Stroke(width = strokeWidth),
            size = Size(radius * 2, radius * 2),
            topLeft = Offset(center.x - radius, center.y - radius)
        )

        // Blue bar
        drawRect(
            color = Color(0xFF4285F4),
            topLeft = Offset(center.x, center.y - strokeWidth / 2f),
            size = Size(radius, strokeWidth)
        )
    }
}
