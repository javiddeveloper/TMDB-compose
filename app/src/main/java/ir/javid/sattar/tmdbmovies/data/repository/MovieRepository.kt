package ir.javid.sattar.tmdbmovies.data.repository

import androidx.paging.PagingData
import ir.javid.sattar.tmdbmovies.data.model.ResultEntity
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getPagingMovies() : Flow<PagingData<ResultEntity>>

    fun getMovie(id: Int): Flow<ResultEntity>
}