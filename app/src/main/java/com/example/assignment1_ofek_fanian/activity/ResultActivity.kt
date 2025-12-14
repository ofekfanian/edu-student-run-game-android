package com.example.assignment1_ofek_fanian.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment1_ofek_fanian.R
import com.google.android.material.button.MaterialButton

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Link to the Game Over screen XML
        setContentView(R.layout.activity_game_over)

        // Find the "Play Again" button (Must match the ID in XML!)
        val restartBtn: MaterialButton = findViewById(R.id.score_BTN_playAgain)

        // Handle click to restart game
        restartBtn.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)

            // Close the result screen so we don't return here
            finish()
        }
    }
}