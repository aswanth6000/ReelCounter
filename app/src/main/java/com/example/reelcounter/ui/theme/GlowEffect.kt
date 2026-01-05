package com.reelcounter.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.reelcounter.ui.theme.BrightGreen

/**
 * Creates a glow effect around a composable using shadow layers.
 */
@Composable
fun GlowCard(
    modifier: Modifier = Modifier,
    glowColor: Color = BrightGreen,
    glowIntensity: Float = 0.3f,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .drawBehind {
                // Outer glow
                drawRoundRect(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            glowColor.copy(alpha = glowIntensity),
                            glowColor.copy(alpha = glowIntensity * 0.5f),
                            Color.Transparent
                        ),
                        radius = size.maxDimension * 1.5f
                    ),
                    style = Stroke(width = 8.dp.toPx())
                )
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        content()
    }
}

/**
 * Adds a glow border effect to any composable.
 */
fun Modifier.glowBorder(
    glowColor: Color = BrightGreen,
    width: Float = 2f,
    intensity: Float = 0.6f
): Modifier = this.drawBehind {
    drawRoundRect(
        brush = Brush.linearGradient(
            colors = listOf(
                glowColor.copy(alpha = intensity),
                glowColor.copy(alpha = intensity * 0.5f),
                glowColor.copy(alpha = intensity)
            )
        ),
        style = Stroke(width = width)
    )
}

