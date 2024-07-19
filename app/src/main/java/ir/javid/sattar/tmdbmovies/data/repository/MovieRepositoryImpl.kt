package ir.javid.sattar.tmdbmovies.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.withTransaction
import ir.javid.sattar.tmdbmovies.data.database.MovieDatabase
import ir.javid.sattar.tmdbmovies.data.database.paging.MovieRemoteMediator
import ir.javid.sattar.tmdbmovies.data.model.ResultEntity
import ir.javid.sattar.tmdbmovies.data.remote.MoviesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
@ExperimentalPagingApi
class MovieRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    private val movieDb: MovieDatabase,
) : MovieRepository {
    override fun getPagingMovies(): Pager<Int, ResultEntity> {
        val pagingSourceFactory = { movieDb.movieDao().getAllMovies() }
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = MovieRemoteMediator(
                moviesApi = moviesApi,
                movieDatabase = movieDb
            ),
            pagingSourceFactory = pagingSourceFactory
        )
    }
    override fun getMovie(id: Int): Flow<ResultEntity> = callbackFlow {
        movieDb.withTransaction {
            val movie = movieDb.movieDao().getMovie(id)
            trySend(movie)
        }
        awaitClose { cancel() }
    }

}