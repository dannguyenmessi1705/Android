package com.didan.jetpack.compose.jetpacknewsapp.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.didan.jetpack.compose.jetpacknewsapp.retrofit.Post

@Composable
fun PostList(posts: List<Post>) {
    LazyColumn {
        items(posts) {
            PostItem(it)
        }
    }
}