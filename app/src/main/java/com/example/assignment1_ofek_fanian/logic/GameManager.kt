package com.example.assignment1_ofek_fanian.logic

import com.example.assignment1_ofek_fanian.model.GameSymbol
import com.example.assignment1_ofek_fanian.model.Player
import com.example.assignment1_ofek_fanian.utilities.Constants
import kotlin.random.Random

class GameManager(lifeCount: Int = Constants.Game.LIVES) {

    // Grid dimensions
    val rows = Constants.Game.ROWS
    val cols = Constants.Game.COLS

    // Player model to manage position and lives
    val player = Player()

    // Logic Matrix: Represents the game grid (0 = Empty, 1 = Homework, 2 = Test)
    var matrix = Array(rows) { IntArray(cols) { GameSymbol.EMPTY.value } }

    var isGameOver = false

    init {
        player.lives = lifeCount
    }

    // Move player left or right
    fun movePlayer(step: Int) {
        if (isGameOver) return
        player.move(step)
    }

    // Main game loop logic
    fun updateGameCycle() {
        if (isGameOver) return

        // 1. Move all items down by one row
        for (i in rows - 1 downTo 1) {
            for (j in 0 until cols) {
                matrix[i][j] = matrix[i - 1][j]
            }
        }

        // 2. Clear the top row
        for (j in 0 until cols) { matrix[0][j] = GameSymbol.EMPTY.value }

        // 3. Add a new random obstacle at the top row
        if (Random.nextInt(2) == 0) {
            val lane = Random.nextInt(cols)
            matrix[0][lane] = if (Random.nextBoolean()) GameSymbol.HOMEWORK.value else GameSymbol.TEST.value
        }
    }

    fun checkCollision(): Boolean {
        // Check if the player hit an obstacle in the last row
        val item = matrix[rows - 1][player.lane]

        if (item != GameSymbol.EMPTY.value) {
            player.crash() // Reduce lives

            if (player.lives <= 0) isGameOver = true
            return true // Collision detected
        }
        return false
    }

    // Getters for UI
    val lives: Int get() = player.lives
    val playerIndex: Int get() = player.lane
}