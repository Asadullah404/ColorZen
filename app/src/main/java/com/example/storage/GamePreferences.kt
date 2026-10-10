package com.example.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.ui.graphics.Color
import com.example.model.RegionState

class GamePreferences(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("colorzen_game_prefs", Context.MODE_PRIVATE)

    fun isLevelUnlocked(levelId: Int): Boolean {
        if (levelId == 1) return true
        return prefs.getBoolean("level_unlocked_$levelId", false)
    }

    fun unlockLevel(levelId: Int) {
        prefs.edit().putBoolean("level_unlocked_$levelId", true).apply()
    }

    fun isLevelCompleted(levelId: Int): Boolean {
        return prefs.getBoolean("level_completed_$levelId", false)
    }

    fun setLevelCompleted(levelId: Int, stars: Int = 3) {
        prefs.edit()
            .putBoolean("level_completed_$levelId", true)
            .putInt("level_stars_$levelId", stars)
            .apply()
    }

    fun getLevelStars(levelId: Int): Int {
        return prefs.getInt("level_stars_$levelId", 0)
    }

    fun getTotalStars(): Int {
        var total = 0
        for (i in 1..40) {
            total += getLevelStars(i)
        }
        return total
    }

    fun saveRegionProgress(levelId: Int, regions: Map<String, RegionState>) {
        val editor = prefs.edit()
        val count = regions.size
        editor.putInt("saved_regions_count_$levelId", count)
        var idx = 0
        for ((regionId, state) in regions) {
            val colorHex = state.currentColor.value.toLong()
            editor.putString("reg_${levelId}_${idx}_id", regionId)
            editor.putLong("reg_${levelId}_${idx}_color", colorHex)
            editor.putFloat("reg_${levelId}_${idx}_progress", state.fillPercentage)
            editor.putBoolean("reg_${levelId}_${idx}_complete", state.isComplete)
            editor.putBoolean("reg_${levelId}_${idx}_matched", state.isColorMatched)
            idx++
        }
        editor.apply()
    }

    fun loadRegionProgress(levelId: Int): Map<String, RegionState> {
        val count = prefs.getInt("saved_regions_count_$levelId", 0)
        if (count == 0) return emptyMap()
        val map = mutableMapOf<String, RegionState>()
        for (i in 0 until count) {
            val id = prefs.getString("reg_${levelId}_${i}_id", null) ?: continue
            val colorHex = prefs.getLong("reg_${levelId}_${i}_color", 0xFFFFFFFFL)
            val progress = prefs.getFloat("reg_${levelId}_${i}_progress", 0f)
            val isComplete = prefs.getBoolean("reg_${levelId}_${i}_complete", false)
            val isMatched = prefs.getBoolean("reg_${levelId}_${i}_matched", false)
            map[id] = RegionState(
                regionId = id,
                currentColor = Color(colorHex.toULong()),
                fillPercentage = progress,
                isComplete = isComplete,
                isColorMatched = isMatched
            )
        }
        return map
    }

    var isSoundEnabled: Boolean
        get() = prefs.getBoolean("setting_sound", true)
        set(value) = prefs.edit().putBoolean("setting_sound", value).apply()

    var isMusicEnabled: Boolean
        get() = prefs.getBoolean("setting_music", true)
        set(value) = prefs.edit().putBoolean("setting_music", value).apply()

    var isHapticEnabled: Boolean
        get() = prefs.getBoolean("setting_haptic", true)
        set(value) = prefs.edit().putBoolean("setting_haptic", value).apply()

    var isGhostGuideEnabled: Boolean
        get() = prefs.getBoolean("setting_ghost_guide", false)
        set(value) = prefs.edit().putBoolean("setting_ghost_guide", value).apply()

    fun resetAllProgress() {
        prefs.edit().clear().apply()
    }
}
