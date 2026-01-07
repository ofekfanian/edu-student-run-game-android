package com.example.assignment1_ofek_fanian.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment1_ofek_fanian.R
import com.example.assignment1_ofek_fanian.databinding.ActivityGameBinding
import com.example.assignment1_ofek_fanian.logic.GameGridManager
import com.example.assignment1_ofek_fanian.logic.GameManager
import com.example.assignment1_ofek_fanian.logic.GameTicker
import com.example.assignment1_ofek_fanian.logic.TiltDetector
import com.example.assignment1_ofek_fanian.model.Player
import com.example.assignment1_ofek_fanian.utilities.*

class GameActivity : AppCompatActivity(), TiltDetector.TiltCallback {

    private lateinit var binding: ActivityGameBinding
    private lateinit var gameManager: GameManager
    private lateinit var gameGridManager: GameGridManager
    private lateinit var gameTicker: GameTicker
    private lateinit var tiltDetector: TiltDetector
    private lateinit var player: Player

    private var useSensors: Boolean = false
    private var isFastMode: Boolean = false
    private var isEndlessMode: Boolean = false
    private var isGameRunning: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load game settings from intent
        useSensors = intent.getBooleanExtra(Constants.KEY_SENSORS, false)
        isFastMode = intent.getBooleanExtra(Constants.KEY_SPEED_MODE, false)
        isEndlessMode = intent.getBooleanExtra("KEY_ENDLESS_MODE", false)

        initManagers()
        initViews()

        isGameRunning = true
        updateHeartsUI()
    }

    private fun initManagers() {
        gameGridManager = GameGridManager(binding.mainLAYGrid)
        player = Player(Constants.NUM_LANES)

        // Passing 'this' as context to allow SignalManager usage inside GameManager
        gameManager = GameManager(
            context = this,
            numRows = Constants.NUM_ROWS,
            numLanes = Constants.NUM_LANES,
            numObstacles = Constants.NUM_OBSTACLES,
            lifeCount = Constants.MAX_LIVES
        )

        gameTicker = GameTicker { tick() }

        val baseInterval = Constants.GAME_TICK_MS
        gameTicker.interval = if (isFastMode) {
            (baseInterval * Constants.SPEED_MULTIPLIER_FAST).toLong()
        } else {
            (baseInterval * Constants.SPEED_MULTIPLIER_SLOW).toLong()
        }

        tiltDetector = TiltDetector(this, this)
    }

    private fun initViews() {
        if (useSensors) {
            binding.mainLAYButtons.visibility = View.GONE
        } else {
            binding.mainLAYButtons.visibility = View.VISIBLE
            binding.mainBTNLeft.setOnClickListener { movePlayer(-1) }
            binding.mainBTNRight.setOnClickListener { movePlayer(1) }
        }
        updateUI()
    }

    private fun tick() {
        if (!isGameRunning) return

        // updateGameLogic returns a Pair(hitObstacle, livesRefilled)
        val (collision, livesRefilled) = gameManager.updateGameLogic(player.lane)

        if (collision) handleCollision()

        if (livesRefilled) {
            // Play refill sound and update UI immediately
            SignalManager.playSound(this, R.raw.sfx_refill)
            VibrationManager.vibrate(100)
            updateHeartsUI()
        }

        updateUI()

        if (!isEndlessMode && gameManager.isGameOver) {
            finishGame()
        }
    }

    private fun handleCollision() {
        VibrationManager.vibrate(500)
        SignalManager.playSound(this, R.raw.sfx_hit)

        gameManager.handleCrash()
        updateHeartsUI()

        if (isEndlessMode && gameManager.isGameOver) {
            gameManager.resetLives()
            updateHeartsUI()
            SignalManager.playSound(this, R.raw.sfx_refill)
        }
    }

    private fun movePlayer(direction: Int) {
        player.move(direction)
        updateUI()
    }

    private fun updateUI() {
        gameGridManager.updateUI(
            obstacles = gameManager.getObstacles(),
            playerLane = player.lane,
            bonus = gameManager.bonus
        )
        binding.mainLBLScore.text = gameManager.score.toString()
    }

    private fun updateHeartsUI() {
        val hearts = arrayOf(binding.mainIMGHeart1, binding.mainIMGHeart2, binding.mainIMGHeart3)
        val crashCount = Constants.MAX_LIVES - gameManager.currentLives

        for (i in hearts.indices) {
            // Logic: Hearts disappear from left to right as requested
            if (i < crashCount) {
                hearts[i].setImageResource(R.drawable.ic_heart_empty)
            } else {
                hearts[i].setImageResource(R.drawable.heart_red_icon)
            }
        }
    }

    private fun finishGame() {
        isGameRunning = false
        gameTicker.stop()
        SignalManager.playSound(this, R.raw.sfx_school_bell)

        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(Constants.KEY_SCORE, gameManager.score)
        startActivity(intent)
        finish()
    }

    override fun onTilt(direction: Int) {
        if (useSensors && isGameRunning) movePlayer(direction)
    }

    override fun onResume() {
        super.onResume()
        if (isGameRunning) {
            gameTicker.start()
            if (useSensors) tiltDetector.startTiltDetection()
            SignalManager.playMusic(this, R.raw.bgm_game)
        }
    }

    override fun onPause() {
        super.onPause()
        gameTicker.stop()
        if (useSensors) tiltDetector.stopTiltDetection()
        SignalManager.stopMusic()
    }
}