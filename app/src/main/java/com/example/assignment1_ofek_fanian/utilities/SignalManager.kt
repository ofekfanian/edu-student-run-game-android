package com.example.assignment1_ofek_fanian.utilities

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner

object SignalManager {
    private var mediaPlayer: MediaPlayer? = null
    private var currentResId: Int? = null
    private var wasPlayingBeforeBackground = false

    // Observer to handle music when app goes to background/foreground
    private val lifecycleObserver = object : DefaultLifecycleObserver {
        override fun onStop(owner: LifecycleOwner) {
            if (mediaPlayer?.isPlaying == true) {
                wasPlayingBeforeBackground = true
                pauseMusic()
            }
        }

        override fun onStart(owner: LifecycleOwner) {
            if (wasPlayingBeforeBackground) {
                resumeMusic()
                wasPlayingBeforeBackground = false
            }
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun init(application: Application) {
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleObserver)
    }

    fun playMusic(context: Context, @RawRes resId: Int) {
        if (resId == currentResId) return

        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context.applicationContext, resId).apply {
            isLooping = true
            setVolume(0.8f, 0.8f)
            start()
        }
        currentResId = resId
    }

    fun playSound(context: Context, @RawRes resId: Int) {
        MediaPlayer.create(context.applicationContext, resId).apply {
            setOnCompletionListener { it.release() }
            start()
        }
    }

    fun pauseMusic() {
        mediaPlayer?.pause()
    }

    fun resumeMusic() {
        mediaPlayer?.start()
    }

    fun stopMusic() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        currentResId = null
    }
}