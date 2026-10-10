package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.LevelDefinitions
import com.example.model.ChapterType
import com.example.model.LevelData
import com.example.model.RegionState
import com.example.storage.GamePreferences
import com.google.firebase.auth.FirebaseUser

@Composable
fun HomeScreen(
    preferences: GamePreferences,
    currentUser: FirebaseUser?,
    onGoogleSignInClicked: () -> Unit,
    onManualSync: () -> Unit,
    onSelectLevel: (LevelData) -> Unit,
    onOpenSettings: () -> Unit
) {
    var selectedChapter by remember { mutableStateOf(ChapterType.SHAPES) }
    var showGoalPreviewMode by remember { mutableStateOf(false) }

    val chapterLevels = remember(selectedChapter) {
        LevelDefinitions.getLevelsForChapter(selectedChapter)
    }

    val totalStars = remember(preferences) {
        preferences.getTotalStars()
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                // Top header row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    brush = Brush.linearGradient(
                                        listOf(Color(0xFF6366F1), Color(0xFFEC4899))
                                    ),
                                    shape = RoundedCornerShape(12.dp)
                                )
                        ) {
                            Text(text = "🎨", fontSize = 20.sp)
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {
                            Text(
                                text = "ColorZen ASMR",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Relaxing Art & Coloring",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFFFFF3CD),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFFD214)),
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = Color(0xFFF59E0B),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "$totalStars",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF92400E)
                                )
                            }
                        }

                        IconButton(
                            onClick = onOpenSettings,
                            modifier = Modifier
                                .size(38.dp)
                                .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(20.dp)
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
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Google Cloud Save / Google Play Connection Strip
            CloudSyncBanner(
                currentUser = currentUser,
                onConnectClicked = onGoogleSignInClicked,
                onSyncClicked = onManualSync
            )

            // Daily Challenge Card
            DailyChallengeBanner(
                preferences = preferences,
                onStartDaily = { onSelectLevel(LevelDefinitions.getDailyLevel()) }
            )

            // Chapter Tabs
            ChapterTabBar(
                selectedChapter = selectedChapter,
                onSelectChapter = { selectedChapter = it },
                preferences = preferences
            )

            // Preview Mode Toggle Bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "${chapterLevels.size} Original Levels",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Toggle: Goal View vs My Canvas
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(3.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(9.dp))
                            .background(if (!showGoalPreviewMode) MaterialTheme.colorScheme.primary else Color.Transparent)
                            .clickable { showGoalPreviewMode = false }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "My Canvas",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (!showGoalPreviewMode) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(9.dp))
                            .background(if (showGoalPreviewMode) MaterialTheme.colorScheme.primary else Color.Transparent)
                            .clickable { showGoalPreviewMode = true }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Goal Art",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (showGoalPreviewMode) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Grid of Level Cards
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(chapterLevels) { level ->
                    LevelCard(
                        level = level,
                        isUnlocked = preferences.isLevelUnlocked(level.id),
                        isCompleted = preferences.isLevelCompleted(level.id),
                        stars = preferences.getLevelStars(level.id),
                        savedRegions = preferences.loadRegionProgress(level.id),
                        showGoalArt = showGoalPreviewMode,
                        onClick = {
                            if (preferences.isLevelUnlocked(level.id)) {
                                onSelectLevel(level)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CloudSyncBanner(
    currentUser: FirebaseUser?,
    onConnectClicked: () -> Unit,
    onSyncClicked: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = if (currentUser != null) Color(0xFFF0FDF4) else Color(0xFFEFF6FF),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (currentUser != null) Color(0xFF86EFAC) else Color(0xFF93C5FD)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = if (currentUser != null) "☁️" else "🎮", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = if (currentUser != null) "Google Account Connected" else "Connect Google Account",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (currentUser != null) Color(0xFF15803D) else MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = if (currentUser != null) (currentUser.displayName ?: "Progress safely backed up") else "Save your coloring progress to cloud",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (currentUser != null) {
                IconButton(
                    onClick = onSyncClicked,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Sync,
                        contentDescription = "Sync",
                        tint = Color(0xFF16A34A),
                        modifier = Modifier.size(18.dp)
                    )
                }
            } else {
                Button(
                    onClick = onConnectClicked,
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(text = "Connect", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun DailyChallengeBanner(
    preferences: GamePreferences,
    onStartDaily: () -> Unit
) {
    val dailyLevel = LevelDefinitions.getDailyLevel()
    val isCompleted = preferences.isLevelCompleted(dailyLevel.id)

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF9E6)
        ),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFFFD214)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(46.dp)
                        .background(Color(0xFFFFD214), CircleShape)
                ) {
                    Text(text = "🐝", fontSize = 24.sp)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "DAILY CHALLENGE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFFD97706)
                        )
                        if (isCompleted) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "✅ Done", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF16A34A))
                        }
                    }
                    Text(
                        text = "Honey Bee & Sunflower",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF78350F)
                    )
                }
            }

            Button(
                onClick = onStartDaily,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isCompleted) Color(0xFF16A34A) else Color(0xFFD97706)
                ),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = if (isCompleted) "Replay" else "Color",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun ChapterTabBar(
    selectedChapter: ChapterType,
    onSelectChapter: (ChapterType) -> Unit,
    preferences: GamePreferences
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        ChapterType.entries.forEach { chapter ->
            val isSelected = chapter == selectedChapter
            val totalInChapter = LevelDefinitions.getLevelsForChapter(chapter).size
            val completedInChapter = LevelDefinitions.getLevelsForChapter(chapter).count {
                preferences.isLevelCompleted(it.id)
            }

            Surface(
                shape = RoundedCornerShape(14.dp),
                color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                ),
                modifier = Modifier
                    .weight(1f)
                    .clickable { onSelectChapter(chapter) }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
                ) {
                    Text(text = chapter.icon, fontSize = 20.sp)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = when (chapter) {
                            ChapterType.SHAPES -> "Shapes"
                            ChapterType.FRUITS -> "Fruits"
                            ChapterType.COZY_ART -> "Cozy Art"
                        },
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "$completedInChapter/$totalInChapter",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun LevelCard(
    level: LevelData,
    isUnlocked: Boolean,
    isCompleted: Boolean,
    stars: Int,
    savedRegions: Map<String, RegionState>,
    showGoalArt: Boolean,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(
            1.5.dp,
            if (isCompleted) Color(0xFF10B981) else if (isUnlocked) MaterialTheme.colorScheme.outlineVariant else Color(0xFFE2E8F0)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isUnlocked) 3.dp else 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = isUnlocked, onClick = onClick)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(115.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color.White)
                    .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(14.dp))
            ) {
                Canvas(modifier = Modifier.fillMaxSize().padding(6.dp)) {
                    val w = size.width
                    val h = size.height

                    for (region in level.regions) {
                        val path = region.pathBuilder(w, h)
                        val colorToDraw = if (showGoalArt) {
                            region.targetColor
                        } else {
                            val saved = savedRegions[region.id]
                            if (saved != null && saved.fillPercentage > 0.05f) {
                                saved.currentColor.copy(alpha = saved.fillPercentage)
                            } else {
                                Color.White
                            }
                        }
                        drawPath(path = path, color = colorToDraw)
                    }

                    for (region in level.regions) {
                        val path = region.pathBuilder(w, h)
                        drawPath(
                            path = path,
                            color = Color(0xFF2D3748),
                            style = Stroke(width = 3.5f)
                        )
                    }
                }

                if (!isUnlocked) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0x990F172A))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Locked",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${level.id}. ${level.title}",
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            if (isUnlocked) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(3) { index ->
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = if (index < stars) Color(0xFFF59E0B) else Color(0xFFCBD5E1),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            } else {
                Text(
                    text = "🔒 Locked",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF94A3B8)
                )
            }
        }
    }
}
