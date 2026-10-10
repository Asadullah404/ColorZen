package com.example.ui.screens

import android.graphics.RectF
import android.graphics.Region
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AsmrSoundManager
import com.example.data.CloudSyncRepository
import com.example.data.LevelDefinitions
import com.example.haptics.HapticManager
import com.example.model.ActiveTool
import com.example.model.ColoringRegion
import com.example.model.LevelData
import com.example.model.RegionState
import com.example.storage.GamePreferences
import com.example.ui.components.ExtraordinaryMarkerPen
import com.example.ui.components.GoalPreviewDialog
import com.example.ui.components.InkSparkle
import com.example.ui.components.LevelVictoryDialog
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.hypot
import kotlin.random.Random

@Composable
fun GameScreen(
    level: LevelData,
    preferences: GamePreferences,
    currentUser: FirebaseUser?,
    cloudSyncRepository: CloudSyncRepository,
    soundManager: AsmrSoundManager,
    hapticManager: HapticManager,
    onBackToHome: () -> Unit,
    onNavigateNextLevel: (LevelData) -> Unit
) {
    BackHandler { onBackToHome() }
    val coroutineScope = rememberCoroutineScope()

    // State for regions
    val regionStates = remember(level.id) {
        val saved = preferences.loadRegionProgress(level.id)
        val initial = mutableStateMapOf<String, RegionState>()
        for (region in level.regions) {
            val savedState = saved[region.id]
            if (savedState != null) {
                initial[region.id] = savedState
            } else {
                initial[region.id] = RegionState(regionId = region.id)
            }
        }
        initial
    }

    // Undo / Redo history
    val undoStack = remember { mutableStateListOf<Map<String, RegionState>>() }
    val redoStack = remember { mutableStateListOf<Map<String, RegionState>>() }

    // Active color and tool
    var selectedColor by remember { mutableStateOf(level.regions.firstOrNull()?.targetColor ?: Color(0xFFFF4757)) }
    var activeTool by remember { mutableStateOf(ActiveTool.PEN) }

    // Touch & Animation state
    var touchPosition by remember { mutableStateOf<Offset?>(null) }
    var isCurrentlyRubbing by remember { mutableStateOf(false) }
    var currentlyRubbingRegionId by remember { mutableStateOf<String?>(null) }
    var hintRegionId by remember { mutableStateOf<String?>(null) }

    // Emitted ink sparkles & stars
    val sparkles = remember { mutableStateListOf<InkSparkle>() }

    // Dialogs
    var showGoalDialog by remember { mutableStateOf(false) }
    var isGhostGuideActive by remember { mutableStateOf(preferences.isGhostGuideEnabled) }
    var showVictoryDialog by remember { mutableStateOf(false) }
    var showResetConfirm by remember { mutableStateOf(false) }

    // Canvas size in pixels
    var canvasSize by remember { mutableStateOf(Offset.Zero) }

    // Completion metrics
    val totalRegions = level.regions.size
    val matchedCount = regionStates.values.count { it.isComplete && it.isColorMatched }
    val progressPercentage = if (totalRegions > 0) (matchedCount * 100) / totalRegions else 0
    val hasMismatches = regionStates.values.any { it.isComplete && !it.isColorMatched }

    // Sparkle animation loop
    LaunchedEffect(Unit) {
        while (true) {
            if (sparkles.isNotEmpty()) {
                val toRemove = mutableListOf<InkSparkle>()
                for (i in sparkles.indices) {
                    val s = sparkles[i]
                    val newAlpha = s.alpha - 0.05f
                    val newRadius = s.radius * 0.94f
                    if (newAlpha <= 0.05f || newRadius <= 1f) {
                        toRemove.add(s)
                    } else {
                        sparkles[i] = s.copy(alpha = newAlpha, radius = newRadius, y = s.y - 1.2f)
                    }
                }
                sparkles.removeAll(toRemove)
            }
            delay(16)
        }
    }

    // Spawn sparkles at position
    fun spawnInkSparkles(center: Offset, color: Color, count: Int = 3) {
        for (i in 0 until count) {
            val ox = (Random.nextFloat() - 0.5f) * 36f
            val oy = (Random.nextFloat() - 0.5f) * 36f
            sparkles.add(
                InkSparkle(
                    x = center.x + ox,
                    y = center.y + oy,
                    radius = Random.nextFloat() * 7f + 4f,
                    color = color,
                    alpha = 1f,
                    isStar = Random.nextBoolean()
                )
            )
        }
    }

    // Check level completion
    LaunchedEffect(matchedCount, totalRegions) {
        if (totalRegions > 0 && matchedCount == totalRegions && !showVictoryDialog) {
            preferences.setLevelCompleted(level.id, stars = 3)
            val nextLevelId = level.id + 1
            if (nextLevelId <= LevelDefinitions.allLevels.size) {
                preferences.unlockLevel(nextLevelId)
            }

            // Sync to Firestore if user is connected
            if (currentUser != null) {
                coroutineScope.launch {
                    cloudSyncRepository.pushProgressToCloud(currentUser, preferences)
                }
            }

            soundManager.playVictoryFanfare()
            hapticManager.triggerVictory()

            // Big victory celebration sparkle burst
            if (canvasSize != Offset.Zero) {
                spawnInkSparkles(Offset(canvasSize.x / 2f, canvasSize.y / 2f), Color(0xFFFFD214), 25)
                spawnInkSparkles(Offset(canvasSize.x / 2f, canvasSize.y / 2f), Color(0xFFFF4757), 20)
            }

            showVictoryDialog = true
        }
    }

    // Save region progress on changes
    LaunchedEffect(regionStates.toMap()) {
        preferences.saveRegionProgress(level.id, regionStates.toMap())
    }

    fun saveHistorySnapshot() {
        val snapshot = regionStates.toMap()
        undoStack.add(snapshot)
        redoStack.clear()
        if (undoStack.size > 25) {
            undoStack.removeAt(0)
        }
    }

    fun handleUndo() {
        if (undoStack.isNotEmpty()) {
            val previous = undoStack.removeAt(undoStack.lastIndex)
            redoStack.add(regionStates.toMap())
            regionStates.clear()
            regionStates.putAll(previous)
            soundManager.playBubblePop()
            hapticManager.triggerRegionCompleted()
        }
    }

    fun handleRedo() {
        if (redoStack.isNotEmpty()) {
            val next = redoStack.removeAt(redoStack.lastIndex)
            undoStack.add(regionStates.toMap())
            regionStates.clear()
            regionStates.putAll(next)
            soundManager.playBubblePop()
            hapticManager.triggerRegionCompleted()
        }
    }

    fun findRegionAt(x: Float, y: Float, w: Float, h: Float): ColoringRegion? {
        if (w <= 0 || h <= 0) return null
        for (region in level.regions) {
            val path = region.pathBuilder(w, h)
            val androidPath = path.asAndroidPath()
            val bounds = RectF()
            androidPath.computeBounds(bounds, true)
            val clipRegion = Region(
                bounds.left.toInt() - 2,
                bounds.top.toInt() - 2,
                bounds.right.toInt() + 2,
                bounds.bottom.toInt() + 2
            )
            val nativeRegion = Region()
            nativeRegion.setPath(androidPath, clipRegion)
            if (nativeRegion.contains(x.toInt(), y.toInt())) {
                return region
            }
        }
        return null
    }

    fun applyRubToPoint(point: Offset, deltaDist: Float) {
        val w = canvasSize.x
        val h = canvasSize.y
        if (w <= 0 || h <= 0) return

        val targetRegion = findRegionAt(point.x, point.y, w, h)
        if (targetRegion != null) {
            currentlyRubbingRegionId = targetRegion.id
            if (targetRegion.id == hintRegionId) {
                hintRegionId = null
            }

            val currentState = regionStates[targetRegion.id] ?: RegionState(regionId = targetRegion.id)

            if (activeTool == ActiveTool.ERASER) {
                if (currentState.fillPercentage > 0f) {
                    val newFill = (currentState.fillPercentage - (deltaDist / 180f)).coerceAtLeast(0f)
                    regionStates[targetRegion.id] = currentState.copy(
                        fillPercentage = newFill,
                        isComplete = false,
                        isColorMatched = false
                    )
                    soundManager.playMarkerFriction()
                    hapticManager.triggerRubTick()
                    spawnInkSparkles(point, Color(0xFFE2E8F0), 1)
                }
            } else {
                val isMatching = isColorSame(selectedColor, targetRegion.targetColor)
                val isNewColor = currentState.fillPercentage > 0f && !isColorSame(currentState.currentColor, selectedColor)
                val baseFill = if (isNewColor) 0.05f else currentState.fillPercentage

                val rubIncrement = (deltaDist / 200f).coerceIn(0.02f, 0.22f)
                val newFill = (baseFill + rubIncrement).coerceAtMost(1f)
                val justCompleted = newFill >= 1f && !currentState.isComplete

                regionStates[targetRegion.id] = currentState.copy(
                    currentColor = selectedColor,
                    fillPercentage = newFill,
                    isComplete = newFill >= 1f,
                    isColorMatched = newFill >= 1f && isMatching
                )

                soundManager.playMarkerFriction()
                hapticManager.triggerRubTick()

                // Emit ASMR ink sparkles from the drawing nib!
                spawnInkSparkles(point, selectedColor, if (justCompleted) 12 else 2)

                if (justCompleted) {
                    soundManager.playBubblePop()
                    hapticManager.triggerRegionCompleted()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .statusBarsPadding()
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = onBackToHome,
                        modifier = Modifier
                            .size(38.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${level.emoji} ${level.title}",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            if (currentUser != null) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = "☁️", fontSize = 12.sp)
                            }
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Progress: $progressPercentage%",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (progressPercentage == 100) Color(0xFF10B981) else MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "($matchedCount/$totalRegions matched)",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFEFF6FF),
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary),
                        modifier = Modifier.clickable { showGoalDialog = true }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Visibility,
                                contentDescription = "Goal View",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "GOAL",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                if (hasMismatches) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFFFEF3C7),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF59E0B)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(text = "⚠️", fontSize = 12.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Mismatched color detected! Match goal colors to pass.",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF92400E)
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFAFAFA))
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
                    .border(2.dp, Color(0xFFE2E8F0), RoundedCornerShape(24.dp))
                    .pointerInput(level.id, selectedColor, activeTool) {
                        detectTapGestures(
                            onPress = { offset ->
                                touchPosition = offset
                                isCurrentlyRubbing = true
                                saveHistorySnapshot()
                                applyRubToPoint(offset, 35f)
                                tryAwaitRelease()
                                touchPosition = null
                                isCurrentlyRubbing = false
                                currentlyRubbingRegionId = null
                            }
                        )
                    }
                    .pointerInput(level.id, selectedColor, activeTool) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                touchPosition = offset
                                isCurrentlyRubbing = true
                                saveHistorySnapshot()
                                applyRubToPoint(offset, 25f)
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                touchPosition = change.position
                                val dist = hypot(dragAmount.x, dragAmount.y)
                                applyRubToPoint(change.position, dist)
                            },
                            onDragEnd = {
                                touchPosition = null
                                isCurrentlyRubbing = false
                                currentlyRubbingRegionId = null
                            },
                            onDragCancel = {
                                touchPosition = null
                                isCurrentlyRubbing = false
                                currentlyRubbingRegionId = null
                            }
                        )
                    }
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    canvasSize = Offset(size.width, size.height)
                    val w = size.width
                    val h = size.height

                    // 1. Ghost Guide Stencil (faint 25% target colors on uncolored areas)
                    if (isGhostGuideActive) {
                        for (region in level.regions) {
                            val state = regionStates[region.id]
                            if (state == null || state.fillPercentage < 0.9f) {
                                val path = region.pathBuilder(w, h)
                                drawPath(
                                    path = path,
                                    color = region.targetColor.copy(alpha = 0.22f)
                                )
                            }
                        }
                    }

                    // 2. Filled regions (with rub reveal & rich ink opacity)
                    for (region in level.regions) {
                        val state = regionStates[region.id]
                        if (state != null && state.fillPercentage > 0f) {
                            val path = region.pathBuilder(w, h)
                            drawPath(
                                path = path,
                                color = state.currentColor.copy(alpha = state.fillPercentage)
                            )
                        }
                    }

                    // 3. Thick Black Outlines
                    for (region in level.regions) {
                        val path = region.pathBuilder(w, h)
                        drawPath(
                            path = path,
                            color = Color(0xFF1E293B),
                            style = Stroke(width = 5f)
                        )
                    }

                    // 4. Mismatch warning badge
                    for (region in level.regions) {
                        val state = regionStates[region.id]
                        if (state != null && state.isComplete && !state.isColorMatched) {
                            val cx = region.centerNormalizedX * w
                            val cy = region.centerNormalizedY * h
                            drawCircle(color = Color(0xFFEF4444), radius = 14f, center = Offset(cx, cy))
                            drawCircle(color = Color.White, radius = 10f, center = Offset(cx, cy))
                            drawCircle(color = Color(0xFFEF4444), radius = 4f, center = Offset(cx, cy))
                        }
                    }

                    // 5. Hint Golden Pulse
                    if (hintRegionId != null) {
                        val hintedRegion = level.regions.find { it.id == hintRegionId }
                        if (hintedRegion != null) {
                            val path = hintedRegion.pathBuilder(w, h)
                            drawPath(path = path, color = Color(0xFFFFD214).copy(alpha = 0.45f))
                            drawPath(path = path, color = Color(0xFFFFB300), style = Stroke(width = 7f))
                        }
                    }
                }

                // Extraordinary Marker Pen & Sparkle Emitter
                ExtraordinaryMarkerPen(
                    touchPos = touchPosition,
                    penColor = if (activeTool == ActiveTool.ERASER) Color.White else selectedColor,
                    sparkles = sparkles,
                    isRubbing = isCurrentlyRubbing
                )

                // Rubbing Progress Badge
                if (currentlyRubbingRegionId != null) {
                    val curState = regionStates[currentlyRubbingRegionId]
                    val pct = ((curState?.fillPercentage ?: 0f) * 100).toInt()
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xDD0F172A),
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(14.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = if (activeTool == ActiveTool.ERASER) "Erasing: $pct%" else "Filling: $pct%",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            // Tools Bar
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    IconButton(
                        onClick = { handleUndo() },
                        enabled = undoStack.isNotEmpty()
                    ) {
                        Icon(imageVector = Icons.Default.Undo, contentDescription = "Undo")
                    }

                    IconButton(
                        onClick = { handleRedo() },
                        enabled = redoStack.isNotEmpty()
                    ) {
                        Icon(imageVector = Icons.Default.Redo, contentDescription = "Redo")
                    }

                    FilledTonalIconButton(
                        onClick = { activeTool = ActiveTool.PEN },
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = if (activeTool == ActiveTool.PEN) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Marker Pen",
                            tint = if (activeTool == ActiveTool.PEN) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    FilledTonalIconButton(
                        onClick = { activeTool = ActiveTool.ERASER },
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = if (activeTool == ActiveTool.ERASER) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoFixNormal,
                            contentDescription = "Eraser",
                            tint = if (activeTool == ActiveTool.ERASER) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    IconButton(
                        onClick = {
                            val uncompleted = level.regions.firstOrNull {
                                val s = regionStates[it.id]
                                s == null || !s.isComplete || !s.isColorMatched
                            }
                            if (uncompleted != null) {
                                hintRegionId = uncompleted.id
                                selectedColor = uncompleted.targetColor
                                activeTool = ActiveTool.PEN
                                soundManager.playChime(1)
                                hapticManager.triggerRegionCompleted()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = "Hint",
                            tint = Color(0xFFF59E0B)
                        )
                    }

                    IconButton(onClick = { showResetConfirm = true }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Reset",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Target Colors Quick-Bar
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 14.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "Goal Colors for this Art (Tap to Equip):",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(4.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(level.regions) { region ->
                        val isSelected = isColorSame(selectedColor, region.targetColor) && activeTool == ActiveTool.PEN
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(34.dp)
                                .scale(if (isSelected) 1.15f else 1f)
                                .clip(CircleShape)
                                .background(region.targetColor)
                                .border(
                                    if (isSelected) 3.dp else 1.5.dp,
                                    if (isSelected) MaterialTheme.colorScheme.primary else Color(0xFFCBD5E1),
                                    CircleShape
                                )
                                .clickable {
                                    selectedColor = region.targetColor
                                    activeTool = ActiveTool.PEN
                                    soundManager.playBubblePop()
                                    hapticManager.triggerRubTick()
                                }
                        ) {
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Full Color Palette
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(top = 4.dp, bottom = 8.dp)
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(LevelDefinitions.defaultPalette) { color ->
                        val isSelected = isColorSame(selectedColor, color) && activeTool == ActiveTool.PEN
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(44.dp)
                                .scale(if (isSelected) 1.18f else 1f)
                                .clip(CircleShape)
                                .background(color)
                                .border(
                                    if (isSelected) 3.5.dp else 2.dp,
                                    if (isSelected) MaterialTheme.colorScheme.primary else Color.White,
                                    CircleShape
                                )
                                .clickable {
                                    selectedColor = color
                                    activeTool = ActiveTool.PEN
                                    soundManager.playBubblePop()
                                    hapticManager.triggerRubTick()
                                }
                        ) {
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = if (color == Color.White) Color.Black else Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showGoalDialog) {
        GoalPreviewDialog(
            level = level,
            isGhostGuideActive = isGhostGuideActive,
            onToggleGhostGuide = {
                isGhostGuideActive = it
                preferences.isGhostGuideEnabled = it
            },
            onSelectColor = { color ->
                selectedColor = color
                activeTool = ActiveTool.PEN
            },
            onDismiss = { showGoalDialog = false }
        )
    }

    if (showVictoryDialog) {
        val nextLevel = LevelDefinitions.allLevels.find { it.id == level.id + 1 }
        LevelVictoryDialog(
            level = level,
            hasNextLevel = nextLevel != null,
            onNextLevel = {
                showVictoryDialog = false
                if (nextLevel != null) {
                    onNavigateNextLevel(nextLevel)
                } else {
                    onBackToHome()
                }
            },
            onReplay = {
                showVictoryDialog = false
                regionStates.clear()
                for (r in level.regions) {
                    regionStates[r.id] = RegionState(regionId = r.id)
                }
            },
            onHome = {
                showVictoryDialog = false
                onBackToHome()
            }
        )
    }

    if (showResetConfirm) {
        AlertDialog(
            onDismissRequest = { showResetConfirm = false },
            title = { Text(text = "Reset This Picture?") },
            text = { Text(text = "Are you sure you want to clear all colored areas back to blank canvas?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showResetConfirm = false
                        saveHistorySnapshot()
                        regionStates.clear()
                        for (r in level.regions) {
                            regionStates[r.id] = RegionState(r.id)
                        }
                    }
                ) {
                    Text(text = "Clear All", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetConfirm = false }) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}

private fun isColorSame(c1: Color, c2: Color): Boolean {
    val rDiff = kotlin.math.abs(c1.red - c2.red)
    val gDiff = kotlin.math.abs(c1.green - c2.green)
    val bDiff = kotlin.math.abs(c1.blue - c2.blue)
    return rDiff < 0.08f && gDiff < 0.08f && bDiff < 0.08f
}
