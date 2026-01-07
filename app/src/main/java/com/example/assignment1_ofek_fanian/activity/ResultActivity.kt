package com.example.assignment1_ofek_fanian.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.assignment1_ofek_fanian.R
import com.example.assignment1_ofek_fanian.databinding.ActivityGameOverBinding
import com.example.assignment1_ofek_fanian.logic.ScoreManager
import com.example.assignment1_ofek_fanian.model.ScoreItem
import com.example.assignment1_ofek_fanian.utilities.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameOverBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var finalScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameOverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        finalScore = intent.getIntExtra(Constants.KEY_SCORE, 0)

        initViews()
    }

    private fun initViews() {
        binding.scoreLBLFinalScore.text = getString(R.string.final_score, finalScore)

        binding.scoreBTNRestart.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
            finish()
        }

        binding.scoreBTNBackToHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        binding.scoreBTNSaveScore.setOnClickListener { showSaveScoreDialog() }

        SignalManager.playMusic(this, R.raw.bgm_result)
    }

    private fun showSaveScoreDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_save_score, null)
        val etName = view.findViewById<EditText>(R.id.dialog_ET_name)

        MaterialAlertDialogBuilder(this)
            .setView(view)
            .setTitle("Save Score")
            .setMessage("Enter your name:")
            .setPositiveButton("Save") { _, _ ->
                val name = etName.text.toString()
                if (name.isNotEmpty()) checkLocationAndSave(name)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun checkLocationAndSave(name: String) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) saveScore(name, location.latitude, location.longitude)
                else saveRandomLocation(name)
            }.addOnFailureListener { saveRandomLocation(name) }
        } else {
            saveRandomLocation(name)
        }
    }

    private fun saveRandomLocation(name: String) {
        saveScore(name, (Math.random() * 180) - 90, (Math.random() * 360) - 180)
    }

    private fun saveScore(name: String, lat: Double, lon: Double) {
        ScoreManager.getInstance().saveScore(ScoreItem(name, finalScore, lat, lon))
        startActivity(Intent(this, LeaderboardActivity::class.java))
        finish()
    }
}