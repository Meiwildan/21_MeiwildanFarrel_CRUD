package com.example.a21_meiwildanfarrel.room

import androidx.room.*

@Dao
interface MovieDao {
    @Insert
    suspend fun addMovie(movie: Movie)

    @Update
    suspend fun updateMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query ("SELECT * FROM Movie")
    suspend fun getMovies():List<Movie>

    @Query ("SELECT * FROM Movie WHERE id=:movie_id")
    suspend fun getMovie(movie_id: Int):List<Movie>
}