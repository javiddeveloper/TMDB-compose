package ir.javid.sattar.tmdbmovies.data.database.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import ir.javid.sattar.tmdbmovies.data.database.MovieDatabase
import ir.javid.sattar.tmdbmovies.data.model.ResultEntity
import ir.javid.sattar.tmdbmovies.data.remote.MoviesApi

@ExperimentalPagingApi
class MovieRemoteMediator(
    private val moviesApi: MoviesApi,
    private val movieDb: MovieDatabase,
) : RemoteMediator<Int, ResultEntity>() {
    private var currentPage = 1
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ResultEntity>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType){
                REFRESH -> 1
                PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                      //  handle next page
                        currentPage + 1
                    }
                }
            }
            val movies = moviesApi.getMovies(loadKey)
            currentPage = movies.page
            movieDb.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    movieDb.movieDao().clearAllMovies()
                }
                val movieEntities = movies.results.map { it.toEntity() }
                movieDb.movieDao().upsertMovies(movieEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = movies.results.isEmpty()
            )
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}