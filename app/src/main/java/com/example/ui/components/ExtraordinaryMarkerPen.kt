package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate

data class InkSparkle(
    val x: Float,
    val y: Float,
    val radius: Float,
    val color: Color,
    val alpha: Float,
    val isStar: Boolean = false
)

@Composable
fun ExtraordinaryMarkerPen(
    touchPos: Offset?,
    penColor: Color,
    sparkles: List<InkSparkle>,
    isRubbing: Boolean = false,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "marker_sheen")
    val glowPulse by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(450, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_pulse"
    )

    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // 1. Draw all emitted floating ink sparkles & stars
            for (sparkle in sparkles) {
                if (sparkle.isStar) {
                    drawSparkleStar(
                        center = Offset(sparkle.x, sparkle.y),
                        size = sparkle.radius * 2.2f,
                        color = sparkle.color.copy(alpha = sparkle.alpha)
                    )
                } else {
                    drawCircle(
                        color = sparkle.color.copy(alpha = sparkle.alpha),
                        radius = sparkle.radius,
                        center = Offset(sparkle.x, sparkle.y)
                    )
                    // White shine center
                    drawCircle(
                        color = Color.White.copy(alpha = sparkle.alpha * 0.8f),
                        radius = sparkle.radius * 0.4f,
                        center = Offset(sparkle.x - sparkle.radius * 0.2f, sparkle.y - sparkle.radius * 0.2f)
                    )
                }
            }

            // 2. Draw the 3D marker pen if touching
            if (touchPos != null) {
                // Soft glow halo on canvas under the nib
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            penColor.copy(alpha = if (isRubbing) 0.55f * glowPulse else 0.35f),
                            Color.Transparent
                        ),
                        center = touchPos,
                        radius = if (isRubbing) 32f else 22f
                    ),
                    radius = if (isRubbing) 32f else 22f,
                    center = touchPos
                )

                drawRendered3DMarker(
                    tipPos = touchPos,
                    penColor = penColor,
                    isRubbing = isRubbing
                )
            }
        }
    }
}

private fun DrawScope.drawSparkleStar(center: Offset, size: Float, color: Color) {
    val half = size / 2f
    val starPath = Path().apply {
        moveTo(center.x, center.y - half)
        lineTo(center.x + half * 0.3f, center.y - half * 0.3f)
        lineTo(center.x + half, center.y)
        lineTo(center.x + half * 0.3f, center.y + half * 0.3f)
        lineTo(center.x, center.y + half)
        lineTo(center.x - half * 0.3f, center.y + half * 0.3f)
        lineTo(center.x - half, center.y)
        lineTo(center.x - half * 0.3f, center.y - half * 0.3f)
        close()
    }
    drawPath(path = starPath, color = color)
}

private fun DrawScope.drawRendered3DMarker(
    tipPos: Offset,
    penColor: Color,
    isRubbing: Boolean
) {
    val tiltAngle = if (isRubbing) -40f else -38f

    rotate(degrees = tiltAngle, pivot = tipPos) {
        translate(left = tipPos.x, top = tipPos.y) {
            // Drop shadow cast behind marker
            val shadowPath = Path().apply {
                moveTo(10f, 15f)
                lineTo(38f, 15f)
                lineTo(44f, 240f)
                lineTo(6f, 240f)
                close()
            }
            drawPath(shadowPath, color = Color(0x35000000))

            // 1. Chisel Nib (Felt Tip) anchored at (0, 0)
            val nibPath = Path().apply {
                moveTo(0f, 0f)       // Touch contact point
                lineTo(-15f, 44f)    // Left chisel wing
                lineTo(15f, 44f)     // Right chisel wing
                close()
            }
            drawPath(nibPath, color = penColor)

            // Darker facet of chisel edge for 3D depth
            val chiselFacet = Path().apply {
                moveTo(0f, 0f)
                lineTo(15f, 44f)
                lineTo(2f, 44f)
                close()
            }
            drawPath(chiselFacet, color = Color(0x38000000))

            // Wet felt tip sheen line
            drawLine(
                color = Color.White.copy(alpha = 0.5f),
                start = Offset(-4f, 8f),
                end = Offset(-8f, 40f),
                strokeWidth = 2.5f
            )

            // 2. Luxury Golden Ferrule Rings
            val ferruleBase = Path().apply {
                moveTo(-17f, 44f)
                lineTo(17f, 44f)
                lineTo(20f, 70f)
                lineTo(-20f, 70f)
                close()
            }
            drawPath(ferruleBase, color = Color(0xFFD4AF37)) // Gold metallic

            // Ferrule highlight
            drawLine(
                color = Color(0xFFFFF2A1),
                start = Offset(-4f, 44f),
                end = Offset(-4f, 70f),
                strokeWidth = 3f
            )

            // Chrome ring divider
            val chromeRing = Path().apply {
                moveTo(-18f, 55f)
                lineTo(18f, 55f)
                lineTo(18f, 59f)
                lineTo(-18f, 59f)
                close()
            }
            drawPath(chromeRing, color = Color(0xFFF1F5F9))

            // 3. Pearlescent White Marker Barrel
            val barrelPath = Path().apply {
                moveTo(-23f, 70f)
                lineTo(23f, 70f)
                lineTo(23f, 230f)
                lineTo(-23f, 230f)
                close()
            }
            drawPath(barrelPath, color = Color(0xFFFAFAFC))

            // 3D Cylinder shadow (right side)
            val barrelShadow = Path().apply {
                moveTo(11f, 70f)
                lineTo(23f, 70f)
                lineTo(23f, 230f)
                lineTo(11f, 230f)
                close()
            }
            drawPath(barrelShadow, color = Color(0x18000000))

            // 3D Cylinder gloss streak (left side)
            drawLine(
                color = Color.White.copy(alpha = 0.95f),
                start = Offset(-13f, 74f),
                end = Offset(-13f, 226f),
                strokeWidth = 4.5f
            )

            // 4. Color Identification Band
            val colorBand = Path().apply {
                moveTo(-23f, 105f)
                lineTo(23f, 105f)
                lineTo(23f, 140f)
                lineTo(-23f, 140f)
                close()
            }
            drawPath(colorBand, color = penColor)

            // Gold pinstripes bordering the color band
            drawLine(color = Color(0xFFD4AF37), start = Offset(-23f, 105f), end = Offset(23f, 105f), strokeWidth = 2f)
            drawLine(color = Color(0xFFD4AF37), start = Offset(-23f, 140f), end = Offset(23f, 140f), strokeWidth = 2f)

            // 5. Crown End Cap
            val capPath = Path().apply {
                moveTo(-21f, 230f)
                lineTo(21f, 230f)
                lineTo(17f, 248f)
                lineTo(-17f, 248f)
                close()
            }
            drawPath(capPath, color = penColor)

            // Cap rim gold ring
            drawLine(color = Color(0xFFD4AF37), start = Offset(-21f, 230f), end = Offset(21f, 230f), strokeWidth = 2f)
        }
    }
}
