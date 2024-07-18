package ir.javid.sattar.tmdbmovies.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import ir.javid.sattar.tmdbmovies.data.database.dao.MovieDao
import ir.javid.sattar.tmdbmovies.data.database.dao.MovieRemoteKeysDao
import ir.javid.sattar.tmdbmovies.data.database.paging.MovieRemoteMediator
import ir.javid.sattar.tmdbmovies.data.model.MovieEntity
import ir.javid.sattar.tmdbmovies.data.model.ResultEntity
import ir.javid.sattar.tmdbmovies.data.remote.MoviesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
@ExperimentalPagingApi
class MovieRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    private val movieDao: MovieDao,
    private val movieRemoteKeysDao: MovieRemoteKeysDao,
) : MovieRepository {
    override fun getPagingMovies(): Flow<PagingData<ResultEntity>> {
        val pagingSourceFactory = { movieDao.getAllMovies() }
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = MovieRemoteMediator(
                moviesApi = moviesApi,
                movieDao = movieDao,
                movieRemoteKeysDao = movieRemoteKeysDao,
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}