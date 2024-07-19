package ir.javid.sattar.tmdbmovies.data.repository

import androidx.paging.Pager
import ir.javid.sattar.tmdbmovies.data.model.ResultEntity
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getPagingMovies() : Pager<Int, ResultEntity>

    fun getMovie(id: Int): Flow<ResultEntity>
}