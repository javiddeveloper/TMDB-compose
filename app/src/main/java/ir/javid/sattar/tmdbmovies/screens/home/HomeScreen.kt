package ir.javid.sattar.tmdbmovies.screens.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.util.DebugLogger
import ir.javid.sattar.tmdbmovies.R
import ir.javid.sattar.tmdbmovies.data.model.ResultEntity
import ir.javid.sattar.tmdbmovies.screens.HomeTopBar
import ir.javid.sattar.tmdbmovies.util.imageURLW300
import okhttp3.OkHttpClient

@ExperimentalMaterial3Api
@ExperimentalCoilApi
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val moviesItems = homeViewModel.getAllMovie.collectAsLazyPagingItems()

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
            Box(modifier = Modifier.padding(padding)) {
                ListContent(moviesItems = moviesItems ){ itemClick ->
//                    navController.navigate(Roots.DETAIL)
                }
            }
        }
    )
}

@ExperimentalCoilApi
@Composable
fun ListContent(moviesItems: LazyPagingItems<ResultEntity>, itemClick:(ResultEntity)->Unit) {
    Log.d("Error", moviesItems.loadState.toString())
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
                MovieItem(item = item,itemClick)
            }
        }
    }
}

@Composable
fun MovieItem(item: ResultEntity, itemClick: (ResultEntity) -> Unit) {
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .logger(DebugLogger())
        .okHttpClient {
            OkHttpClient.Builder()
                .hostnameVerifier { _, _ -> true }
                .build()
        }
        .build()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable {
                itemClick.invoke(item)
            }
    ) {
        AsyncImage(
            modifier = Modifier
                .aspectRatio(2f / 3f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(3.dp)),
            imageLoader= imageLoader,
            model = ImageRequest.Builder(LocalContext.current)
                .data(item.posterPath.imageURLW300())
                .crossfade(700)
                .build(),

            contentDescription = item.title,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.play_circle_outline),
            error = painterResource(R.drawable.error_red)
        )
        Text(
            text = item.title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth()
        )
    }
}