package ir.javid.sattar.tmdbmovies.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.javid.sattar.tmdbmovies.data.repository.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
@ExperimentalCoroutinesApi
@FlowPreview
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) :ViewModel(){

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val moviePagingFlow = searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            if (query.isEmpty()) {
                movieRepository.getPagingMovies().flatMapLatest { it.flow }
            } else {
                movieRepository.getPagingMoviesByQuery(query).flatMapLatest { it.flow }
            }
        }
        .cachedIn(viewModelScope)

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

}