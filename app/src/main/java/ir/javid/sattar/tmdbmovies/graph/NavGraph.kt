package ir.javid.sattar.tmdbmovies.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ir.javid.sattar.tmdbmovies.screens.home.HomeScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Roots.Home.route
    ) {
        composable(route = Roots.Home.route){
            HomeScreen(navController = navController)
        }

    }
}