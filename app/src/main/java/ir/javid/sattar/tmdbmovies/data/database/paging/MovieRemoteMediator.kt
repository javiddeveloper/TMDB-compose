package ir.javid.sattar.tmdbmovies.data.database.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import ir.javid.sattar.tmdbmovies.data.database.MovieDatabase
import ir.javid.sattar.tmdbmovies.data.database.dao.MovieDao
import ir.javid.sattar.tmdbmovies.data.database.dao.MovieRemoteKeysDao
import ir.javid.sattar.tmdbmovies.data.model.MovieRemoteKeys
import ir.javid.sattar.tmdbmovies.data.model.ResultEntity
import ir.javid.sattar.tmdbmovies.data.remote.MoviesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@ExperimentalPagingApi
class MovieRemoteMediator(
    private val moviesApi: MoviesApi,
    private val movieDao: MovieDao,
    private val movieRemoteKeysDao: MovieRemoteKeysDao,
) : RemoteMediator<Int, ResultEntity>() {


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ResultEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
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

            if (loadType == LoadType.REFRESH) {
                withContext(Dispatchers.Default){
                    movieDao.deleteAllMovies()
                    movieRemoteKeysDao.deleteAllRemoteKeys()
                }
            }
            val keys = response.results.map { movie ->
                MovieRemoteKeys(
                    id = movie.id,
                    prevPage = prevPage,
                    nextPage = nextPage
                )
            }
            movieRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
            val entityModel = response.results.map { it.toEntity() }
            movieDao.addMovies(movies = entityModel)


            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ResultEntity>
    ): MovieRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                movieRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, ResultEntity>
    ): MovieRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { unsplashImage ->
                movieRemoteKeysDao.getRemoteKeys(id = unsplashImage.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, ResultEntity>
    ): MovieRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { unsplashImage ->
                movieRemoteKeysDao.getRemoteKeys(id = unsplashImage.id)
            }
    }

}