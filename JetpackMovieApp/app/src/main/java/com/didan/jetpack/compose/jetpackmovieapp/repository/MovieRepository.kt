package com.didan.jetpack.compose.jetpackmovieapp.repository

import android.content.Context
import com.didan.jetpack.compose.jetpackmovieapp.retrofit.Movie
import com.didan.jetpack.compose.jetpackmovieapp.retrofit.RetrofitInstance
import com.didan.jetpack.compose.jetpackmovieapp.room.MovieDao
import com.didan.jetpack.compose.jetpackmovieapp.room.MoviesDb

class MovieRepository(context: Context) {

    suspend fun getPopularMoviesFromApi(apiKey: String): List<Movie> {
        return RetrofitInstance.api.getPopularMovies(apiKey).results
    }


    private val db = MoviesDb.getInstance(context)
    private val movieDao: MovieDao = db.movieDao

    suspend fun getMoviesFromDb(): List<Movie> {
        // Convert the list of movies from the database to a list of movies that can be used in the UI
        val movie: List<com.didan.jetpack.compose.jetpackmovieapp.room.Movie> =
            movieDao.getAllMoviesInDb()
        return movie.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate
            )
        }
    }

    suspend fun insertMoviesToDb(movieList: List<Movie>) {
        val moviesToInsert = movieList.map {
            com.didan.jetpack.compose.jetpackmovieapp.room.Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate
            )
        }
        movieDao.insertMovies(moviesToInsert)
    }

    suspend fun insertMovieToDb(movie: Movie) {
        val movieToInsert = com.didan.jetpack.compose.jetpackmovieapp.room.Movie(
            id = movie.id,
            title = movie.title,
            overview = movie.overview,
            posterPath = movie.posterPath,
            releaseDate = movie.releaseDate
        )
        movieDao.insertMovie(movieToInsert)
    }

}