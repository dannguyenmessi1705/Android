package com.didan.jetpack.compose.jetpacknewsapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.didan.jetpack.compose.jetpacknewsapp.retrofit.Post
import com.didan.jetpack.compose.jetpacknewsapp.util.GenRandomColor

@Composable
fun PostItem(post: Post) {
    val backgroundColor = GenRandomColor.getRandomColor()
    Card(
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = post.title,
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = post.body,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}