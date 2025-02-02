package ir.javid.sattar.tmdbmovies.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ir.javid.sattar.tmdbmovies.data.model.ResultEntity

@Dao
interface MovieDao {


    @Query("SELECT * FROM resultentity")
    fun getAllMovies(): PagingSource<Int, ResultEntity>

    @Query("SELECT * FROM resultentity where dbID = :id")
    suspend fun getMovie(id: Int): ResultEntity

    @Query("SELECT * FROM resultentity WHERE title LIKE '%' || :query || '%'")
    fun searchMovies(query: String): PagingSource<Int, ResultEntity>

    @Upsert
    suspend fun upsertMovies(movies: List<ResultEntity>)

    @Query("DELETE FROM resultentity")
    suspend fun clearAllMovies()

}