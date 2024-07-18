package ir.javid.sattar.tmdbmovies.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@ExperimentalMaterial3Api
@Composable
fun HomeTopBar(
    title: String,
    onSearchClicked: () -> Unit
) {
    TopAppBar(
        title = { Text(text = title) },
        actions = {
            IconButton(onClick = onSearchClicked) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
        }
    )
}