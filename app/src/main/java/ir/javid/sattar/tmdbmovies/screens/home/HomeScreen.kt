package ir.javid.sattar.tmdbmovies.screens.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import ir.javid.sattar.tmdbmovies.BuildConfig
import ir.javid.sattar.tmdbmovies.data.model.ResultEntity
import ir.javid.sattar.tmdbmovies.graph.Roots


@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val moviesItems = homeViewModel.getAllMovie.collectAsLazyPagingItems()

    Scaffold(
//        topBar = {
//            HomeTopBar(
//                onSearchClicked = {
//                    navController.navigate(Roots.OTHER)
//                }
//            )
//        },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                ListContent(moviesItems = moviesItems)
            }
        }
    )
}

@Composable
fun ListContent(moviesItems: LazyPagingItems<ResultEntity>) {
    Log.d("Error", moviesItems.loadState.toString())
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            moviesItems.itemCount
        ) { index ->

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = moviesItems[index]?.title.toString())
            }
        }
    }
}