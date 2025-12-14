package com.example.assignment1_ofek_fanian.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.assignment1_ofek_fanian.R
import com.example.assignment1_ofek_fanian.logic.GameManager
import com.example.assignment1_ofek_fanian.model.GameSymbol
import com.example.assignment1_ofek_fanian.utilities.Constants

class GameActivity : AppCompatActivity() {

    // --- UI Components ---
    private lateinit var main_BTN_left: AppCompatImageButton
    private lateinit var main_BTN_right: AppCompatImageButton
    private lateinit var hearts: Array<AppCompatImageView>
    private lateinit var gameViews: Array<Array<ImageView>> // Visual Matrix (7x3)

    // --- Logic & Timer ---
    private lateinit var gameManager: GameManager

    // Handler for the game timer (Loop)
    private val handler = Handler(Looper.getMainLooper())
    private var gameRunning = false

    // The Game Loop: Runs every X milliseconds
    private val runnable: Runnable = object : Runnable {
        override fun run() {
            // 1. Schedule the next run
            handler.postDelayed(this, Constants.Game.TIMER_DELAY)
            // 2. Execute game logic
            tick()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)

        // Adjust padding for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        gameManager = GameManager()
        findViews()
        initViews()
    }

    private fun findViews() {
        main_BTN_left = findViewById(R.id.main_BTN_left)
        main_BTN_right = findViewById(R.id.main_BTN_right)

        // Array of Heart images
        hearts = arrayOf(
            findViewById(R.id.main_IMG_heart1),
            findViewById(R.id.main_IMG_heart2),
            findViewById(R.id.main_IMG_heart3)
        )

        // Connect the visual grid (Rows 0-6, Cols 0-2) to the XML views
        gameViews = arrayOf(
            arrayOf(findViewById(R.id.img_00), findViewById(R.id.img_01), findViewById(R.id.img_02)),
            arrayOf(findViewById(R.id.img_10), findViewById(R.id.img_11), findViewById(R.id.img_12)),
            arrayOf(findViewById(R.id.img_20), findViewById(R.id.img_21), findViewById(R.id.img_22)),
            arrayOf(findViewById(R.id.img_30), findViewById(R.id.img_31), findViewById(R.id.img_32)),
            arrayOf(findViewById(R.id.img_40), findViewById(R.id.img_41), findViewById(R.id.img_42)),
            arrayOf(findViewById(R.id.img_50), findViewById(R.id.img_51), findViewById(R.id.img_52)),
            arrayOf(findViewById(R.id.img_60), findViewById(R.id.img_61), findViewById(R.id.img_62))
        )
    }

    private fun initViews() {
        updateUI()

        // Move Left
        main_BTN_left.setOnClickListener {
            gameManager.movePlayer(-1)
            updateUI()
        }

        // Move Right
        main_BTN_right.setOnClickListener {
            gameManager.movePlayer(1)
            updateUI()
        }
    }

    // --- Main Game Logic Loop ---
    private fun tick() {
        // 1. Move obstacles down & Spawn new ones
        gameManager.updateGameCycle()

        // 2. Check for collisions
        if (gameManager.checkCollision()) {

            var crashMessage = ""
            when (gameManager.lives) {
                2 -> crashMessage = "2 attempts left! ⚠️"
                1 -> crashMessage = "Last Chance! 🚨"
                0 -> crashMessage = "Game Over! 😭"
                else -> crashMessage = "Ouch!"
            }

            // A. Feedback (Vibrate + Toast)
            toastAndVibrate(crashMessage)
            updateLivesUI()

            // B. Visual Feedback: Show "Dizzy" character
            updateUI()
            val playerRow = Constants.Game.ROWS - 1
            val playerCol = gameManager.playerIndex
            gameViews[playerRow][playerCol].setImageResource(R.drawable.ic_dizzy)
        } else {
            // No collision -> Draw normal game state
            updateUI()
        }

        // 3. Check if game is lost
        if (gameManager.isGameOver) {
            stopTimer()
            changeActivityToScore()
        }
    }

    // Draw the Logic Matrix onto the Screen
    private fun updateUI() {
        for (i in 0 until Constants.Game.ROWS) {
            for (j in 0 until Constants.Game.COLS) {
                val imageView = gameViews[i][j]
                imageView.setImageDrawable(null) // Clear old image

                // Draw Player
                if (i == Constants.Game.ROWS - 1 && j == gameManager.playerIndex) {
                    imageView.setImageResource(R.drawable.ic_guy)
                    continue
                }

                // Draw Obstacles based on logic matrix
                val value = gameManager.matrix[i][j]
                if (value == GameSymbol.HOMEWORK.value) {
                    imageView.setImageResource(R.drawable.ic_homework)
                } else if (value == GameSymbol.TEST.value) {
                    imageView.setImageResource(R.drawable.ic_test)
                }
            }
        }
    }

    private fun updateLivesUI() {
        // Update hearts based on remaining lives
        if (gameManager.lives < hearts.size) {
            val indexToUpdate = gameManager.lives
            hearts[indexToUpdate].setImageResource(R.drawable.heart_empty)
        }
    }

    private fun changeActivityToScore() {
        val intent = Intent(this, ResultActivity::class.java)
        startActivity(intent)
        finish() // Close this activity so back button won't return here
    }

    private fun toastAndVibrate(message: String) {
        vibrate()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun vibrate() {
        val v = getSystemService(VIBRATOR_SERVICE) as Vibrator
        // Check Android version for correct vibration method
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            v.vibrate(500)
        }
    }

    // --- Lifecycle Methods (Important for Timer) ---

    override fun onResume() {
        super.onResume()
        // Start timer when app is visible
        if (!gameManager.isGameOver) startTimer()
    }

    override fun onPause() {
        super.onPause()
        // Stop timer when app goes to background
        stopTimer()
    }

    private fun startTimer() {
        if (!gameRunning) {
            gameRunning = true
            handler.post(runnable)
        }
    }

    private fun stopTimer() {
        gameRunning = false
        handler.removeCallbacks(runnable)
    }
}