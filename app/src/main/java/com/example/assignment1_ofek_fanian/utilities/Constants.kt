package com.example.assignment1_ofek_fanian.utilities

object Constants {
    // Intent Keys
    const val KEY_SCORE = "KEY_SCORE"
    const val KEY_SENSORS = "KEY_SENSORS"
    const val KEY_SPEED_MODE = "KEY_SPEED_MODE"

    // Game Grid Configuration
    const val NUM_ROWS = 7
    const val NUM_LANES = 5
    const val NUM_OBSTACLES = 3

    // Lives and Bonuses
    const val MAX_LIVES = 3
    const val MAX_SLEEP_BONUSES = 2

    // Game Timing and Speed
    const val GAME_TICK_MS = 1000L
    const val SPEED_MULTIPLIER_FAST = 0.5
    const val SPEED_MULTIPLIER_SLOW = 1.0

    // Scoring and Sensors
    const val DISTANCE_PER_TICK = 10
    const val TILT_THRESHOLD = 4.0f
}