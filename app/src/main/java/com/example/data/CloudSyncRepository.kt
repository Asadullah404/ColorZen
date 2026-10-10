package com.example.data

import android.content.Context
import android.util.Log
import com.example.R
import com.example.storage.GamePreferences
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class CloudSyncRepository(private val context: Context) {

    private val db: FirebaseFirestore by lazy {
        val dbId = context.getString(R.string.firestore_database_id)
        FirebaseFirestore.getInstance(FirebaseApp.getInstance(), dbId)
    }

    suspend fun syncWithCloud(user: FirebaseUser, preferences: GamePreferences) {
        val uid = user.uid
        val docRef = db.collection("users").document(uid).collection("progress").document("game_save")

        try {
            val snapshot = docRef.get().await()
            if (snapshot.exists()) {
                val cloudUnlocked = (snapshot.get("unlockedLevels") as? List<*>)?.mapNotNull { (it as? Number)?.toInt() } ?: emptyList()
                val cloudCompleted = (snapshot.get("completedLevels") as? List<*>)?.mapNotNull { (it as? Number)?.toInt() } ?: emptyList()
                val cloudLevelStars = (snapshot.get("levelStars") as? Map<*, *>) ?: emptyMap<Any, Any>()

                // Merge into local
                for (lvl in cloudUnlocked) {
                    preferences.unlockLevel(lvl)
                }
                for (lvl in cloudCompleted) {
                    val stars = (cloudLevelStars[lvl.toString()] as? Number)?.toInt() ?: 3
                    val currentStars = preferences.getLevelStars(lvl)
                    if (stars > currentStars) {
                        preferences.setLevelCompleted(lvl, stars)
                    }
                }
            }

            // Push current state to cloud
            pushProgressToCloud(user, preferences)
        } catch (e: Exception) {
            Log.e("CloudSyncRepository", "Cloud sync error: ${e.message}", e)
        }
    }

    suspend fun pushProgressToCloud(user: FirebaseUser, preferences: GamePreferences) {
        val uid = user.uid
        val docRef = db.collection("users").document(uid).collection("progress").document("game_save")

        val unlockedList = mutableListOf<Int>()
        val completedList = mutableListOf<Int>()
        val starsMap = mutableMapOf<String, Int>()

        for (i in 1..40) {
            if (preferences.isLevelUnlocked(i)) unlockedList.add(i)
            if (preferences.isLevelCompleted(i)) {
                completedList.add(i)
                starsMap[i.toString()] = preferences.getLevelStars(i)
            }
        }

        val payload = mapOf(
            "userId" to uid,
            "totalStars" to preferences.getTotalStars(),
            "unlockedLevels" to unlockedList,
            "completedLevels" to completedList,
            "levelStars" to starsMap,
            "updatedAt" to FieldValue.serverTimestamp()
        )

        try {
            docRef.set(payload, SetOptions.merge()).await()
            Log.d("CloudSyncRepository", "Cloud progress saved successfully!")
        } catch (e: Exception) {
            Log.e("CloudSyncRepository", "Failed to push progress to cloud", e)
        }
    }
}
