package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.audio.AsmrSoundManager
import com.example.auth.GoogleAuthManager
import com.example.data.CloudSyncRepository
import com.example.haptics.HapticManager
import com.example.model.LevelData
import com.example.storage.GamePreferences
import com.example.ui.components.SettingsDialog
import com.example.ui.screens.GameScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

sealed class AppScreen {
    object Home : AppScreen()
    data class Game(val level: LevelData) : AppScreen()
}

class MainActivity : ComponentActivity() {

    private val soundManager = AsmrSoundManager()
    private lateinit var authManager: GoogleAuthManager
    private lateinit var cloudSyncRepo: CloudSyncRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        authManager = GoogleAuthManager(this)
        cloudSyncRepo = CloudSyncRepository(this)

        setContent {
            val context = LocalContext.current
            val coroutineScope = rememberCoroutineScope()
            val preferences = remember { GamePreferences(context) }
            val hapticManager = remember { HapticManager(context) }
            val currentUser by authManager.currentUser.collectAsState()

            // Silent auto sign-in and cloud sync on startup
            LaunchedEffect(Unit) {
                authManager.attemptSilentSignIn(coroutineScope) { success ->
                    if (success) {
                        coroutineScope.launch {
                            val user = authManager.currentUser.value
                            if (user != null) {
                                cloudSyncRepo.syncWithCloud(user, preferences)
                            }
                        }
                    }
                }
            }

            // Sync settings with managers
            LaunchedEffect(preferences.isSoundEnabled, preferences.isMusicEnabled, preferences.isHapticEnabled) {
                soundManager.isSoundEnabled = preferences.isSoundEnabled
                soundManager.isMusicEnabled = preferences.isMusicEnabled
                hapticManager.isHapticEnabled = preferences.isHapticEnabled
            }

            var currentScreen by remember { mutableStateOf<AppScreen>(AppScreen.Home) }
            var showSettingsDialog by remember { mutableStateOf(false) }

            fun triggerGoogleSignIn() {
                authManager.signInWithGoogle(
                    activity = this@MainActivity,
                    scope = coroutineScope,
                    onSuccess = { user ->
                        Toast.makeText(context, "Connected as ${user.displayName ?: "Google User"}! Syncing...", Toast.LENGTH_SHORT).show()
                        coroutineScope.launch {
                            cloudSyncRepo.syncWithCloud(user, preferences)
                        }
                    },
                    onError = { err ->
                        Toast.makeText(context, err, Toast.LENGTH_LONG).show()
                    }
                )
            }

            fun triggerManualSync() {
                val user = currentUser
                if (user != null) {
                    coroutineScope.launch {
                        cloudSyncRepo.syncWithCloud(user, preferences)
                        Toast.makeText(context, "Progress synced with cloud! ☁️", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    triggerGoogleSignIn()
                }
            }

            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AnimatedContent(
                        targetState = currentScreen,
                        transitionSpec = { fadeIn() togetherWith fadeOut() },
                        label = "screen_transition"
                    ) { screen ->
                        when (screen) {
                            is AppScreen.Home -> {
                                HomeScreen(
                                    preferences = preferences,
                                    currentUser = currentUser,
                                    onGoogleSignInClicked = { triggerGoogleSignIn() },
                                    onManualSync = { triggerManualSync() },
                                    onSelectLevel = { level ->
                                        currentScreen = AppScreen.Game(level)
                                    },
                                    onOpenSettings = {
                                        showSettingsDialog = true
                                    }
                                )
                            }
                            is AppScreen.Game -> {
                                GameScreen(
                                    level = screen.level,
                                    preferences = preferences,
                                    currentUser = currentUser,
                                    cloudSyncRepository = cloudSyncRepo,
                                    soundManager = soundManager,
                                    hapticManager = hapticManager,
                                    onBackToHome = {
                                        currentScreen = AppScreen.Home
                                    },
                                    onNavigateNextLevel = { nextLevel ->
                                        currentScreen = AppScreen.Game(nextLevel)
                                    }
                                )
                            }
                        }
                    }

                    if (showSettingsDialog) {
                        SettingsDialog(
                            currentUser = currentUser,
                            onGoogleSignInClicked = { triggerGoogleSignIn() },
                            onSignOutClicked = {
                                authManager.signOut(coroutineScope) {
                                    Toast.makeText(context, "Signed out from Google", Toast.LENGTH_SHORT).show()
                                }
                            },
                            isSoundEnabled = preferences.isSoundEnabled,
                            onToggleSound = {
                                preferences.isSoundEnabled = it
                                soundManager.isSoundEnabled = it
                            },
                            isMusicEnabled = preferences.isMusicEnabled,
                            onToggleMusic = {
                                preferences.isMusicEnabled = it
                                soundManager.isMusicEnabled = it
                            },
                            isHapticEnabled = preferences.isHapticEnabled,
                            onToggleHaptic = {
                                preferences.isHapticEnabled = it
                                hapticManager.isHapticEnabled = it
                            },
                            isGhostGuideEnabled = preferences.isGhostGuideEnabled,
                            onToggleGhostGuide = {
                                preferences.isGhostGuideEnabled = it
                            },
                            onResetProgress = {
                                preferences.resetAllProgress()
                                currentScreen = AppScreen.Home
                            },
                            onDismiss = { showSettingsDialog = false }
                        )
                    }
                }
            }
        }
    }
}
