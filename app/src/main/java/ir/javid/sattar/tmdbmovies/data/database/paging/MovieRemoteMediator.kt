package ir.javid.sattar.tmdbmovies.data.database.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import ir.javid.sattar.tmdbmovies.data.database.MovieDatabase
import ir.javid.sattar.tmdbmovies.data.model.RemoteKeys
import ir.javid.sattar.tmdbmovies.data.model.ResultEntity
import ir.javid.sattar.tmdbmovies.data.remote.MoviesApi
@ExperimentalPagingApi
class MovieRemoteMediator(
    private val moviesApi: MoviesApi,
    private val movieDatabase: MovieDatabase,
) : RemoteMediator<Int, ResultEntity>() {

    private val movieImageDao = movieDatabase.movieDao()
    private val movieRemoteKeysDao = movieDatabase.movieRemoteKeys()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ResultEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = moviesApi.getMovies(page = currentPage)
            val endOfPaginationReached = response.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

//            Log.d("MovieRemoteMediator", "LoadType: $loadType Current Page: $currentPage Prev Page: $prevPage Next Page: $nextPage")

            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieImageDao.clearAllMovies()
                    movieRemoteKeysDao.clearRemoteKeys()
                }
                val keys = response.results.map { unsplashImage ->
                    RemoteKeys(
                        movieId = 0,
                        prevKey = prevPage,
                        nextKey = nextPage
                    )
                }
                val movies = response.results.map { it.toEntity() }
                movieRemoteKeysDao.upsertAll(keys)
                movieImageDao.upsertMovies(movies)

//                Log.d("MovieRemoteMediator", "Movies upserted: ${movies.size}")
//                Log.d("MovieRemoteMediator", "RemoteKeys upserted: ${keys.size}")
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ResultEntity>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.dbID?.let { id ->
                Log.d("MovieRemoteMediator", "getRemoteKeyClosestToCurrentPosition: data -> ${id} remote key ${movieRemoteKeysDao.remoteKeysMovieId(movieId = id)}")
                movieRemoteKeysDao.remoteKeysMovieId(movieId = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, ResultEntity>
    ): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                Log.d("MovieRemoteMediator", "getRemoteKeyForFirstItem: data -> ${movie.dbID} remote key ${movieRemoteKeysDao.remoteKeysMovieId(movieId = movie.dbID)}")
                movieRemoteKeysDao.remoteKeysMovieId(movieId = movie.dbID)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, ResultEntity>
    ): RemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()
            ?.let { movie ->
                Log.d("MovieRemoteMediator", "getRemoteKeyForLastItem: data -> ${movie.dbID} remote key ${movieRemoteKeysDao.remoteKeysMovieId(movieId = movie.dbID)}")
                movieRemoteKeysDao.remoteKeysMovieId(movieId = movie.dbID)
            }
    }
}
