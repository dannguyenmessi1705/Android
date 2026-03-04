package com.didan.jetpack.compose.jetpackmovieapp.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.didan.jetpack.compose.jetpackmovieapp.retrofit.Movie

@Composable
fun MovieList(movies: List<Movie>) {
    LazyColumn(

    ) {
        items(movies) {
            MovieItem(movie = it)
        }
    }
}