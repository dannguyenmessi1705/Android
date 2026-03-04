package com.didan.jetpack.compose.jetpackmovieapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_table")
data class Movie(
    @PrimaryKey
    val id: Int,
    val title: String,
    val overview: String,
    @ColumnInfo("poster_path")
    val posterPath: String?,
    @ColumnInfo("release_date")
    val releaseDate: String
)
