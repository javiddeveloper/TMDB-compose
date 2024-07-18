package ir.javid.sattar.tmdbmovies.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.javid.sattar.tmdbmovies.data.database.MovieDatabase
import ir.javid.sattar.tmdbmovies.data.database.dao.MovieDao
import ir.javid.sattar.tmdbmovies.data.database.dao.MovieRemoteKeysDao
import ir.javid.sattar.tmdbmovies.util.Constants.MOVIE_DATABASE
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            MOVIE_DATABASE
        ).build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(database: MovieDatabase): MovieDao {
        return  database.movieDao()
    }
    @Singleton
    @Provides
    fun provideMovieRemoteKeysDao(database: MovieDatabase): MovieRemoteKeysDao {
        return  database.movieRemoteKeysDao()
    }

}