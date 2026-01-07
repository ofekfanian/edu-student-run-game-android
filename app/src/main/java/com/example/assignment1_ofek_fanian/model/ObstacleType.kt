package com.example.assignment1_ofek_fanian.model

import com.example.assignment1_ofek_fanian.R

enum class ObstacleType(val imageRes: Int) {
    COURSE_REPEAT(R.drawable.course_repeat),
    DEADLINE(R.drawable.ic_deadline),
    HOMEWORK(R.drawable.ic_homework),
    RESIT(R.drawable.ic_resit),
    TRICK_QUESTION(R.drawable.trick_question),
    TEST(R.drawable.ic_test);

    companion object {
        fun getRandom() = entries.random()
    }
}