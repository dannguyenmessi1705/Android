package com.didan.jetpack.compose.jetpackmovieapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MoviesDb : RoomDatabase() {

    // DAO
    abstract val movieDao: MovieDao

    companion object {
        @Volatile
        private var INSTANCE: MoviesDb? = null
        fun getInstance(context: Context): MoviesDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MoviesDb::class.java,
                        "movies_db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}