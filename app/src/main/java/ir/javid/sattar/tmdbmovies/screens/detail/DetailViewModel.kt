package ir.javid.sattar.tmdbmovies.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.javid.sattar.tmdbmovies.data.model.ResultEntity
import ir.javid.sattar.tmdbmovies.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _movie = MutableStateFlow<ResultEntity?>(null)
    val movie: StateFlow<ResultEntity?> = _movie.asStateFlow()

    fun getMovieDetails(movieId: Int) = viewModelScope.launch {
        movieRepository.getMovie(movieId).collectLatest {
            try {
                _movie.value = it
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}