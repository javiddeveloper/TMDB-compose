package ir.javid.sattar.tmdbmovies.graph

sealed class Roots(val route: String){
    object Home: Roots("home_screen")
}
