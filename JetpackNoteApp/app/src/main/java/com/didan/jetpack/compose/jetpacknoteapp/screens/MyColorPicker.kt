package com.didan.jetpack.compose.jetpacknoteapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt

@Composable
fun MyColorPicker(
    selectedColor: Color,
    onColorSelected: (Color) -> Unit
) {
    // Color List
    val colorList = listOf(
        Color("#F44336".toColorInt()), // Red
        Color("#E91E63".toColorInt()), // Pink
        Color("#9C27B0".toColorInt()), // Purple
        Color("#673AB7".toColorInt()), // Deep Purple
        Color("#3F51B5".toColorInt()), // Indigo
        Color("#2196F3".toColorInt()), // Blue
        Color("#03A9F4".toColorInt()), // Light Blue
        Color("#00BCD4".toColorInt()), // Cyan
        Color("#009688".toColorInt()), // Teal
        Color("#4CAF50".toColorInt()), // Green
        Color("#8BC34A".toColorInt()), // Light Green
        Color("#CDDC39".toColorInt()), // Lime
        Color("#FFEB3B".toColorInt()), // Yellow
        Color("#FFC107".toColorInt()), // Amber
        Color("#FF9800".toColorInt()), // Orange
        Color("#FF5722".toColorInt()), // Deep Orange
        Color("#795548".toColorInt()), // Brown
        Color("#9E9E9E".toColorInt()), // Grey
        Color("#607D8B".toColorInt())  // Blue Grey
    )

    LazyRow(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        items(colorList) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp)
                    .clip(CircleShape)
                    .background(color = it)
                    .border(
                        width = if (it == selectedColor) 4.dp else 0.dp,
                        color = if (it == selectedColor) Color.Black else Color.Transparent,
                        shape = CircleShape
                    )
                    .clickable {
                        onColorSelected(it)
                    }
            )
        }
    }
}