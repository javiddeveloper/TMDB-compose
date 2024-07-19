package ir.javid.sattar.tmdbmovies.data.di

import androidx.paging.ExperimentalPagingApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.javid.sattar.tmdbmovies.data.database.MovieDatabase
import ir.javid.sattar.tmdbmovies.data.remote.MoviesApi
import ir.javid.sattar.tmdbmovies.data.repository.MovieRepository
import ir.javid.sattar.tmdbmovies.data.repository.MovieRepositoryImpl
import javax.inject.Singleton

@ExperimentalPagingApi
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        moviesApi: MoviesApi,
        movieDb: MovieDatabase,
    ): MovieRepository {
        return MovieRepositoryImpl(moviesApi,movieDb)
    }
}