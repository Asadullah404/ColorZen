package com.example.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.sin
import kotlin.random.Random

class AsmrSoundManager {

    private val sampleRate = 22050
    private val scope = CoroutineScope(Dispatchers.Default)

    var isSoundEnabled: Boolean = true
    var isMusicEnabled: Boolean = true

    private var lastFrictionTime = 0L

    fun playMarkerFriction() {
        if (!isSoundEnabled) return
        val now = System.currentTimeMillis()
        if (now - lastFrictionTime < 60) return // throttle to keep smooth
        lastFrictionTime = now

        scope.launch {
            try {
                val durationMs = 45
                val numSamples = (sampleRate * (durationMs / 1000f)).toInt()
                val buffer = ShortArray(numSamples)
                val random = Random(System.nanoTime())
                val freq = 420.0 + random.nextInt(80)

                for (i in 0 until numSamples) {
                    val envelope = sin(Math.PI * i / numSamples).toFloat()
                    val noise = (random.nextFloat() * 2f - 1f) * 0.4f
                    val tone = sin(2.0 * Math.PI * i * freq / sampleRate).toFloat() * 0.6f
                    val sample = (noise + tone) * envelope * 0.25f
                    buffer[i] = (sample * Short.MAX_VALUE).toInt().toShort()
                }

                playBuffer(buffer)
            } catch (_: Exception) {}
        }
    }

    fun playBubblePop() {
        if (!isSoundEnabled) return
        scope.launch {
            try {
                val durationMs = 70
                val numSamples = (sampleRate * (durationMs / 1000f)).toInt()
                val buffer = ShortArray(numSamples)

                for (i in 0 until numSamples) {
                    val t = i.toFloat() / numSamples
                    val freq = 260.0 + (t * 400.0) // rising pop frequency
                    val envelope = (1f - t) * sin(Math.PI * t).toFloat()
                    val sample = sin(2.0 * Math.PI * i * freq / sampleRate).toFloat() * envelope * 0.55f
                    buffer[i] = (sample * Short.MAX_VALUE).toInt().toShort()
                }

                playBuffer(buffer)
            } catch (_: Exception) {}
        }
    }

    fun playChime(noteIndex: Int = 0) {
        if (!isSoundEnabled) return
        scope.launch {
            try {
                val frequencies = listOf(523.25, 659.25, 783.99, 1046.50) // C5, E5, G5, C6
                val freq = frequencies[noteIndex % frequencies.size]
                val durationMs = 280
                val numSamples = (sampleRate * (durationMs / 1000f)).toInt()
                val buffer = ShortArray(numSamples)

                for (i in 0 until numSamples) {
                    val t = i.toFloat() / numSamples
                    val decay = Math.exp((-4.0 * t)).toFloat()
                    val tone = (sin(2.0 * Math.PI * i * freq / sampleRate) * 0.7 +
                            sin(4.0 * Math.PI * i * freq / sampleRate) * 0.3).toFloat()
                    val sample = tone * decay * 0.45f
                    buffer[i] = (sample * Short.MAX_VALUE).toInt().toShort()
                }

                playBuffer(buffer)
            } catch (_: Exception) {}
        }
    }

    fun playVictoryFanfare() {
        if (!isSoundEnabled) return
        scope.launch {
            val notes = listOf(523.25, 659.25, 783.99, 1046.50, 1318.51)
            for (freq in notes) {
                try {
                    val durationMs = 150
                    val numSamples = (sampleRate * (durationMs / 1000f)).toInt()
                    val buffer = ShortArray(numSamples)
                    for (i in 0 until numSamples) {
                        val t = i.toFloat() / numSamples
                        val decay = Math.exp((-3.5 * t)).toFloat()
                        val tone = sin(2.0 * Math.PI * i * freq / sampleRate).toFloat()
                        buffer[i] = (tone * decay * 0.5f * Short.MAX_VALUE).toInt().toShort()
                    }
                    playBuffer(buffer)
                    kotlinx.coroutines.delay(100)
                } catch (_: Exception) {}
            }
        }
    }

    private fun playBuffer(buffer: ShortArray) {
        val audioTrack = AudioTrack.Builder()
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )
            .setAudioFormat(
                AudioFormat.Builder()
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .setSampleRate(sampleRate)
                    .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                    .build()
            )
            .setBufferSizeInBytes(buffer.size * 2)
            .setTransferMode(AudioTrack.MODE_STATIC)
            .build()

        audioTrack.write(buffer, 0, buffer.size)
        audioTrack.play()
        // Release after finish
        scope.launch {
            kotlinx.coroutines.delay(buffer.size * 1000L / sampleRate + 50)
            try {
                audioTrack.stop()
                audioTrack.release()
            } catch (_: Exception) {}
        }
    }
}
