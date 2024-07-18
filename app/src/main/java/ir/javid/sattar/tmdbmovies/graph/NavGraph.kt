package ir.javid.sattar.tmdbmovies.graph

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import ir.javid.sattar.tmdbmovies.screens.detail.DetailScreen
import ir.javid.sattar.tmdbmovies.screens.home.HomeScreen

@ExperimentalCoilApi
@ExperimentalMaterial3Api
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Roots.Home.route
    ) {
        composable(route = Roots.Home.route){
            HomeScreen(navController = navController)
        }

        composable(
            route = "${Roots.Detail.route}/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: -1
            DetailScreen(navController = navController, movieId = movieId)
        }

    }
}