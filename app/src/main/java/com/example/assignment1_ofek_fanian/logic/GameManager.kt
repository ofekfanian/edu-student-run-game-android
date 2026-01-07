package com.example.assignment1_ofek_fanian.logic

import android.content.Context
import com.example.assignment1_ofek_fanian.model.Bonus
import com.example.assignment1_ofek_fanian.model.BonusType
import com.example.assignment1_ofek_fanian.model.Obstacle
import com.example.assignment1_ofek_fanian.model.ObstacleType
import com.example.assignment1_ofek_fanian.utilities.Constants
import com.example.assignment1_ofek_fanian.utilities.SignalManager
import com.example.assignment1_ofek_fanian.utilities.VibrationManager
import com.example.assignment1_ofek_fanian.R

class GameManager(
    private val context: Context,
    private val numRows: Int,
    private val numLanes: Int,
    private val numObstacles: Int,
    lifeCount: Int
) {
    private val obstacles = Array(numObstacles) {
        Obstacle(row = -1, col = (0 until numLanes).random())
    }
    private val obstacleDelay = IntArray(numObstacles)
    var currentLives: Int = lifeCount
    val bonus = Bonus(row = -1, col = 0)
    var score: Int = 0
        private set

    private var sleepBonusCount = 0
    private var shieldEndTime: Long = 0

    val isGameOver: Boolean
        get() = currentLives <= 0

    val isShieldActive: Boolean
        get() = System.currentTimeMillis() < shieldEndTime

    init {
        initGame()
    }

    fun initGame() {
        score = 0
        sleepBonusCount = 0
        currentLives = Constants.MAX_LIVES

        for (i in 0 until numObstacles) {
            obstacles[i].row = -1
            obstacles[i].col = (0 until numLanes).random()
            obstacles[i].type = ObstacleType.getRandom()
            obstacleDelay[i] = (i * 2) + 1
        }
    }

    fun resetLives() {
        currentLives = Constants.MAX_LIVES
    }

    // Changed: returns 1 if health was refilled, 0 if regular bonus, -1 if no bonus
    fun updateGameLogic(playerLane: Int): Pair<Boolean, Boolean> {
        val hitObstacle = updateObstacles(playerLane)
        val livesRefilled = updateBonus(playerLane)
        score += Constants.DISTANCE_PER_TICK
        return Pair(hitObstacle, livesRefilled)
    }

    private fun updateObstacles(playerLane: Int): Boolean {
        var hit = false
        for (i in 0 until numObstacles) {
            if (obstacleDelay[i] > 0) {
                obstacleDelay[i]--
                continue
            }

            val obstacle = obstacles[i]
            val reachedBottom = obstacle.moveDown(numRows)

            if (reachedBottom) {
                obstacle.reset(numLanes)
            }

            if (obstacle.row == numRows - 1 && obstacle.col == playerLane) {
                if (!isShieldActive) hit = true
            }
        }
        return hit
    }

    private fun updateBonus(playerLane: Int): Boolean {
        var livesRefilled = false
        if (!bonus.isActive) {
            if ((0..20).random() == 0) {
                // Modified: Only spawn heal bonus if lives < max
                val tempBonus = Bonus(row = -1, col = 0)
                tempBonus.spawn(numLanes)
                if (tempBonus.type.isHeal && currentLives >= Constants.MAX_LIVES) {
                    return false // Don't spawn ic_sleep if lives are full
                }
                bonus.spawn(numLanes)
            }
        } else {
            val reachedBottom = bonus.moveBonus(numRows)
            if (bonus.row == numRows - 1 && bonus.col == playerLane) {
                livesRefilled = applyBonusEffect(bonus.type)
                bonus.remove()
            } else if (reachedBottom) {
                bonus.remove()
            }
        }
        return livesRefilled
    }

    private fun applyBonusEffect(type: BonusType): Boolean {
        var refilled = false
        score += type.scoreValue

        when {
            type.isHeal -> {
                if (currentLives < Constants.MAX_LIVES) {
                    currentLives++
                    sleepBonusCount++
                    refilled = true // Signal to play sfx_refill
                }
            }
            type.isShield -> shieldEndTime = System.currentTimeMillis() + 4000
        }

        // Only play regular bonus sound if NOT refilling lives
        if (!refilled) {
            SignalManager.playSound(context, R.raw.sfx_bonus)
            VibrationManager.vibrate(50)
        }

        return refilled
    }

    fun handleCrash() {
        currentLives--
    }

    fun getObstacles() = obstacles
}