package com.example.assignment1_ofek_fanian.model

class Obstacle(row: Int, col: Int) : BaseObject(row, col) {

    var type = ObstacleType.getRandom()

    // Resets obstacle properties
    fun reset(numLanes: Int) {
        row = 0
        col = (0 until numLanes).random()
        type = ObstacleType.getRandom()
    }
}