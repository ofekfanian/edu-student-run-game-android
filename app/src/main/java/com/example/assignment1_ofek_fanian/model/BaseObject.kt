package com.example.assignment1_ofek_fanian.model

open class BaseObject(
    var row: Int,
    var col: Int
) {
    // Moves object down by one row
    fun moveDown(maxRows: Int): Boolean {
        row++
        return row >= maxRows
    }
}