package com.example.assignment1_ofek_fanian.model

class Bonus(row: Int, col: Int) : BaseObject(row, col) {

    var type = BonusType.getRandom()
    var isActive: Boolean = false

    // Spawns bonus at random lane
    fun spawn(numLanes: Int) {
        row = 0
        col = (0 until numLanes).random()
        type = BonusType.getRandom()
        isActive = true
    }

    fun remove() {
        isActive = false
        row = -1
    }

    // Moves only if bonus is currently active
    fun moveBonus(maxRows: Int): Boolean {
        return if (isActive) super.moveDown(maxRows) else false
    }
}