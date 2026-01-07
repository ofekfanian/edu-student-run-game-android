package com.example.assignment1_ofek_fanian.logic

import android.os.Handler
import android.os.Looper

class GameTicker(private val onTick: () -> Unit) {
    private val handler = Handler(Looper.getMainLooper())
    private var isRunning = false
    var interval: Long = 1000

    private val runnable = object : Runnable {
        override fun run() {
            if (!isRunning) return
            onTick()
            handler.postDelayed(this, interval)
        }
    }

    fun start() {
        if (!isRunning) {
            isRunning = true
            handler.post(runnable)
        }
    }

    fun stop() {
        isRunning = false
        handler.removeCallbacks(runnable)
    }
}