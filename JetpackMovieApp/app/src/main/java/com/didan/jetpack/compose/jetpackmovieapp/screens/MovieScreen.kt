package com.didan.jetpack.compose.jetpackmovieapp.screens

import androidx.compose.runtime.Composable
import com.didan.jetpack.compose.jetpackmovieapp.viewmodel.MovieViewModel

@Composable
fun MovieScreen(viewModel: MovieViewModel) {
    val movieList = viewModel.movies
    MovieList(movies = movieList)
}