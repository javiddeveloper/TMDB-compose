package ir.javid.sattar.tmdbmovies.screens.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import ir.javid.sattar.tmdbmovies.data.model.ResultEntity
import ir.javid.sattar.tmdbmovies.graph.Roots
import ir.javid.sattar.tmdbmovies.screens.HomeTopBar

@ExperimentalMaterial3Api
@ExperimentalCoilApi
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val moviesItems = homeViewModel.moviePagingFlow.collectAsLazyPagingItems()
    val context = LocalContext.current
    LaunchedEffect(key1 = moviesItems.loadState) {
        if (moviesItems.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (moviesItems.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Scaffold(
        topBar = {
            HomeTopBar(
                "TMDB movies",
                onSearchClicked = {
//                    navController.navigate(Roots.OTHER)
                }
            )
        },
        content = { padding ->

            Box(modifier = Modifier
                .padding(padding)
                .fillMaxSize()) {
                if (moviesItems.loadState.refresh is LoadState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    ListContent(moviesItems = moviesItems) { itemClick ->
                        navController.navigate("${Roots.Detail.route}/${itemClick.id}")
                    }
                }
            }
        }
    )
}

@ExperimentalCoilApi
@Composable
fun ListContent(moviesItems: LazyPagingItems<ResultEntity>, itemClick: (ResultEntity) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            moviesItems.itemCount
        ) {
            val item = moviesItems[it]
            if (item != null) {
                MovieItem(item = item, itemClick)
            }
        }

        item {
            if(moviesItems.loadState.append is LoadState.Loading) {
                CircularProgressIndicator()
            }
        }
    }
}
