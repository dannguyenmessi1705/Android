package com.didan.jetpack.compose.jetpackmovieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.didan.jetpack.compose.jetpackmovieapp.repository.MovieRepository

class MovieViewModelFactory(private val movieRepository: MovieRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(movieRepository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}