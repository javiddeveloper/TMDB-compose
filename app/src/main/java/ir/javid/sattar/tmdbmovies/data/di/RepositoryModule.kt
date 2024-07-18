package ir.javid.sattar.tmdbmovies.data.di


import androidx.paging.ExperimentalPagingApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.javid.sattar.tmdbmovies.data.database.dao.MovieDao
import ir.javid.sattar.tmdbmovies.data.database.dao.MovieRemoteKeysDao
import ir.javid.sattar.tmdbmovies.data.remote.MoviesApi
import ir.javid.sattar.tmdbmovies.data.remote.interceptors.RequestInterceptor
import ir.javid.sattar.tmdbmovies.data.repository.MovieRepository
import ir.javid.sattar.tmdbmovies.data.repository.MovieRepositoryImpl
import ir.javid.sattar.tmdbmovies.util.Constants.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@ExperimentalPagingApi
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    @Singleton
    fun provideMovieRepository(
        moviesApi: MoviesApi,
        movieDao: MovieDao,
        movieRemoteKeysDao: MovieRemoteKeysDao
    ): MovieRepository {
        return MovieRepositoryImpl(moviesApi,movieDao,movieRemoteKeysDao)
    }
}