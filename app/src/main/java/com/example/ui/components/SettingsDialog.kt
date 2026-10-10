package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.google.firebase.auth.FirebaseUser

@Composable
fun SettingsDialog(
    currentUser: FirebaseUser?,
    onGoogleSignInClicked: () -> Unit,
    onSignOutClicked: () -> Unit,
    isSoundEnabled: Boolean,
    onToggleSound: (Boolean) -> Unit,
    isMusicEnabled: Boolean,
    onToggleMusic: (Boolean) -> Unit,
    isHapticEnabled: Boolean,
    onToggleHaptic: (Boolean) -> Unit,
    isGhostGuideEnabled: Boolean,
    onToggleGhostGuide: (Boolean) -> Unit,
    onResetProgress: () -> Unit,
    onDismiss: () -> Unit
) {
    var showResetConfirm by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                // Header
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Settings",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Google Account / Cloud Save Card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (currentUser != null) Color(0xFFF0FDF4) else Color(0xFFEFF6FF)
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (currentUser != null) Color(0xFF86EFAC) else Color(0xFF93C5FD)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(
                                        if (currentUser != null) Color(0xFF16A34A) else MaterialTheme.colorScheme.primary,
                                        CircleShape
                                    )
                            ) {
                                Text(text = if (currentUser != null) "☁️" else "G", fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = if (currentUser != null) "Cloud Save Active" else "Google Cloud Save",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (currentUser != null) Color(0xFF15803D) else MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = currentUser?.displayName ?: currentUser?.email ?: "Backup progress to Google account",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1
                                )
                            }
                        }

                        if (currentUser != null) {
                            TextButton(onClick = onSignOutClicked) {
                                Text(text = "Sign Out", fontSize = 12.sp, color = Color(0xFFDC2626))
                            }
                        } else {
                            Button(
                                onClick = onGoogleSignInClicked,
                                shape = RoundedCornerShape(10.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(text = "Connect", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Toggle items
                SettingToggleItem(
                    icon = Icons.Default.VolumeUp,
                    title = "Sound Effects",
                    subtitle = "ASMR felt friction, pop & chimes",
                    checked = isSoundEnabled,
                    onCheckedChange = onToggleSound
                )

                SettingToggleItem(
                    icon = Icons.Default.MusicNote,
                    title = "Relaxing Music",
                    subtitle = "Calming ambient soundscape",
                    checked = isMusicEnabled,
                    onCheckedChange = onToggleMusic
                )

                SettingToggleItem(
                    icon = Icons.Default.Vibration,
                    title = "Haptic Feedback",
                    subtitle = "Subtle tactile vibrations",
                    checked = isHapticEnabled,
                    onCheckedChange = onToggleHaptic
                )

                SettingToggleItem(
                    icon = Icons.Default.Visibility,
                    title = "Ghost Guide Stencil",
                    subtitle = "Show target colors lightly on canvas",
                    checked = isGhostGuideEnabled,
                    onCheckedChange = onToggleGhostGuide
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Reset progress button
                OutlinedButton(
                    onClick = { showResetConfirm = true },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Default.DeleteOutline, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Reset All Game Progress", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "ColorZen ASMR • Version 1.1.0 • Cloud Sync",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }

    if (showResetConfirm) {
        AlertDialog(
            onDismissRequest = { showResetConfirm = false },
            title = { Text(text = "Reset Progress?") },
            text = { Text(text = "This will lock all levels except Level 1 and reset all stars and colors. This cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showResetConfirm = false
                        onResetProgress()
                    }
                ) {
                    Text(text = "Reset Everything", color = MaterialTheme.colorScheme.error)
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

@Composable
private fun SettingToggleItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text(text = subtitle, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
