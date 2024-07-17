package ir.javid.sattar.tmdbmovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.javid.sattar.tmdbmovies.graph.SetupNavGraph
import ir.javid.sattar.tmdbmovies.ui.theme.TMDBmoviesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TMDBmoviesTheme {
                    val navController = rememberNavController()
                    SetupNavGraph(navController = navController)
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        TMDBmoviesTheme {
            SetupNavGraph(navController = rememberNavController())
        }
    }
}


