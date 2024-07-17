package ir.javid.sattar.tmdbmovies.data.repository

import ir.javid.sattar.tmdbmovies.data.remote.MoviesApi
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi
) :MovieRepository {
    override fun a(): String {
       return " helllo"
    }

}