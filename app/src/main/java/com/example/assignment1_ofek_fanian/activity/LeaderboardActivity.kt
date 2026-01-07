package com.example.assignment1_ofek_fanian.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment1_ofek_fanian.databinding.ActivityLeaderboardBinding
import com.example.assignment1_ofek_fanian.fragments.ListFragment
import com.example.assignment1_ofek_fanian.fragments.MapFragment
import com.example.assignment1_ofek_fanian.interfaces.CallbackHighScoreClick

// Class updated to implement the renamed interface
class LeaderboardActivity : AppCompatActivity(), CallbackHighScoreClick {

    private lateinit var binding: ActivityLeaderboardBinding
    private lateinit var listFragment: ListFragment
    private lateinit var mapFragment: MapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaderboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        listFragment = ListFragment()
        listFragment.setHighScoreCallback(this)
        mapFragment = MapFragment()

        supportFragmentManager.beginTransaction()
            .add(binding.mainFRAMEList.id, listFragment)
            .add(binding.mainFRAMEMap.id, mapFragment)
            .commit()
    }

    override fun onHighScoreClicked(lat: Double, lon: Double) {
        mapFragment.zoomToLocation(lat, lon)
    }
}