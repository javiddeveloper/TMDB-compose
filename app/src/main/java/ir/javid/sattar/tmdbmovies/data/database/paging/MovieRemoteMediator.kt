package ir.javid.sattar.tmdbmovies.data.database.paging

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

            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieImageDao.clearAllMovies()
                    movieRemoteKeysDao.clearRemoteKeys()
                }
                val keys = response.results.map { unsplashImage ->
                    RemoteKeys(
                        movieId = unsplashImage.id,
                        prevKey = prevPage,
                        nextKey = nextPage
                    )
                }
                val movies = response.results.map { it.toEntity() }
                movieRemoteKeysDao.upsertAll(keys)
                movieImageDao.upsertMovies(movies)
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
            state.closestItemToPosition(position)?.id?.let { id ->
                movieRemoteKeysDao.remoteKeysMovieId(movieId = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, ResultEntity>
    ): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                movieRemoteKeysDao.remoteKeysMovieId(movieId = movie.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, ResultEntity>
    ): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                movieRemoteKeysDao.remoteKeysMovieId(movieId = movie.id)
            }
    }

}

//@ExperimentalPagingApi
//class MovieRemoteMediator(
//    private val moviesApi: MoviesApi,
//    private val movieDb: MovieDatabase,
//) : RemoteMediator<Int, ResultEntity>() {
//    private var currentPage = 1
//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, ResultEntity>
//    ): MediatorResult {
//        return try {
//            val loadKey = when(loadType){
//                REFRESH -> 1
//                PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
//                APPEND -> {
//                    val lastItem = state.lastItemOrNull()
//                    if (lastItem == null) {
//                        1
//                    } else {
//                      //  handle next page
//                        currentPage + 1
//                    }
//                }
//            }
//            val movies = moviesApi.getMovies(loadKey)
//            currentPage = movies.page
//            movieDb.withTransaction {
//                if(loadType == LoadType.REFRESH) {
//                    movieDb.movieDao().clearAllMovies()
//                }
//                val movieEntities = movies.results.map { it.toEntity() }
//                movieDb.movieDao().upsertMovies(movieEntities)
//            }
//
//            MediatorResult.Success(
//                endOfPaginationReached = movies.results.isEmpty()
//            )
//        } catch (e: Exception) {
//            return MediatorResult.Error(e)
//        }
//    }
//}