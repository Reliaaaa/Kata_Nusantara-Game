package com.example.data.local

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.sin

object AudioEffects {

    private val audioScope = CoroutineScope(Dispatchers.Default)
    var isSoundEnabled: Boolean = true
    var isBgmEnabled: Boolean = true
    var bgmVolume: Float = 0.5f

    private var bgmJob: Job? = null
    private var activeBgmTrack: AudioTrack? = null

    fun startBgm(trackType: String = "MYSTERY") {
        stopBgm()
        if (!isBgmEnabled) return

        bgmJob = audioScope.launch {
            try {
                val sampleRate = 22050
                // Pentatonic Gamelan Slendro/Pelog Frequencies (Hz)
                val baseNotes = if (trackType == "COURT") {
                    doubleArrayOf(196.0, 220.0, 261.63, 293.66, 329.63, 392.0, 440.0, 523.25)
                } else {
                    doubleArrayOf(220.0, 246.94, 293.66, 329.63, 392.00, 440.00, 523.25, 587.33)
                }

                val durationSec = 4
                val bufferSize = sampleRate * durationSec
                val pcmData = ShortArray(bufferSize)

                for (i in 0 until bufferSize) {
                    val timeSec = i.toDouble() / sampleRate

                    // Deep Javanese Gong resonance drone (110Hz + 220Hz)
                    val drone = sin(2.0 * Math.PI * 110.0 * timeSec) * 0.12 + sin(2.0 * Math.PI * 220.0 * timeSec) * 0.08

                    // Bonang/Saron percussive chime notes
                    val tempo = if (trackType == "COURT") 3.5 else 2.2
                    val noteInterval = (timeSec * tempo).toInt()
                    val notePhase = (timeSec * tempo) - noteInterval
                    val noteEnvelope = kotlin.math.exp(-3.8 * notePhase)

                    val noteIndex = (noteInterval * 3 + (noteInterval % 5)) % baseNotes.size
                    val freq = baseNotes[noteIndex]
                    val chime = sin(2.0 * Math.PI * freq * timeSec) * noteEnvelope * 0.22

                    // Bronze overtone shimmer
                    val shimmer = sin(2.0 * Math.PI * (freq * 2.76) * timeSec) * noteEnvelope * 0.06

                    val sampleValue = (drone + chime + shimmer) * bgmVolume * Short.MAX_VALUE
                    pcmData[i] = sampleValue.toInt().coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
                }

                val track = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_GAME)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(sampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(pcmData.size * 2)
                    .setTransferMode(AudioTrack.MODE_STATIC)
                    .build()

                track.write(pcmData, 0, pcmData.size)
                track.setLoopPoints(0, pcmData.size, -1)
                track.play()
                activeBgmTrack = track
            } catch (_: Exception) {
                // Fallback gracefully
            }
        }
    }

    fun stopBgm() {
        try {
            bgmJob?.cancel()
            bgmJob = null
            activeBgmTrack?.stop()
            activeBgmTrack?.release()
            activeBgmTrack = null
        } catch (_: Exception) {}
    }

    fun toggleBgm(enabled: Boolean, trackType: String = "MYSTERY") {
        isBgmEnabled = enabled
        if (enabled) {
            startBgm(trackType)
        } else {
            stopBgm()
        }
    }

    fun playClick() {
        if (!isSoundEnabled) return
        audioScope.launch {
            playSingleTone(800.0, 15, 0.3f)
        }
    }

    fun playCorrect() {
        if (!isSoundEnabled) return
        audioScope.launch {
            playSingleTone(523.25, 70, 0.4f) // C5
            playSingleTone(659.25, 70, 0.45f) // E5
            playSingleTone(783.99, 120, 0.5f) // G5
        }
    }

    fun playWrong() {
        if (!isSoundEnabled) return
        audioScope.launch {
            playSingleTone(220.0, 90, 0.4f)
            playSingleTone(164.81, 140, 0.45f)
        }
    }

    fun playClueUnlocked() {
        if (!isSoundEnabled) return
        audioScope.launch {
            playSingleTone(440.0, 50, 0.3f)
            playSingleTone(554.37, 50, 0.35f)
            playSingleTone(659.25, 60, 0.4f)
            playSingleTone(880.0, 100, 0.5f)
        }
    }

    fun playGavelBang() {
        if (!isSoundEnabled) return
        audioScope.launch {
            // Low impactful bass thump
            playSingleTone(90.0, 120, 0.7f)
            playSingleTone(65.0, 180, 0.5f)
        }
    }

    fun playSkakmat() {
        if (!isSoundEnabled) return
        audioScope.launch {
            playSingleTone(880.0, 80, 0.5f)
            playSingleTone(1174.66, 120, 0.6f) // D6
            playSingleTone(100.0, 160, 0.7f) // Impactful bass hit
        }
    }

    fun playVictoryFanfare() {
        if (!isSoundEnabled) return
        audioScope.launch {
            playSingleTone(523.25, 100, 0.4f)
            playSingleTone(659.25, 100, 0.45f)
            playSingleTone(783.99, 120, 0.5f)
            playSingleTone(1046.50, 260, 0.6f) // C6
        }
    }

    private fun playSingleTone(freq: Double, durationMs: Int, volume: Float) {
        try {
            val sampleRate = 22050
            val numSamples = (durationMs * sampleRate) / 1000
            val sample = ShortArray(numSamples)

            for (i in 0 until numSamples) {
                // Generate sine wave with gentle envelope fade out
                val envelope = 1.0 - (i.toDouble() / numSamples.toDouble())
                val angle = 2.0 * Math.PI * i / (sampleRate / freq)
                val value = (sin(angle) * Short.MAX_VALUE * volume * envelope).toInt()
                sample[i] = value.coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
            }

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
                .setBufferSizeInBytes(sample.size * 2)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            audioTrack.write(sample, 0, sample.size)
            audioTrack.play()
            Thread.sleep(durationMs.toLong() + 20)
            audioTrack.release()
        } catch (_: Exception) {
            // Graceful fallback if device audio is busy
        }
    }
}
