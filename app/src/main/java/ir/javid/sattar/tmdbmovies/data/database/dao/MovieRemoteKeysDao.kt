package ir.javid.sattar.tmdbmovies.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.javid.sattar.tmdbmovies.data.model.MovieRemoteKeys

@Dao
interface MovieRemoteKeysDao {

    @Query("SELECT * FROM movie_remote_keys WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): MovieRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<MovieRemoteKeys>)

    @Query("DELETE FROM movie_remote_keys")
    suspend fun deleteAllRemoteKeys()

}