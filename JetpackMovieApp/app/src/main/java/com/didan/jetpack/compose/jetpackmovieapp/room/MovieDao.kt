package com.didan.jetpack.compose.jetpackmovieapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movieList: List<Movie>)

    @Query("SELECT * FROM movies_table")
    suspend fun getAllMoviesInDb(): List<Movie>


}