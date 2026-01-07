package com.example.assignment1_ofek_fanian.logic

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.example.assignment1_ofek_fanian.utilities.Constants
import kotlin.math.abs

class TiltDetector(context: Context, private val tiltCallback: TiltCallback) {

    private val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private var lastUpdate: Long = 0

    // Callback interface for tilt events
    interface TiltCallback {
        fun onTilt(direction: Int)
    }

    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val x = event.values[0]
            val currentTime = System.currentTimeMillis()

            // Control detection frequency
            if ((currentTime - lastUpdate) > 50) {
                if (abs(x) > Constants.TILT_THRESHOLD) {
                    lastUpdate = currentTime
                    if (x > Constants.TILT_THRESHOLD) {
                        tiltCallback.onTilt(-1) // Left tilt
                    } else if (x < -Constants.TILT_THRESHOLD) {
                        tiltCallback.onTilt(1) // Right tilt
                    }
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    // Start listening to sensors
    fun startTiltDetection() {
        accelerometer?.let {
            sensorManager.registerListener(sensorEventListener, it, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    // Stop listening to sensors
    fun stopTiltDetection() {
        sensorManager.unregisterListener(sensorEventListener)
    }
}