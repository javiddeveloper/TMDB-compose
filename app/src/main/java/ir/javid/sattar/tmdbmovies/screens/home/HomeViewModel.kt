package ir.javid.sattar.tmdbmovies.screens.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.javid.sattar.tmdbmovies.data.repository.MovieRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) :ViewModel(){
    val getAllMovie = movieRepository.getPagingMovies()

}