package com.example.assignment1_ofek_fanian.logic

import android.view.View
import android.widget.LinearLayout
import android.widget.ImageView
import com.example.assignment1_ofek_fanian.R
import com.example.assignment1_ofek_fanian.model.Bonus
import com.example.assignment1_ofek_fanian.model.Obstacle
import com.example.assignment1_ofek_fanian.utilities.Constants

class GameGridManager(private val linearMatrix: LinearLayout) {

    private val gameGrid: Array<Array<ImageView>> = Array(Constants.NUM_ROWS) { row ->
        val rowLayout = linearMatrix.getChildAt(row) as LinearLayout
        Array(Constants.NUM_LANES) { col ->
            rowLayout.getChildAt(col) as ImageView
        }
    }

    fun updateUI(obstacles: Array<Obstacle>, playerLane: Int, bonus: Bonus) {
        // Clear Grid visibility
        for (row in 0 until Constants.NUM_ROWS) {
            for (col in 0 until Constants.NUM_LANES) {
                gameGrid[row][col].visibility = View.INVISIBLE
            }
        }

        // Draw Player
        if (playerLane in 0 until Constants.NUM_LANES) {
            gameGrid[Constants.NUM_ROWS - 1][playerLane].apply {
                setImageResource(R.drawable.ic_guy)
                visibility = View.VISIBLE
            }
        }

        // Draw Obstacles
        obstacles.forEach { obstacle ->
            if (obstacle.row in 0 until Constants.NUM_ROWS) {
                gameGrid[obstacle.row][obstacle.col].apply {
                    setImageResource(obstacle.type.imageRes)
                    visibility = View.VISIBLE
                }
            }
        }

        // Draw Bonus
        if (bonus.isActive && bonus.row in 0 until Constants.NUM_ROWS) {
            gameGrid[bonus.row][bonus.col].apply {
                setImageResource(bonus.type.imageRes)
                visibility = View.VISIBLE
            }
        }
    }
}