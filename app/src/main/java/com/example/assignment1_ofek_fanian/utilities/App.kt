package com.example.assignment1_ofek_fanian.utilities

import android.app.Application
import com.example.assignment1_ofek_fanian.logic.ScoreManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SignalManager.init(this)
        VibrationManager.init(this)
        ScoreManager.init(this)
    }
}