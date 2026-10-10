package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate

/**
 * 3D-styled Marker Pen whose chisel nib is pinned directly at the user's touch coordinate.
 * The body angles away at 40 degrees so the drawing nib is visible and natural.
 */
@Composable
fun MarkerPenOverlay(
    touchPos: Offset?,
    penColor: Color,
    modifier: Modifier = Modifier
) {
    if (touchPos == null) return

    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawMarkerPen(
                tipPos = touchPos,
                penColor = penColor
            )
        }
    }
}

private fun DrawScope.drawMarkerPen(
    tipPos: Offset,
    penColor: Color
) {
    // We rotate around tipPos so that (0,0) in local coords is the sharp chisel nib tip
    rotate(degrees = -38f, pivot = tipPos) {
        translate(left = tipPos.x, top = tipPos.y) {
            // Shadow behind pen body
            val shadowPath = Path().apply {
                moveTo(8f, 20f)
                lineTo(34f, 20f)
                lineTo(38f, 230f)
                lineTo(4f, 230f)
                close()
            }
            drawPath(shadowPath, color = Color(0x33000000))

            // 1. Chisel Marker Nib (Touching point is at (0, 0))
            val nibPath = Path().apply {
                moveTo(0f, 0f)       // Very point touching paper
                lineTo(-14f, 42f)     // Left chisel edge
                lineTo(14f, 42f)      // Right chisel edge
                close()
            }
            drawPath(nibPath, color = penColor)

            // Nib chisel dark angle facet
            val nibFacet = Path().apply {
                moveTo(0f, 0f)
                lineTo(14f, 42f)
                lineTo(0f, 42f)
                close()
            }
            drawPath(nibFacet, color = Color(0x33000000))

            // 2. Collar / Ferrule (Metallic band)
            val collarPath = Path().apply {
                moveTo(-16f, 42f)
                lineTo(16f, 42f)
                lineTo(19f, 66f)
                lineTo(-19f, 66f)
                close()
            }
            drawPath(collarPath, color = Color(0xFFCBD5E1))

            // Collar highlight
            drawLine(
                color = Color.White.copy(alpha = 0.8f),
                start = Offset(-4f, 42f),
                end = Offset(-4f, 66f),
                strokeWidth = 3f
            )

            // 3. Pen Barrel (White rounded body)
            val barrelPath = Path().apply {
                moveTo(-22f, 66f)
                lineTo(22f, 66f)
                lineTo(22f, 220f)
                lineTo(-22f, 220f)
                close()
            }
            drawPath(barrelPath, color = Color(0xFFF8FAFC))

            // Barrel 3D shading (darker right edge)
            val barrelShade = Path().apply {
                moveTo(10f, 66f)
                lineTo(22f, 66f)
                lineTo(22f, 220f)
                lineTo(10f, 220f)
                close()
            }
            drawPath(barrelShade, color = Color(0x15000000))

            // Barrel glossy highlight (left edge)
            drawLine(
                color = Color.White.copy(alpha = 0.9f),
                start = Offset(-12f, 70f),
                end = Offset(-12f, 215f),
                strokeWidth = 4f
            )

            // 4. Color indicator band on pen barrel
            val bandPath = Path().apply {
                moveTo(-22f, 95f)
                lineTo(22f, 95f)
                lineTo(22f, 125f)
                lineTo(-22f, 125f)
                close()
            }
            drawPath(bandPath, color = penColor)

            // 5. Pen Top Cap
            val capPath = Path().apply {
                moveTo(-20f, 220f)
                lineTo(20f, 220f)
                lineTo(16f, 238f)
                lineTo(-16f, 238f)
                close()
            }
            drawPath(capPath, color = penColor)
        }
    }
}
