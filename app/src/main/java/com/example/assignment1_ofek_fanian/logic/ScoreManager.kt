package com.example.assignment1_ofek_fanian.logic

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.assignment1_ofek_fanian.model.ScoreItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ScoreManager private constructor(context: Context) {
    private val sp: SharedPreferences = context.getSharedPreferences("SP_SCORES", Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        @Volatile
        private var instance: ScoreManager? = null

        fun init(context: Context) {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) instance = ScoreManager(context)
                }
            }
        }

        fun getInstance() = instance ?: throw IllegalStateException("ScoreManager not initialized")
    }

    fun saveScore(scoreItem: ScoreItem) {
        val scores = getAllScores().toMutableList().apply {
            add(scoreItem)
            sortByDescending { it.score }
        }.take(10)

        val json = gson.toJson(scores)

        // Fixed: Using KTX extension function to resolve the warning
        sp.edit {
            putString("KEY_SCORES", json)
        }
    }

    fun getAllScores(): List<ScoreItem> {
        val json = sp.getString("KEY_SCORES", null) ?: return emptyList()
        val type = object : TypeToken<List<ScoreItem>>() {}.type
        return gson.fromJson(json, type)
    }
}