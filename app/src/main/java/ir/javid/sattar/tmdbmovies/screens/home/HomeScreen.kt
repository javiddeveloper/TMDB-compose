package ir.javid.sattar.tmdbmovies.screens.home

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import ir.javid.sattar.tmdbmovies.BuildConfig
import ir.javid.sattar.tmdbmovies.graph.Roots


@Composable
fun HomeScreen(
    navController: NavHostController,
//    homeViewModel: HomeViewModel = hiltViewModel()
) {
    Text(text = BuildConfig.API_KEY)
//    val getAllImages = homeViewModel.getAllImages.collectAsLazyPagingItems()

//    Scaffold(
//        topBar = {
//            HomeTopBar(
//                onSearchClicked = {
//                    navController.navigate(Roots.OTHER)
//                }
//            )
//        },
//        content = { padding ->
//            padding
//            ListContent(items = getAllImages)
//        }
//    )
}