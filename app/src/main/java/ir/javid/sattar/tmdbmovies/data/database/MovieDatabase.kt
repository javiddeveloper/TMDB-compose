package ir.javid.sattar.tmdbmovies.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ir.javid.sattar.tmdbmovies.data.database.dao.MovieDao
import ir.javid.sattar.tmdbmovies.data.database.dao.RemoteKeysDao
import ir.javid.sattar.tmdbmovies.data.model.RemoteKeys
import ir.javid.sattar.tmdbmovies.data.model.ResultEntity

@Database(entities = [ResultEntity::class,RemoteKeys::class], version = 1)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun movieRemoteKeys(): RemoteKeysDao

}