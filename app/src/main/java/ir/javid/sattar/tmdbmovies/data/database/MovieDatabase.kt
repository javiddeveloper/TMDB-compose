package ir.javid.sattar.tmdbmovies.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ir.javid.sattar.tmdbmovies.data.database.dao.MovieDao
import ir.javid.sattar.tmdbmovies.data.database.dao.MovieRemoteKeysDao
import ir.javid.sattar.tmdbmovies.data.model.MovieRemoteKeys
import ir.javid.sattar.tmdbmovies.data.model.ResultEntity

@Database(entities = [ResultEntity::class, MovieRemoteKeys::class], version = 1)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun movieRemoteKeysDao(): MovieRemoteKeysDao

}