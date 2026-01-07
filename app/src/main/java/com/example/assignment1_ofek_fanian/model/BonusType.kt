package com.example.assignment1_ofek_fanian.model

import com.example.assignment1_ofek_fanian.R

enum class BonusType(
    val imageRes: Int,
    val scoreValue: Int = 10,
    val isHeal: Boolean = false,
    val isShield: Boolean = false,
) {
    SLEEP(R.drawable.ic_sleep, scoreValue = 250, isHeal = true),
    SEMESTER_BREAK(R.drawable.ic_semester_break, scoreValue = 100, isShield = true),
    GRADE_100(R.drawable.ic_grade_100, scoreValue = 300),
    SCHOLARSHIP(R.drawable.ic_scholsrship, scoreValue = 200),
    FRIENDS(R.drawable.ic_friends, scoreValue = 100),
    CLOCK(R.drawable.ic_clock, scoreValue = 80);

    companion object {
        fun getRandom() = entries.random()
    }
}