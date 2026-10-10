package com.example.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

enum class ChapterType(val title: String, val subtitle: String, val icon: String) {
    SHAPES("Chapter 1: Shapes", "Geometric & Fun Shapes (8 Levels)", "⭐"),
    FRUITS("Chapter 2: Juicy Fruits", "Fresh & Sweet Fruits (8 Levels)", "🍓"),
    COZY_ART("Chapter 3: Cozy Art", "Characters & Cute Objects (10 Levels)", "🧋")
}

data class ColoringRegion(
    val id: String,
    val name: String,
    val targetColor: Color,
    val pathBuilder: (Float, Float) -> Path, // normalized builder taking (width, height)
    val centerNormalizedX: Float = 0.5f,
    val centerNormalizedY: Float = 0.5f
)

data class RegionState(
    val regionId: String,
    val currentColor: Color = Color.White,
    val fillPercentage: Float = 0f, // 0.0 to 1.0
    val isComplete: Boolean = false,
    val isColorMatched: Boolean = false
)

data class LevelData(
    val id: Int,
    val chapter: ChapterType,
    val title: String,
    val emoji: String,
    val regions: List<ColoringRegion>,
    val outlineBuilder: ((Float, Float) -> Path)? = null
)

data class UserLevelProgress(
    val levelId: Int,
    val isUnlocked: Boolean = false,
    val isCompleted: Boolean = false,
    val stars: Int = 0,
    val regionProgress: Map<String, SavedRegionProgress> = emptyMap()
)

data class SavedRegionProgress(
    val colorHex: Long,
    val fillPercentage: Float,
    val isComplete: Boolean,
    val isColorMatched: Boolean
)

enum class ActiveTool {
    PEN,
    ERASER,
    HINT
}
