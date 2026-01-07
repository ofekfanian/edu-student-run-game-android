package com.example.assignment1_ofek_fanian.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.assignment1_ofek_fanian.R
import com.example.assignment1_ofek_fanian.databinding.ActivityHomeBinding
import com.example.assignment1_ofek_fanian.utilities.*

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    private var isSensorMode = false
    private var isFastMode = false
    private var isEndlessMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        checkLocationPermissions()
    }

    private fun initViews() {
        binding.homeTOGGLEMode.check(R.id.home_BTN_normal)
        binding.homeTOGGLEDiff.check(R.id.home_BTN_easy)
        binding.homeTOGGLEControls.check(R.id.home_BTN_buttons)

        binding.homeTOGGLEMode.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) isEndlessMode = (checkedId == R.id.home_BTN_endless)
        }

        binding.homeTOGGLEDiff.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) isFastMode = (checkedId == R.id.home_BTN_hard)
        }

        binding.homeTOGGLEControls.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) isSensorMode = (checkedId == R.id.home_BTN_tilt)
        }

        binding.homeBTNStart.setOnClickListener {
            val anim = AnimationUtils.loadAnimation(this, R.anim.button_press_scale)
            binding.homeBTNStart.startAnimation(anim)

            binding.homeBTNStart.postDelayed({
                val intent = Intent(this, GameActivity::class.java).apply {
                    putExtra(Constants.KEY_SENSORS, isSensorMode)
                    putExtra(Constants.KEY_SPEED_MODE, isFastMode)
                    putExtra("KEY_ENDLESS_MODE", isEndlessMode)
                }
                startActivity(intent)
            }, 200)
        }

        binding.homeBTNScores.setOnClickListener {
            startActivity(Intent(this, LeaderboardActivity::class.java))
        }

        SignalManager.playMusic(this, R.raw.bgm_home)
    }

    private fun checkLocationPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
        }
    }

    private val locationPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { }
}