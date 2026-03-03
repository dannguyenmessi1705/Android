package com.didan.jetpack.compose.jetpacknewsapp.util

import androidx.compose.ui.graphics.Color

object GenRandomColor {
    fun getRandomColor(): Color {
        val red = (0..255).random()
        val green = (0..255).random()
        val blue = (0..255).random()
        return Color(red, green, blue)
    }
}