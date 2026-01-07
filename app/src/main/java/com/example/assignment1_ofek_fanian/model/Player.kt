package com.example.assignment1_ofek_fanian.model

class Player(private val numLanes: Int) {

    var lane: Int = numLanes / 2
        private set

    // Updates player lane with boundary check
    fun move(direction: Int) {
        val newLane = lane + direction
        if (newLane in 0 until numLanes) {
            lane = newLane
        }
    }
}