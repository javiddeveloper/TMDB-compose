package ir.javid.sattar.tmdbmovies.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.javid.sattar.tmdbmovies.data.model.ResultEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM result_entity")
    fun getAllMovies(): PagingSource<Int, ResultEntity>

    @Query("SELECT * FROM result_entity where id = :id")
    suspend fun getMovie(id: Int): ResultEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies: List<ResultEntity>)

    @Query("DELETE FROM result_entity")
    suspend fun deleteAllMovies()

}