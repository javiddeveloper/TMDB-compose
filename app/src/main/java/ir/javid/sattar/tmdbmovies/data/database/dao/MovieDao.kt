package ir.javid.sattar.tmdbmovies.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import ir.javid.sattar.tmdbmovies.data.model.ResultEntity

@Dao
interface MovieDao {


    @Query("SELECT * FROM resultentity")
    fun getAllMovies(): PagingSource<Int, ResultEntity>

    @Query("SELECT * FROM resultentity where id = :id")
    suspend fun getMovie(id: Int): ResultEntity

    @Upsert
    suspend fun upsertMovies(movies: List<ResultEntity>)

    @Query("DELETE FROM resultentity")
    suspend fun clearAllMovies()

}