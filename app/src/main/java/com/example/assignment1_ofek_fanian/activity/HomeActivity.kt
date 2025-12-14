package com.example.assignment1_ofek_fanian.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment1_ofek_fanian.R
import com.google.android.material.button.MaterialButton

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Connects the Activity to the XML layout (The Menu Screen)
        setContentView(R.layout.activity_home)

        // Find the "Start Game" button by its ID
        val startButton: MaterialButton = findViewById(R.id.main_BTN_start)

        startButton.setOnClickListener {
            // Create an Intent to switch from HomeActivity to GameActivity
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)

            // Close the menu so the "Back" button won't return here
            finish()
        }
    }
}