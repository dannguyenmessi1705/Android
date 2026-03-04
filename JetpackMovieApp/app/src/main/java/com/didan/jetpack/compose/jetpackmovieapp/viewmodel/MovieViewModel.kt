package com.didan.jetpack.compose.jetpackmovieapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.didan.jetpack.compose.jetpackmovieapp.repository.MovieRepository
import com.didan.jetpack.compose.jetpackmovieapp.retrofit.Movie
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    var movies by mutableStateOf<List<Movie>>(emptyList())
        private set

    // The Online Movies
    var moviesFromApi by mutableStateOf<List<Movie>>(emptyList())
        private set

    // The Offline Movies
    var moviesFromDb by mutableStateOf<List<Movie>>(emptyList())
        private set

    init {
        viewModelScope.launch {
            try {
                moviesFromApi =
                    repository.getPopularMoviesFromApi("890a86f5656fdca2767b6be3222e3526")

//                 Insert the fetched movies into the room database
                repository.insertMoviesToDb(moviesFromApi)

                // Assign the fetched movies to the movies state variable
                movies = moviesFromApi
            } catch (e: Exception) {
//                 Fetch the db on room database if the api call fails
                moviesFromDb = repository.getMoviesFromDb()

                // Assign the db movies to the movies state variable
                movies = moviesFromDb
            }
        }
    }

}