package ir.javid.sattar.tmdbmovies.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.javid.sattar.tmdbmovies.data.repository.MovieRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) :ViewModel(){
    val moviePagingFlow = movieRepository.getPagingMovies()
        .flow
        .cachedIn(viewModelScope)

}