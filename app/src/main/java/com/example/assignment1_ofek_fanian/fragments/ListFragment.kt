package com.example.assignment1_ofek_fanian.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.assignment1_ofek_fanian.R // Added import for R
import com.example.assignment1_ofek_fanian.databinding.FragmentListBinding
import com.example.assignment1_ofek_fanian.interfaces.CallbackHighScoreClick
import com.example.assignment1_ofek_fanian.logic.ScoreManager

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private var callback: CallbackHighScoreClick? = null

    fun setHighScoreCallback(callback: CallbackHighScoreClick) {
        this.callback = callback
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
    }

    private fun initList() {
        val scores = ScoreManager.getInstance().getAllScores()
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_score, // Your custom layout with white text
            android.R.id.text1,  // The ID within item_score.xml
            scores.map { "${it.name}: ${it.score}" }
        )
        binding.listLSTScores.adapter = adapter

        binding.listLSTScores.setOnItemClickListener { _, _, position, _ ->
            val selected = scores[position]
            callback?.onHighScoreClicked(selected.lat, selected.lon)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}