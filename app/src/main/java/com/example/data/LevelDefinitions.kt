package com.example.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import com.example.model.ChapterType
import com.example.model.ColoringRegion
import com.example.model.LevelData

object LevelDefinitions {

    val allLevels: List<LevelData> by lazy {
        listOf(
            // ================= CHAPTER 1: SHAPES (8 LEVELS) =================
            createStarLevel(),
            createHeartLevel(),
            createDiamondLevel(),
            createMoonLevel(),
            createHexagonLevel(),
            createCloverLevel(),
            createPrismLevel(),
            createTargetLevel(),

            // ================= CHAPTER 2: FRUITS (8 LEVELS) =================
            createAppleLevel(),
            createStrawberryLevel(),
            createWatermelonLevel(),
            createPineappleLevel(),
            createBananasLevel(),
            createOrangeLevel(),
            createGrapesLevel(),
            createCherriesLevel(),

            // ================= CHAPTER 3: COZY ART (10 LEVELS) =================
            createBobaLevel(),
            createKittyLevel(),
            createRainbowCloudLevel(),
            createDonutLevel(),
            createRocketLevel(),
            createDinoLevel(),
            createIceCreamLevel(),
            createFlowerPotLevel(),
            createBeetleCarLevel(),
            createMushroomHouseLevel(),

            // ================= DAILY CHALLENGE =================
            createDailyBeeLevel()
        )
    }

    fun getLevelsForChapter(chapter: ChapterType): List<LevelData> {
        return allLevels.filter { it.chapter == chapter }
    }

    fun getDailyLevel(): LevelData {
        return allLevels.last()
    }

    // Palette presets for the game
    val defaultPalette: List<Color> = listOf(
        Color(0xFFFF4757), // Crimson Red
        Color(0xFFFF7F50), // Coral Orange
        Color(0xFFFFA502), // Golden Amber
        Color(0xFFFFEAA7), // Soft Butter Yellow
        Color(0xFF2ED573), // Emerald Green
        Color(0xFF7BED9F), // Mint Green
        Color(0xFF70A1FF), // Sky Blue
        Color(0xFF1E90FF), // Deep Azure
        Color(0xFF5352ED), // Royal Indigo
        Color(0xFF9B59B6), // Amethyst Purple
        Color(0xFFFF6B81), // Kawaii Rose
        Color(0xFFFF9FF3), // Bubblegum Pink
        Color(0xFF8B5A2B), // Chocolate Brown
        Color(0xFFD2B48C), // Biscuit Tan
        Color(0xFF2F3542), // Jet Black
        Color(0xFFFFFFFF)  // Clean White
    )

    // -------------------------------------------------------------
    // LEVEL 1: Sparkling Star (⭐)
    // -------------------------------------------------------------
    private fun createStarLevel(): LevelData {
        val yellow = Color(0xFFFFD214)
        val deepGold = Color(0xFFF39C12)
        val pink = Color(0xFFFF69B4)
        val sky = Color(0xFF70A1FF)

        return LevelData(
            id = 1,
            chapter = ChapterType.SHAPES,
            title = "Sparkling Star",
            emoji = "⭐",
            regions = listOf(
                ColoringRegion(
                    id = "star_top",
                    name = "Top Star Peak",
                    targetColor = yellow,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.5f, h * 0.18f)
                            lineTo(w * 0.60f, h * 0.38f)
                            lineTo(w * 0.40f, h * 0.38f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.28f
                ),
                ColoringRegion(
                    id = "star_left_arm",
                    name = "Left Star Arm",
                    targetColor = deepGold,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.40f, h * 0.38f)
                            lineTo(w * 0.18f, h * 0.42f)
                            lineTo(w * 0.35f, h * 0.55f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.31f,
                    centerNormalizedY = 0.45f
                ),
                ColoringRegion(
                    id = "star_right_arm",
                    name = "Right Star Arm",
                    targetColor = deepGold,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.60f, h * 0.38f)
                            lineTo(w * 0.82f, h * 0.42f)
                            lineTo(w * 0.65f, h * 0.55f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.69f,
                    centerNormalizedY = 0.45f
                ),
                ColoringRegion(
                    id = "star_center",
                    name = "Star Heart Center",
                    targetColor = yellow,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.40f, h * 0.38f)
                            lineTo(w * 0.60f, h * 0.38f)
                            lineTo(w * 0.65f, h * 0.55f)
                            lineTo(w * 0.50f, h * 0.65f)
                            lineTo(w * 0.35f, h * 0.55f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.50f
                ),
                ColoringRegion(
                    id = "star_left_leg",
                    name = "Left Star Leg",
                    targetColor = yellow,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.35f, h * 0.55f)
                            lineTo(w * 0.50f, h * 0.65f)
                            lineTo(w * 0.28f, h * 0.82f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.37f,
                    centerNormalizedY = 0.67f
                ),
                ColoringRegion(
                    id = "star_right_leg",
                    name = "Right Star Leg",
                    targetColor = yellow,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.50f, h * 0.65f)
                            lineTo(w * 0.65f, h * 0.55f)
                            lineTo(w * 0.72f, h * 0.82f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.63f,
                    centerNormalizedY = 0.67f
                ),
                ColoringRegion(
                    id = "blush_cheeks",
                    name = "Rosy Cheeks",
                    targetColor = pink,
                    pathBuilder = { w, h ->
                        Path().apply {
                            // Left cheek
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.42f, h * 0.50f, w * 0.46f, h * 0.54f))
                            // Right cheek
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.54f, h * 0.50f, w * 0.58f, h * 0.54f))
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.52f
                ),
                ColoringRegion(
                    id = "sparkle_star",
                    name = "Cosmic Sparkle",
                    targetColor = sky,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.78f, h * 0.24f)
                            lineTo(w * 0.82f, h * 0.20f)
                            lineTo(w * 0.86f, h * 0.24f)
                            lineTo(w * 0.82f, h * 0.28f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.82f,
                    centerNormalizedY = 0.24f
                )
            )
        )
    }

    // -------------------------------------------------------------
    // LEVEL 2: Sweet Heart (❤️)
    // -------------------------------------------------------------
    private fun createHeartLevel(): LevelData {
        val red = Color(0xFFFF4757)
        val deepRose = Color(0xFFD63031)
        val softPink = Color(0xFFFF9FF3)
        val haloGold = Color(0xFFFFD214)

        return LevelData(
            id = 2,
            chapter = ChapterType.SHAPES,
            title = "Sweet Heart",
            emoji = "❤️",
            regions = listOf(
                ColoringRegion(
                    id = "heart_left",
                    name = "Left Heart Lobe",
                    targetColor = red,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.5f, h * 0.42f)
                            cubicTo(w * 0.42f, h * 0.28f, w * 0.22f, h * 0.32f, w * 0.24f, h * 0.50f)
                            cubicTo(w * 0.25f, h * 0.62f, w * 0.40f, h * 0.72f, w * 0.5f, h * 0.82f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.36f,
                    centerNormalizedY = 0.52f
                ),
                ColoringRegion(
                    id = "heart_right",
                    name = "Right Heart Lobe",
                    targetColor = deepRose,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.5f, h * 0.42f)
                            cubicTo(w * 0.58f, h * 0.28f, w * 0.78f, h * 0.32f, w * 0.76f, h * 0.50f)
                            cubicTo(w * 0.75f, h * 0.62f, w * 0.60f, h * 0.72f, w * 0.5f, h * 0.82f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.64f,
                    centerNormalizedY = 0.52f
                ),
                ColoringRegion(
                    id = "wing_left",
                    name = "Left Angel Wing",
                    targetColor = softPink,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.24f, h * 0.48f)
                            cubicTo(w * 0.12f, h * 0.35f, w * 0.05f, h * 0.45f, w * 0.08f, h * 0.58f)
                            cubicTo(w * 0.12f, h * 0.68f, w * 0.20f, h * 0.62f, w * 0.25f, h * 0.58f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.15f,
                    centerNormalizedY = 0.52f
                ),
                ColoringRegion(
                    id = "wing_right",
                    name = "Right Angel Wing",
                    targetColor = softPink,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.76f, h * 0.48f)
                            cubicTo(w * 0.88f, h * 0.35f, w * 0.95f, h * 0.45f, w * 0.92f, h * 0.58f)
                            cubicTo(w * 0.88f, h * 0.68f, w * 0.80f, h * 0.62f, w * 0.75f, h * 0.58f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.85f,
                    centerNormalizedY = 0.52f
                ),
                ColoringRegion(
                    id = "halo_ring",
                    name = "Golden Halo",
                    targetColor = haloGold,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.32f, h * 0.18f, w * 0.68f, h * 0.26f))
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.22f
                )
            )
        )
    }

    // -------------------------------------------------------------
    // LEVEL 3: Crystal Diamond (💎)
    // -------------------------------------------------------------
    private fun createDiamondLevel(): LevelData {
        val lightCyan = Color(0xFFA0E7E5)
        val brightCyan = Color(0xFF00CEC9)
        val deepBlue = Color(0xFF0984E3)
        val highlight = Color(0xFFDFF9FB)

        return LevelData(
            id = 3,
            chapter = ChapterType.SHAPES,
            title = "Crystal Diamond",
            emoji = "💎",
            regions = listOf(
                ColoringRegion(
                    id = "table_top",
                    name = "Crown Table",
                    targetColor = highlight,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.35f, h * 0.30f)
                            lineTo(w * 0.65f, h * 0.30f)
                            lineTo(w * 0.58f, h * 0.42f)
                            lineTo(w * 0.42f, h * 0.42f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.36f
                ),
                ColoringRegion(
                    id = "crown_left",
                    name = "Left Crown Facet",
                    targetColor = lightCyan,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.35f, h * 0.30f)
                            lineTo(w * 0.18f, h * 0.42f)
                            lineTo(w * 0.42f, h * 0.42f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.31f,
                    centerNormalizedY = 0.38f
                ),
                ColoringRegion(
                    id = "crown_right",
                    name = "Right Crown Facet",
                    targetColor = lightCyan,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.65f, h * 0.30f)
                            lineTo(w * 0.82f, h * 0.42f)
                            lineTo(w * 0.58f, h * 0.42f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.69f,
                    centerNormalizedY = 0.38f
                ),
                ColoringRegion(
                    id = "pavilion_center",
                    name = "Center Pavilion",
                    targetColor = brightCyan,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.42f, h * 0.42f)
                            lineTo(w * 0.58f, h * 0.42f)
                            lineTo(w * 0.50f, h * 0.78f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.54f
                ),
                ColoringRegion(
                    id = "pavilion_left",
                    name = "Left Pavilion",
                    targetColor = deepBlue,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.18f, h * 0.42f)
                            lineTo(w * 0.42f, h * 0.42f)
                            lineTo(w * 0.50f, h * 0.78f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.36f,
                    centerNormalizedY = 0.54f
                ),
                ColoringRegion(
                    id = "pavilion_right",
                    name = "Right Pavilion",
                    targetColor = deepBlue,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.82f, h * 0.42f)
                            lineTo(w * 0.58f, h * 0.42f)
                            lineTo(w * 0.50f, h * 0.78f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.64f,
                    centerNormalizedY = 0.54f
                )
            )
        )
    }

    // -------------------------------------------------------------
    // LEVEL 4: Crescent Moon (🌙)
    // -------------------------------------------------------------
    private fun createMoonLevel(): LevelData {
        val moonGold = Color(0xFFFFD214)
        val cloudBlue = Color(0xFFA0E7E5)
        val nightcapPurple = Color(0xFF6C5CE7)
        val capPompom = Color(0xFFFFFFFF)

        return LevelData(
            id = 4,
            chapter = ChapterType.SHAPES,
            title = "Crescent Moon",
            emoji = "🌙",
            regions = listOf(
                ColoringRegion(
                    id = "moon_body",
                    name = "Golden Moon",
                    targetColor = moonGold,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.55f, h * 0.18f)
                            cubicTo(w * 0.30f, h * 0.25f, w * 0.25f, h * 0.65f, w * 0.58f, h * 0.80f)
                            cubicTo(w * 0.38f, h * 0.68f, w * 0.42f, h * 0.35f, w * 0.55f, h * 0.18f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.42f,
                    centerNormalizedY = 0.48f
                ),
                ColoringRegion(
                    id = "nightcap",
                    name = "Sleeping Cap",
                    targetColor = nightcapPurple,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.55f, h * 0.18f)
                            lineTo(w * 0.68f, h * 0.22f)
                            cubicTo(w * 0.75f, h * 0.26f, w * 0.80f, h * 0.35f, w * 0.78f, h * 0.42f)
                            lineTo(w * 0.72f, h * 0.38f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.70f,
                    centerNormalizedY = 0.28f
                ),
                ColoringRegion(
                    id = "pompom",
                    name = "Fluffy Pompom",
                    targetColor = capPompom,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.74f, h * 0.40f, w * 0.84f, h * 0.48f))
                        }
                    },
                    centerNormalizedX = 0.79f,
                    centerNormalizedY = 0.44f
                ),
                ColoringRegion(
                    id = "cloud_base",
                    name = "Cozy Cloud",
                    targetColor = cloudBlue,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.35f, h * 0.76f)
                            cubicTo(w * 0.25f, h * 0.70f, w * 0.20f, h * 0.82f, w * 0.30f, h * 0.88f)
                            cubicTo(w * 0.45f, h * 0.92f, w * 0.65f, h * 0.92f, w * 0.78f, h * 0.85f)
                            cubicTo(w * 0.85f, h * 0.78f, w * 0.75f, h * 0.70f, w * 0.65f, h * 0.72f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.52f,
                    centerNormalizedY = 0.80f
                )
            )
        )
    }

    // -------------------------------------------------------------
    // LEVEL 5: Honeycomb Hexagon (⬡)
    // -------------------------------------------------------------
    private fun createHexagonLevel(): LevelData {
        val honeyGold = Color(0xFFFFA502)
        val amber = Color(0xFFFF7F50)
        val sweetYellow = Color(0xFFFFEAA7)

        fun hexPath(cx: Float, cy: Float, r: Float): (Float, Float) -> Path = { w, h ->
            Path().apply {
                for (i in 0 until 6) {
                    val angle = Math.toRadians((60 * i + 30).toDouble())
                    val x = w * (cx + r * Math.cos(angle).toFloat())
                    val y = h * (cy + r * Math.sin(angle).toFloat())
                    if (i == 0) moveTo(x, y) else lineTo(x, y)
                }
                close()
            }
        }

        return LevelData(
            id = 5,
            chapter = ChapterType.SHAPES,
            title = "Honeycomb Hexagon",
            emoji = "⬡",
            regions = listOf(
                ColoringRegion("hex_center", "Center Cell", honeyGold, hexPath(0.5f, 0.5f, 0.16f), 0.5f, 0.5f),
                ColoringRegion("hex_top", "Top Honey Cell", sweetYellow, hexPath(0.5f, 0.26f, 0.14f), 0.5f, 0.26f),
                ColoringRegion("hex_bottom", "Bottom Honey Cell", sweetYellow, hexPath(0.5f, 0.74f, 0.14f), 0.5f, 0.74f),
                ColoringRegion("hex_left", "Left Amber Cell", amber, hexPath(0.28f, 0.5f, 0.14f), 0.28f, 0.5f),
                ColoringRegion("hex_right", "Right Amber Cell", amber, hexPath(0.72f, 0.5f, 0.14f), 0.72f, 0.5f)
            )
        )
    }

    // -------------------------------------------------------------
    // LEVEL 6: Lucky Clover (🍀)
    // -------------------------------------------------------------
    private fun createCloverLevel(): LevelData {
        val emerald = Color(0xFF2ED573)
        val mint = Color(0xFF7BED9F)
        val ladybugRed = Color(0xFFFF4757)

        return LevelData(
            id = 6,
            chapter = ChapterType.SHAPES,
            title = "Lucky Clover",
            emoji = "🍀",
            regions = listOf(
                ColoringRegion(
                    id = "leaf_top",
                    name = "Top Heart Leaf",
                    targetColor = emerald,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.5f, h * 0.48f)
                            cubicTo(w * 0.40f, h * 0.32f, w * 0.38f, h * 0.18f, w * 0.50f, h * 0.22f)
                            cubicTo(w * 0.62f, h * 0.18f, w * 0.60f, h * 0.32f, w * 0.50f, h * 0.48f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.32f
                ),
                ColoringRegion(
                    id = "leaf_left",
                    name = "Left Heart Leaf",
                    targetColor = mint,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.48f, h * 0.50f)
                            cubicTo(w * 0.32f, h * 0.40f, w * 0.18f, h * 0.38f, w * 0.22f, h * 0.50f)
                            cubicTo(w * 0.18f, h * 0.62f, w * 0.32f, h * 0.60f, w * 0.48f, h * 0.50f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.32f,
                    centerNormalizedY = 0.50f
                ),
                ColoringRegion(
                    id = "leaf_right",
                    name = "Right Heart Leaf",
                    targetColor = mint,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.52f, h * 0.50f)
                            cubicTo(w * 0.68f, h * 0.40f, w * 0.82f, h * 0.38f, w * 0.78f, h * 0.50f)
                            cubicTo(w * 0.82f, h * 0.62f, w * 0.68f, h * 0.60f, w * 0.52f, h * 0.50f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.68f,
                    centerNormalizedY = 0.50f
                ),
                ColoringRegion(
                    id = "leaf_bottom",
                    name = "Bottom Heart Leaf",
                    targetColor = emerald,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.5f, h * 0.52f)
                            cubicTo(w * 0.40f, h * 0.68f, w * 0.38f, h * 0.82f, w * 0.50f, h * 0.78f)
                            cubicTo(w * 0.62f, h * 0.82f, w * 0.60f, h * 0.68f, w * 0.50f, h * 0.52f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.68f
                ),
                ColoringRegion(
                    id = "ladybug",
                    name = "Lucky Ladybug",
                    targetColor = ladybugRed,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.60f, h * 0.32f, w * 0.68f, h * 0.40f))
                        }
                    },
                    centerNormalizedX = 0.64f,
                    centerNormalizedY = 0.36f
                )
            )
        )
    }

    // -------------------------------------------------------------
    // LEVEL 7: Magic Prism (🔺)
    // -------------------------------------------------------------
    private fun createPrismLevel(): LevelData {
        val glass = Color(0xFFA0E7E5)
        val beamRed = Color(0xFFFF4757)
        val beamGold = Color(0xFFFFD214)
        val beamBlue = Color(0xFF1E90FF)

        return LevelData(
            id = 7,
            chapter = ChapterType.SHAPES,
            title = "Magic Prism",
            emoji = "🔺",
            regions = listOf(
                ColoringRegion(
                    id = "prism_body",
                    name = "Crystal Prism",
                    targetColor = glass,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.38f, h * 0.28f)
                            lineTo(w * 0.62f, h * 0.68f)
                            lineTo(w * 0.14f, h * 0.68f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.38f,
                    centerNormalizedY = 0.54f
                ),
                ColoringRegion(
                    id = "rainbow_red",
                    name = "Red Light Ray",
                    targetColor = beamRed,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.52f, h * 0.50f)
                            lineTo(w * 0.88f, h * 0.44f)
                            lineTo(w * 0.88f, h * 0.52f)
                            lineTo(w * 0.54f, h * 0.54f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.70f,
                    centerNormalizedY = 0.48f
                ),
                ColoringRegion(
                    id = "rainbow_gold",
                    name = "Gold Light Ray",
                    targetColor = beamGold,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.54f, h * 0.54f)
                            lineTo(w * 0.88f, h * 0.52f)
                            lineTo(w * 0.88f, h * 0.60f)
                            lineTo(w * 0.56f, h * 0.58f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.72f,
                    centerNormalizedY = 0.56f
                ),
                ColoringRegion(
                    id = "rainbow_blue",
                    name = "Blue Light Ray",
                    targetColor = beamBlue,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.56f, h * 0.58f)
                            lineTo(w * 0.88f, h * 0.60f)
                            lineTo(w * 0.88f, h * 0.68f)
                            lineTo(w * 0.58f, h * 0.62f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.73f,
                    centerNormalizedY = 0.64f
                )
            )
        )
    }

    // -------------------------------------------------------------
    // LEVEL 8: Bulls-Eye Target (🎯)
    // -------------------------------------------------------------
    private fun createTargetLevel(): LevelData {
        val targetRed = Color(0xFFFF4757)
        val targetWhite = Color(0xFFF1F2F6)
        val arrowGold = Color(0xFFFFD214)

        return LevelData(
            id = 8,
            chapter = ChapterType.SHAPES,
            title = "Bulls-Eye Target",
            emoji = "🎯",
            regions = listOf(
                ColoringRegion(
                    id = "outer_ring",
                    name = "Outer Red Ring",
                    targetColor = targetRed,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.15f, h * 0.20f, w * 0.85f, h * 0.80f))
                        }
                    },
                    centerNormalizedX = 0.20f,
                    centerNormalizedY = 0.50f
                ),
                ColoringRegion(
                    id = "middle_ring",
                    name = "White Score Ring",
                    targetColor = targetWhite,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.26f, h * 0.30f, w * 0.74f, h * 0.70f))
                        }
                    },
                    centerNormalizedX = 0.30f,
                    centerNormalizedY = 0.50f
                ),
                ColoringRegion(
                    id = "inner_bullseye",
                    name = "Center Bulls-Eye",
                    targetColor = targetRed,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.38f, h * 0.40f, w * 0.62f, h * 0.60f))
                        }
                    },
                    centerNormalizedX = 0.50f,
                    centerNormalizedY = 0.50f
                ),
                ColoringRegion(
                    id = "arrow_feathers",
                    name = "Arrow Feathers",
                    targetColor = arrowGold,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.68f, h * 0.32f)
                            lineTo(w * 0.84f, h * 0.16f)
                            lineTo(w * 0.78f, h * 0.16f)
                            lineTo(w * 0.65f, h * 0.28f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.76f,
                    centerNormalizedY = 0.22f
                )
            )
        )
    }

    // -------------------------------------------------------------
    // LEVEL 9: Crisp Red Apple (🍎)
    // -------------------------------------------------------------
    private fun createAppleLevel(): LevelData {
        val appleRed = Color(0xFFFF4757)
        val deepRed = Color(0xFFC0392B)
        val leafGreen = Color(0xFF2ED573)
        val stemBrown = Color(0xFF8B5A2B)
        val pulpCream = Color(0xFFFFF7D6)

        return LevelData(
            id = 9,
            chapter = ChapterType.FRUITS,
            title = "Crisp Red Apple",
            emoji = "🍎",
            regions = listOf(
                ColoringRegion(
                    id = "apple_left",
                    name = "Apple Left Half",
                    targetColor = appleRed,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.50f, h * 0.34f)
                            cubicTo(w * 0.32f, h * 0.24f, w * 0.18f, h * 0.44f, w * 0.24f, h * 0.68f)
                            cubicTo(w * 0.28f, h * 0.84f, w * 0.45f, h * 0.88f, w * 0.50f, h * 0.84f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.35f,
                    centerNormalizedY = 0.58f
                ),
                ColoringRegion(
                    id = "apple_right",
                    name = "Apple Right Half",
                    targetColor = deepRed,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.50f, h * 0.34f)
                            cubicTo(w * 0.68f, h * 0.24f, w * 0.82f, h * 0.44f, w * 0.76f, h * 0.68f)
                            cubicTo(w * 0.72f, h * 0.84f, w * 0.55f, h * 0.88f, w * 0.50f, h * 0.84f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.65f,
                    centerNormalizedY = 0.58f
                ),
                ColoringRegion(
                    id = "apple_stem",
                    name = "Apple Stem",
                    targetColor = stemBrown,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.48f, h * 0.34f)
                            cubicTo(w * 0.46f, h * 0.22f, w * 0.54f, h * 0.16f, w * 0.56f, h * 0.14f)
                            lineTo(w * 0.58f, h * 0.15f)
                            cubicTo(w * 0.54f, h * 0.22f, w * 0.52f, h * 0.34f, w * 0.52f, h * 0.34f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.52f,
                    centerNormalizedY = 0.24f
                ),
                ColoringRegion(
                    id = "apple_leaf",
                    name = "Green Leaf",
                    targetColor = leafGreen,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.54f, h * 0.24f)
                            cubicTo(w * 0.65f, h * 0.14f, w * 0.78f, h * 0.16f, w * 0.80f, h * 0.24f)
                            cubicTo(w * 0.74f, h * 0.32f, w * 0.62f, h * 0.30f, w * 0.54f, h * 0.24f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.67f,
                    centerNormalizedY = 0.22f
                ),
                ColoringRegion(
                    id = "apple_wedge",
                    name = "Cut Wedge Slice",
                    targetColor = pulpCream,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.40f, h * 0.50f, w * 0.60f, h * 0.74f))
                        }
                    },
                    centerNormalizedX = 0.50f,
                    centerNormalizedY = 0.62f
                )
            )
        )
    }

    // -------------------------------------------------------------
    // LEVEL 10: Sweet Strawberry (🍓)
    // -------------------------------------------------------------
    private fun createStrawberryLevel(): LevelData {
        val berryRed = Color(0xFFFF4757)
        val calyxGreen = Color(0xFF2ED573)
        val seedsGold = Color(0xFFFFD214)

        return LevelData(
            id = 10,
            chapter = ChapterType.FRUITS,
            title = "Sweet Strawberry",
            emoji = "🍓",
            regions = listOf(
                ColoringRegion(
                    id = "strawberry_body",
                    name = "Strawberry Berry",
                    targetColor = berryRed,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.26f, h * 0.38f)
                            cubicTo(w * 0.18f, h * 0.55f, w * 0.35f, h * 0.78f, w * 0.50f, h * 0.88f)
                            cubicTo(w * 0.65f, h * 0.78f, w * 0.82f, h * 0.55f, w * 0.74f, h * 0.38f)
                            cubicTo(w * 0.64f, h * 0.34f, w * 0.36f, h * 0.34f, w * 0.26f, h * 0.38f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.60f
                ),
                ColoringRegion(
                    id = "calyx_leaves",
                    name = "Green Calyx Crown",
                    targetColor = calyxGreen,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.50f, h * 0.22f)
                            lineTo(w * 0.38f, h * 0.36f)
                            lineTo(w * 0.50f, h * 0.34f)
                            lineTo(w * 0.62f, h * 0.36f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.30f
                ),
                ColoringRegion(
                    id = "strawberry_seeds",
                    name = "Golden Seeds",
                    targetColor = seedsGold,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.42f, h * 0.48f, w * 0.46f, h * 0.53f))
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.54f, h * 0.48f, w * 0.58f, h * 0.53f))
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.48f, h * 0.62f, w * 0.52f, h * 0.67f))
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.55f
                )
            )
        )
    }

    // -------------------------------------------------------------
    // LEVEL 11: Fresh Watermelon (🍉)
    // -------------------------------------------------------------
    private fun createWatermelonLevel(): LevelData {
        val darkGreen = Color(0xFF1E824C)
        val paleGreen = Color(0xFFA2D9CE)
        val rubyRed = Color(0xFFFF4757)

        return LevelData(
            id = 11,
            chapter = ChapterType.FRUITS,
            title = "Fresh Watermelon",
            emoji = "🍉",
            regions = listOf(
                ColoringRegion(
                    id = "dark_rind",
                    name = "Outer Dark Rind",
                    targetColor = darkGreen,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.15f, h * 0.38f)
                            cubicTo(w * 0.25f, h * 0.82f, w * 0.75f, h * 0.82f, w * 0.85f, h * 0.38f)
                            lineTo(w * 0.80f, h * 0.42f)
                            cubicTo(w * 0.72f, h * 0.76f, w * 0.28f, h * 0.76f, w * 0.20f, h * 0.42f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.75f
                ),
                ColoringRegion(
                    id = "pale_rind",
                    name = "Inner Pale Rind",
                    targetColor = paleGreen,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.20f, h * 0.42f)
                            cubicTo(w * 0.28f, h * 0.76f, w * 0.72f, h * 0.76f, w * 0.80f, h * 0.42f)
                            lineTo(w * 0.76f, h * 0.44f)
                            cubicTo(w * 0.70f, h * 0.70f, w * 0.30f, h * 0.70f, w * 0.24f, h * 0.44f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.68f
                ),
                ColoringRegion(
                    id = "ruby_flesh",
                    name = "Juicy Ruby Flesh",
                    targetColor = rubyRed,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.24f, h * 0.44f)
                            cubicTo(w * 0.30f, h * 0.70f, w * 0.70f, h * 0.70f, w * 0.76f, h * 0.44f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.52f
                )
            )
        )
    }

    // -------------------------------------------------------------
    // LEVEL 12: Tropical Pineapple (🍍)
    // -------------------------------------------------------------
    private fun createPineappleLevel(): LevelData {
        val yellowGold = Color(0xFFFFD214)
        val crownGreen = Color(0xFF2ED573)
        val sunglassesBlack = Color(0xFF2F3542)

        return LevelData(
            id = 12,
            chapter = ChapterType.FRUITS,
            title = "Tropical Pineapple",
            emoji = "🍍",
            regions = listOf(
                ColoringRegion(
                    id = "crown_leaves",
                    name = "Spiky Palm Crown",
                    targetColor = crownGreen,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.50f, h * 0.14f)
                            lineTo(w * 0.38f, h * 0.36f)
                            lineTo(w * 0.50f, h * 0.32f)
                            lineTo(w * 0.62f, h * 0.36f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.24f
                ),
                ColoringRegion(
                    id = "pineapple_body",
                    name = "Golden Pineapple",
                    targetColor = yellowGold,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.28f, h * 0.32f, w * 0.72f, h * 0.85f))
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.62f
                ),
                ColoringRegion(
                    id = "sunglasses",
                    name = "Cool Shades",
                    targetColor = sunglassesBlack,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addRoundRect(
                                androidx.compose.ui.geometry.RoundRect(
                                    rect = androidx.compose.ui.geometry.Rect(w * 0.34f, h * 0.48f, w * 0.48f, h * 0.58f),
                                    radiusX = 16f,
                                    radiusY = 16f
                                )
                            )
                            addRoundRect(
                                androidx.compose.ui.geometry.RoundRect(
                                    rect = androidx.compose.ui.geometry.Rect(w * 0.52f, h * 0.48f, w * 0.66f, h * 0.58f),
                                    radiusX = 16f,
                                    radiusY = 16f
                                )
                            )
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.53f
                )
            )
        )
    }

    // -------------------------------------------------------------
    // LEVEL 13: Ripe Bananas (🍌)
    // -------------------------------------------------------------
    private fun createBananasLevel(): LevelData {
        val bananaYellow = Color(0xFFFFD214)
        val paleYellow = Color(0xFFFFEAA7)
        val stemGreen = Color(0xFF2ED573)

        return LevelData(
            id = 13,
            chapter = ChapterType.FRUITS,
            title = "Ripe Bananas",
            emoji = "🍌",
            regions = listOf(
                ColoringRegion(
                    id = "banana_front",
                    name = "Front Banana",
                    targetColor = bananaYellow,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.48f, h * 0.24f)
                            cubicTo(w * 0.25f, h * 0.42f, w * 0.32f, h * 0.75f, w * 0.65f, h * 0.82f)
                            cubicTo(w * 0.45f, h * 0.70f, w * 0.40f, h * 0.46f, w * 0.52f, h * 0.26f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.42f,
                    centerNormalizedY = 0.56f
                ),
                ColoringRegion(
                    id = "banana_back",
                    name = "Back Banana",
                    targetColor = paleYellow,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.52f, h * 0.26f)
                            cubicTo(w * 0.68f, h * 0.44f, w * 0.62f, h * 0.72f, w * 0.75f, h * 0.78f)
                            cubicTo(w * 0.66f, h * 0.62f, w * 0.58f, h * 0.40f, w * 0.48f, h * 0.24f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.62f,
                    centerNormalizedY = 0.50f
                ),
                ColoringRegion(
                    id = "banana_stem",
                    name = "Green Bunch Tip",
                    targetColor = stemGreen,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addRect(androidx.compose.ui.geometry.Rect(w * 0.46f, h * 0.18f, w * 0.54f, h * 0.26f))
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.22f
                )
            )
        )
    }

    // -------------------------------------------------------------
    // LEVEL 14: Citrus Orange (🍊)
    // -------------------------------------------------------------
    private fun createOrangeLevel(): LevelData {
        val peelOrange = Color(0xFFFF7F50)
        val pulpAmber = Color(0xFFFFA502)
        val leafGreen = Color(0xFF2ED573)

        return LevelData(
            id = 14,
            chapter = ChapterType.FRUITS,
            title = "Citrus Orange",
            emoji = "🍊",
            regions = listOf(
                ColoringRegion(
                    id = "orange_peel",
                    name = "Orange Outer Rind",
                    targetColor = peelOrange,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.22f, h * 0.28f, w * 0.78f, h * 0.84f))
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.38f
                ),
                ColoringRegion(
                    id = "orange_slice",
                    name = "Juicy Citrus Wedge",
                    targetColor = pulpAmber,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.32f, h * 0.38f, w * 0.68f, h * 0.74f))
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.56f
                ),
                ColoringRegion(
                    id = "orange_leaf",
                    name = "Citrus Leaf",
                    targetColor = leafGreen,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.50f, h * 0.28f)
                            cubicTo(w * 0.58f, h * 0.16f, w * 0.72f, h * 0.18f, w * 0.75f, h * 0.26f)
                            cubicTo(w * 0.68f, h * 0.32f, w * 0.56f, h * 0.32f, w * 0.50f, h * 0.28f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.62f,
                    centerNormalizedY = 0.24f
                )
            )
        )
    }

    // -------------------------------------------------------------
    // LEVEL 15: Plump Grapes (🍇)
    // -------------------------------------------------------------
    private fun createGrapesLevel(): LevelData {
        val grapePurple = Color(0xFF8E44AD)
        val deepViolet = Color(0xFF6C5CE7)
        val vineGreen = Color(0xFF2ED573)

        return LevelData(
            id = 15,
            chapter = ChapterType.FRUITS,
            title = "Plump Grapes",
            emoji = "🍇",
            regions = listOf(
                ColoringRegion(
                    id = "grapes_top",
                    name = "Top Berry Cluster",
                    targetColor = grapePurple,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.30f, h * 0.36f, w * 0.48f, h * 0.54f))
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.52f, h * 0.36f, w * 0.70f, h * 0.54f))
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.45f
                ),
                ColoringRegion(
                    id = "grapes_middle",
                    name = "Mid Grape Bunch",
                    targetColor = deepViolet,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.40f, h * 0.50f, w * 0.60f, h * 0.68f))
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.59f
                ),
                ColoringRegion(
                    id = "grapes_tip",
                    name = "Bottom Grape Tip",
                    targetColor = grapePurple,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.44f, h * 0.66f, w * 0.56f, h * 0.78f))
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.72f
                ),
                ColoringRegion(
                    id = "vine_leaf",
                    name = "Vine Tendril Leaf",
                    targetColor = vineGreen,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.50f, h * 0.36f)
                            cubicTo(w * 0.45f, h * 0.22f, w * 0.55f, h * 0.18f, w * 0.60f, h * 0.20f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.54f,
                    centerNormalizedY = 0.26f
                )
            )
        )
    }

    // -------------------------------------------------------------
    // LEVEL 16: Twin Cherries (🍒)
    // -------------------------------------------------------------
    private fun createCherriesLevel(): LevelData {
        val cherryRed = Color(0xFFFF4757)
        val deepCrimson = Color(0xFFC0392B)
        val leafGreen = Color(0xFF2ED573)

        return LevelData(
            id = 16,
            chapter = ChapterType.FRUITS,
            title = "Twin Cherries",
            emoji = "🍒",
            regions = listOf(
                ColoringRegion(
                    id = "cherry_left",
                    name = "Left Glossy Cherry",
                    targetColor = cherryRed,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.22f, h * 0.52f, w * 0.48f, h * 0.78f))
                        }
                    },
                    centerNormalizedX = 0.35f,
                    centerNormalizedY = 0.65f
                ),
                ColoringRegion(
                    id = "cherry_right",
                    name = "Right Glossy Cherry",
                    targetColor = deepCrimson,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.52f, h * 0.56f, w * 0.78f, h * 0.82f))
                        }
                    },
                    centerNormalizedX = 0.65f,
                    centerNormalizedY = 0.69f
                ),
                ColoringRegion(
                    id = "cherry_leaf",
                    name = "Joined Double Leaf",
                    targetColor = leafGreen,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.50f, h * 0.24f)
                            cubicTo(w * 0.62f, h * 0.15f, w * 0.74f, h * 0.18f, w * 0.72f, h * 0.26f)
                            cubicTo(w * 0.64f, h * 0.30f, w * 0.54f, h * 0.28f, w * 0.50f, h * 0.24f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.62f,
                    centerNormalizedY = 0.22f
                )
            )
        )
    }

    // -------------------------------------------------------------
    // LEVEL 17: Happy Boba Tea (🧋)
    // -------------------------------------------------------------
    private fun createBobaLevel(): LevelData {
        val milkTea = Color(0xFFD2B48C)
        val creamWhite = Color(0xFFFFF7D6)
        val strawPink = Color(0xFFFF69B4)
        val bobaBrown = Color(0xFF2F3542)

        return LevelData(
            id = 17,
            chapter = ChapterType.COZY_ART,
            title = "Happy Boba Tea",
            emoji = "🧋",
            regions = listOf(
                ColoringRegion(
                    id = "cup_body",
                    name = "Milk Tea Cup",
                    targetColor = milkTea,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.32f, h * 0.40f)
                            lineTo(w * 0.68f, h * 0.40f)
                            lineTo(w * 0.62f, h * 0.82f)
                            lineTo(w * 0.38f, h * 0.82f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.60f
                ),
                ColoringRegion(
                    id = "foam_cap",
                    name = "Foam Cream Cap",
                    targetColor = creamWhite,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addRoundRect(
                                androidx.compose.ui.geometry.RoundRect(
                                    rect = androidx.compose.ui.geometry.Rect(w * 0.28f, h * 0.34f, w * 0.72f, h * 0.42f),
                                    radiusX = 20f,
                                    radiusY = 20f
                                )
                            )
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.38f
                ),
                ColoringRegion(
                    id = "straw",
                    name = "Boba Straw",
                    targetColor = strawPink,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.54f, h * 0.16f)
                            lineTo(w * 0.60f, h * 0.16f)
                            lineTo(w * 0.56f, h * 0.34f)
                            lineTo(w * 0.50f, h * 0.34f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.55f,
                    centerNormalizedY = 0.24f
                ),
                ColoringRegion(
                    id = "boba_pearls",
                    name = "Chewy Boba Pearls",
                    targetColor = bobaBrown,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.40f, h * 0.72f, w * 0.46f, h * 0.78f))
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.48f, h * 0.74f, w * 0.54f, h * 0.80f))
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.54f, h * 0.71f, w * 0.60f, h * 0.77f))
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.75f
                )
            )
        )
    }

    // -------------------------------------------------------------
    // LEVEL 18: Kawaii Kitty (🐱)
    // -------------------------------------------------------------
    private fun createKittyLevel(): LevelData {
        val furWhite = Color(0xFFF1F2F6)
        val earPink = Color(0xFFFF9FF3)
        val collarRed = Color(0xFFFF4757)
        val bellGold = Color(0xFFFFD214)

        return LevelData(
            id = 18,
            chapter = ChapterType.COZY_ART,
            title = "Kawaii Kitty",
            emoji = "🐱",
            regions = listOf(
                ColoringRegion(
                    id = "cat_head",
                    name = "Kitty Face",
                    targetColor = furWhite,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.25f, h * 0.36f, w * 0.75f, h * 0.72f))
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.54f
                ),
                ColoringRegion(
                    id = "ears_outer",
                    name = "Pointy Kitty Ears",
                    targetColor = furWhite,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.30f, h * 0.42f)
                            lineTo(w * 0.24f, h * 0.26f)
                            lineTo(w * 0.42f, h * 0.38f)
                            close()
                            moveTo(w * 0.58f, h * 0.38f)
                            lineTo(w * 0.76f, h * 0.26f)
                            lineTo(w * 0.70f, h * 0.42f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.32f,
                    centerNormalizedY = 0.32f
                ),
                ColoringRegion(
                    id = "ears_inner",
                    name = "Inner Pink Ears",
                    targetColor = earPink,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.31f, h * 0.40f)
                            lineTo(w * 0.27f, h * 0.30f)
                            lineTo(w * 0.39f, h * 0.38f)
                            close()
                            moveTo(w * 0.61f, h * 0.38f)
                            lineTo(w * 0.73f, h * 0.30f)
                            lineTo(w * 0.69f, h * 0.40f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.35f
                ),
                ColoringRegion(
                    id = "collar",
                    name = "Red Bell Collar",
                    targetColor = collarRed,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addRoundRect(
                                androidx.compose.ui.geometry.RoundRect(
                                    rect = androidx.compose.ui.geometry.Rect(w * 0.35f, h * 0.70f, w * 0.65f, h * 0.76f),
                                    radiusX = 12f,
                                    radiusY = 12f
                                )
                            )
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.73f
                ),
                ColoringRegion(
                    id = "bell",
                    name = "Golden Chime Bell",
                    targetColor = bellGold,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.46f, h * 0.75f, w * 0.54f, h * 0.83f))
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.79f
                )
            )
        )
    }

    // -------------------------------------------------------------
    // LEVEL 19: Rainbow Cloud (🌈)
    // -------------------------------------------------------------
    private fun createRainbowCloudLevel(): LevelData {
        val cloudWhite = Color(0xFFF1F2F6)
        val arcRed = Color(0xFFFF4757)
        val arcYellow = Color(0xFFFFD214)
        val arcBlue = Color(0xFF1E90FF)

        return LevelData(
            id = 19,
            chapter = ChapterType.COZY_ART,
            title = "Rainbow Cloud",
            emoji = "🌈",
            regions = listOf(
                ColoringRegion(
                    id = "rainbow_arc_red",
                    name = "Red Rainbow Stripe",
                    targetColor = arcRed,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.20f, h * 0.60f)
                            cubicTo(w * 0.25f, h * 0.25f, w * 0.75f, h * 0.25f, w * 0.80f, h * 0.60f)
                            lineTo(w * 0.75f, h * 0.60f)
                            cubicTo(w * 0.70f, h * 0.32f, w * 0.30f, h * 0.32f, w * 0.25f, h * 0.60f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.30f
                ),
                ColoringRegion(
                    id = "rainbow_arc_yellow",
                    name = "Yellow Rainbow Stripe",
                    targetColor = arcYellow,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.25f, h * 0.60f)
                            cubicTo(w * 0.30f, h * 0.32f, w * 0.70f, h * 0.32f, w * 0.75f, h * 0.60f)
                            lineTo(w * 0.70f, h * 0.60f)
                            cubicTo(w * 0.66f, h * 0.38f, w * 0.34f, h * 0.38f, w * 0.30f, h * 0.60f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.38f
                ),
                ColoringRegion(
                    id = "rainbow_arc_blue",
                    name = "Blue Rainbow Stripe",
                    targetColor = arcBlue,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.30f, h * 0.60f)
                            cubicTo(w * 0.34f, h * 0.38f, w * 0.66f, h * 0.38f, w * 0.70f, h * 0.60f)
                            lineTo(w * 0.65f, h * 0.60f)
                            cubicTo(w * 0.62f, h * 0.44f, w * 0.38f, h * 0.44f, w * 0.35f, h * 0.60f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.46f
                ),
                ColoringRegion(
                    id = "cloud_fluff",
                    name = "Fluffy Cloud",
                    targetColor = cloudWhite,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.25f, h * 0.68f)
                            cubicTo(w * 0.15f, h * 0.62f, w * 0.18f, h * 0.78f, w * 0.30f, h * 0.82f)
                            cubicTo(w * 0.45f, h * 0.86f, w * 0.65f, h * 0.86f, w * 0.78f, h * 0.80f)
                            cubicTo(w * 0.88f, h * 0.72f, w * 0.80f, h * 0.60f, w * 0.68f, h * 0.64f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.74f
                )
            )
        )
    }

    // -------------------------------------------------------------
    // LEVEL 20: Glazed Donut (🍩)
    // -------------------------------------------------------------
    private fun createDonutLevel(): LevelData {
        val pastryBrown = Color(0xFFD2B48C)
        val pinkGlaze = Color(0xFFFF69B4)
        val sprinklesMulti = Color(0xFFFFD214)

        return LevelData(
            id = 20,
            chapter = ChapterType.COZY_ART,
            title = "Glazed Donut",
            emoji = "🍩",
            regions = listOf(
                ColoringRegion(
                    id = "donut_pastry",
                    name = "Baked Pastry Ring",
                    targetColor = pastryBrown,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.18f, h * 0.25f, w * 0.82f, h * 0.75f))
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.30f
                ),
                ColoringRegion(
                    id = "donut_glaze",
                    name = "Strawberry Glaze",
                    targetColor = pinkGlaze,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.22f, h * 0.28f, w * 0.78f, h * 0.72f))
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.62f
                ),
                ColoringRegion(
                    id = "donut_sprinkles",
                    name = "Sweet Sprinkles",
                    targetColor = sprinklesMulti,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.35f, h * 0.36f, w * 0.40f, h * 0.40f))
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.60f, h * 0.36f, w * 0.65f, h * 0.40f))
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.32f, h * 0.58f, w * 0.37f, h * 0.62f))
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.62f, h * 0.58f, w * 0.67f, h * 0.62f))
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.48f
                )
            )
        )
    }

    // -------------------------------------------------------------
    // LEVEL 21: Cosmic Rocket (🚀)
    // -------------------------------------------------------------
    private fun createRocketLevel(): LevelData {
        val hullWhite = Color(0xFFF1F2F6)
        val noseRed = Color(0xFFFF4757)
        val windowCyan = Color(0xFF00CEC9)
        val fireOrange = Color(0xFFFF7F50)

        return LevelData(
            id = 21,
            chapter = ChapterType.COZY_ART,
            title = "Cosmic Rocket",
            emoji = "🚀",
            regions = listOf(
                ColoringRegion(
                    id = "rocket_hull",
                    name = "Rocket Fuselage",
                    targetColor = hullWhite,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.50f, h * 0.22f)
                            cubicTo(w * 0.35f, h * 0.38f, w * 0.35f, h * 0.65f, w * 0.38f, h * 0.72f)
                            lineTo(w * 0.62f, h * 0.72f)
                            cubicTo(w * 0.65f, h * 0.65f, w * 0.65f, h * 0.38f, w * 0.50f, h * 0.22f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.48f
                ),
                ColoringRegion(
                    id = "nose_cone",
                    name = "Red Nose Cone",
                    targetColor = noseRed,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.50f, h * 0.22f)
                            lineTo(w * 0.42f, h * 0.35f)
                            lineTo(w * 0.58f, h * 0.35f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.28f
                ),
                ColoringRegion(
                    id = "window",
                    name = "Porthole Glass",
                    targetColor = windowCyan,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.42f, h * 0.44f, w * 0.58f, h * 0.56f))
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.50f
                ),
                ColoringRegion(
                    id = "fire_thruster",
                    name = "Blazing Fire Flame",
                    targetColor = fireOrange,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.44f, h * 0.72f)
                            lineTo(w * 0.50f, h * 0.88f)
                            lineTo(w * 0.56f, h * 0.72f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.80f
                )
            )
        )
    }

    // -------------------------------------------------------------
    // LEVEL 22: Baby Dino (🦖)
    // -------------------------------------------------------------
    private fun createDinoLevel(): LevelData {
        val dinoGreen = Color(0xFF2ED573)
        val bellyYellow = Color(0xFFFFEAA7)
        val spikesCoral = Color(0xFFFF7F50)

        return LevelData(
            id = 22,
            chapter = ChapterType.COZY_ART,
            title = "Baby Dino",
            emoji = "🦖",
            regions = listOf(
                ColoringRegion(
                    id = "dino_body",
                    name = "Dino Body",
                    targetColor = dinoGreen,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.30f, h * 0.34f, w * 0.70f, h * 0.76f))
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.52f
                ),
                ColoringRegion(
                    id = "belly_patch",
                    name = "Tummy Patch",
                    targetColor = bellyYellow,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.45f, h * 0.52f, w * 0.65f, h * 0.72f))
                        }
                    },
                    centerNormalizedX = 0.55f,
                    centerNormalizedY = 0.62f
                ),
                ColoringRegion(
                    id = "dino_spikes",
                    name = "Back Spikes",
                    targetColor = spikesCoral,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.28f, h * 0.42f)
                            lineTo(w * 0.22f, h * 0.38f)
                            lineTo(w * 0.30f, h * 0.48f)
                            close()
                            moveTo(w * 0.26f, h * 0.52f)
                            lineTo(w * 0.20f, h * 0.50f)
                            lineTo(w * 0.28f, h * 0.60f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.25f,
                    centerNormalizedY = 0.46f
                )
            )
        )
    }

    // -------------------------------------------------------------
    // LEVEL 23: Ice Cream Sundae (🍨)
    // -------------------------------------------------------------
    private fun createIceCreamLevel(): LevelData {
        val coneWaffle = Color(0xFFD2B48C)
        val vanillaScoop = Color(0xFFFFF7D6)
        val berryScoop = Color(0xFFFF69B4)
        val cherryRed = Color(0xFFFF4757)

        return LevelData(
            id = 23,
            chapter = ChapterType.COZY_ART,
            title = "Ice Cream Sundae",
            emoji = "🍨",
            regions = listOf(
                ColoringRegion(
                    id = "waffle_cone",
                    name = "Crispy Waffle Cone",
                    targetColor = coneWaffle,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.35f, h * 0.55f)
                            lineTo(w * 0.65f, h * 0.55f)
                            lineTo(w * 0.50f, h * 0.85f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.68f
                ),
                ColoringRegion(
                    id = "scoop_bottom",
                    name = "Vanilla Scoop",
                    targetColor = vanillaScoop,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.32f, h * 0.42f, w * 0.68f, h * 0.60f))
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.50f
                ),
                ColoringRegion(
                    id = "scoop_top",
                    name = "Berry Scoop",
                    targetColor = berryScoop,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.36f, h * 0.28f, w * 0.64f, h * 0.46f))
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.36f
                ),
                ColoringRegion(
                    id = "cherry_top",
                    name = "Glossy Cherry",
                    targetColor = cherryRed,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.45f, h * 0.20f, w * 0.55f, h * 0.30f))
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.25f
                )
            )
        )
    }

    // -------------------------------------------------------------
    // LEVEL 24: Sunny Flower Pot (🌻)
    // -------------------------------------------------------------
    private fun createFlowerPotLevel(): LevelData {
        val potClay = Color(0xFFFF7F50)
        val stemGreen = Color(0xFF2ED573)
        val petalsYellow = Color(0xFFFFD214)
        val centerBrown = Color(0xFF8B5A2B)

        return LevelData(
            id = 24,
            chapter = ChapterType.COZY_ART,
            title = "Sunny Flower Pot",
            emoji = "🌻",
            regions = listOf(
                ColoringRegion(
                    id = "clay_pot",
                    name = "Terracotta Pot",
                    targetColor = potClay,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.35f, h * 0.60f)
                            lineTo(w * 0.65f, h * 0.60f)
                            lineTo(w * 0.60f, h * 0.82f)
                            lineTo(w * 0.40f, h * 0.82f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.70f
                ),
                ColoringRegion(
                    id = "flower_stem",
                    name = "Green Leaf Stem",
                    targetColor = stemGreen,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.48f, h * 0.44f)
                            lineTo(w * 0.52f, h * 0.44f)
                            lineTo(w * 0.52f, h * 0.60f)
                            lineTo(w * 0.48f, h * 0.60f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.52f
                ),
                ColoringRegion(
                    id = "sun_petals",
                    name = "Golden Petals",
                    targetColor = petalsYellow,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.28f, h * 0.20f, w * 0.72f, h * 0.48f))
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.26f
                ),
                ColoringRegion(
                    id = "flower_face",
                    name = "Center Smiley Disc",
                    targetColor = centerBrown,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.40f, h * 0.26f, w * 0.60f, h * 0.42f))
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.34f
                )
            )
        )
    }

    // -------------------------------------------------------------
    // LEVEL 25: Cute Beetle Car (🚗)
    // -------------------------------------------------------------
    private fun createBeetleCarLevel(): LevelData {
        val carCyan = Color(0xFF00CEC9)
        val glassSky = Color(0xFFA0E7E5)
        val tiresBlack = Color(0xFF2F3542)
        val lightYellow = Color(0xFFFFD214)

        return LevelData(
            id = 25,
            chapter = ChapterType.COZY_ART,
            title = "Cute Beetle Car",
            emoji = "🚗",
            regions = listOf(
                ColoringRegion(
                    id = "car_body",
                    name = "Beetle Chassis",
                    targetColor = carCyan,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.20f, h * 0.55f)
                            cubicTo(w * 0.25f, h * 0.35f, w * 0.75f, h * 0.35f, w * 0.80f, h * 0.55f)
                            lineTo(w * 0.85f, h * 0.65f)
                            lineTo(w * 0.15f, h * 0.65f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.56f
                ),
                ColoringRegion(
                    id = "car_windshield",
                    name = "Windshield Glass",
                    targetColor = glassSky,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addRoundRect(
                                androidx.compose.ui.geometry.RoundRect(
                                    rect = androidx.compose.ui.geometry.Rect(w * 0.34f, h * 0.42f, w * 0.66f, h * 0.52f),
                                    radiusX = 14f,
                                    radiusY = 14f
                                )
                            )
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.47f
                ),
                ColoringRegion(
                    id = "wheels",
                    name = "Car Tires",
                    targetColor = tiresBlack,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.24f, h * 0.60f, w * 0.38f, h * 0.74f))
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.62f, h * 0.60f, w * 0.76f, h * 0.74f))
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.67f
                ),
                ColoringRegion(
                    id = "headlights",
                    name = "Golden Headlights",
                    targetColor = lightYellow,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.14f, h * 0.58f, w * 0.18f, h * 0.64f))
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.82f, h * 0.58f, w * 0.86f, h * 0.64f))
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.61f
                )
            )
        )
    }

    // -------------------------------------------------------------
    // LEVEL 26: Mushroom House (🍄)
    // -------------------------------------------------------------
    private fun createMushroomHouseLevel(): LevelData {
        val capRed = Color(0xFFFF4757)
        val stemCream = Color(0xFFFFF7D6)
        val doorWood = Color(0xFF8B5A2B)
        val spotsWhite = Color(0xFFFFFFFF)

        return LevelData(
            id = 26,
            chapter = ChapterType.COZY_ART,
            title = "Mushroom House",
            emoji = "🍄",
            regions = listOf(
                ColoringRegion(
                    id = "mushroom_cap",
                    name = "Crimson Cap",
                    targetColor = capRed,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.20f, h * 0.48f)
                            cubicTo(w * 0.25f, h * 0.20f, w * 0.75f, h * 0.20f, w * 0.80f, h * 0.48f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.36f
                ),
                ColoringRegion(
                    id = "polka_dots",
                    name = "White Polka Dots",
                    targetColor = spotsWhite,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.35f, h * 0.30f, w * 0.42f, h * 0.38f))
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.58f, h * 0.30f, w * 0.65f, h * 0.38f))
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.34f
                ),
                ColoringRegion(
                    id = "mushroom_stem",
                    name = "Cottage Base",
                    targetColor = stemCream,
                    pathBuilder = { w, h ->
                        Path().apply {
                            moveTo(w * 0.36f, h * 0.48f)
                            lineTo(w * 0.64f, h * 0.48f)
                            lineTo(w * 0.68f, h * 0.80f)
                            lineTo(w * 0.32f, h * 0.80f)
                            close()
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.64f
                ),
                ColoringRegion(
                    id = "cottage_door",
                    name = "Wooden Door",
                    targetColor = doorWood,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addRoundRect(
                                androidx.compose.ui.geometry.RoundRect(
                                    rect = androidx.compose.ui.geometry.Rect(w * 0.44f, h * 0.62f, w * 0.56f, h * 0.80f),
                                    radiusX = 14f,
                                    radiusY = 14f
                                )
                            )
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.71f
                )
            )
        )
    }

    // -------------------------------------------------------------
    // DAILY LEVEL: Sunflower & Honey Bee (🐝)
    // -------------------------------------------------------------
    private fun createDailyBeeLevel(): LevelData {
        val beeYellow = Color(0xFFFFD214)
        val beeBlack = Color(0xFF2F3542)
        val wingCyan = Color(0xFFA0E7E5)

        return LevelData(
            id = 99,
            chapter = ChapterType.COZY_ART,
            title = "Honey Bee Daily",
            emoji = "🐝",
            regions = listOf(
                ColoringRegion(
                    id = "bee_body",
                    name = "Bee Striped Body",
                    targetColor = beeYellow,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.30f, h * 0.40f, w * 0.70f, h * 0.65f))
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.52f
                ),
                ColoringRegion(
                    id = "bee_stripes",
                    name = "Black Bee Stripes",
                    targetColor = beeBlack,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addRect(androidx.compose.ui.geometry.Rect(w * 0.42f, h * 0.41f, w * 0.48f, h * 0.64f))
                            addRect(androidx.compose.ui.geometry.Rect(w * 0.54f, h * 0.41f, w * 0.60f, h * 0.64f))
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.52f
                ),
                ColoringRegion(
                    id = "bee_wings",
                    name = "Translucent Wings",
                    targetColor = wingCyan,
                    pathBuilder = { w, h ->
                        Path().apply {
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.40f, h * 0.22f, w * 0.52f, h * 0.42f))
                            addOval(androidx.compose.ui.geometry.Rect(w * 0.50f, h * 0.22f, w * 0.62f, h * 0.42f))
                        }
                    },
                    centerNormalizedX = 0.5f,
                    centerNormalizedY = 0.32f
                )
            )
        )
    }
}
