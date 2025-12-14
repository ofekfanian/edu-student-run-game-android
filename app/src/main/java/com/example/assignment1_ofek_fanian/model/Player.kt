package com.example.assignment1_ofek_fanian.model

class Player {
    var lane = 1 // Start in the middle lane (index 1)
    var lives = 3

    fun move(step: Int) {
        lane += step

        // Keep player inside the grid borders (0 to 2)
        if (lane < 0) lane = 0
        if (lane > 2) lane = 2
    }

    fun crash() {
        lives-- // Reduce life by 1
    }
}