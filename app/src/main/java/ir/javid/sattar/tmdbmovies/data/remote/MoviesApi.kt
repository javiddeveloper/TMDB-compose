package ir.javid.sattar.tmdbmovies.data.remote

import ir.javid.sattar.tmdbmovies.data.model.MovieDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    @GET("discover/movie")
    suspend fun getMovies(
        @Query("page") page: Int
    ): MovieDto
}