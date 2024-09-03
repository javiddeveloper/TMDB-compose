package ir.javid.sattar.tmdbmovies.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@ExperimentalMaterial3Api
@Composable
fun HomeTopBar(
    title: String,
    onSearchQueryChanged: (String) -> Unit,
    searchQuery: String
) {
    var isSearchActive by remember { mutableStateOf(false) }
    var localSearchQuery by remember { mutableStateOf("") }

    TopAppBar(
        title = {
            if (isSearchActive) {
                TextField(
                    value = searchQuery,
                    onValueChange = {
                        localSearchQuery = it
                        onSearchQueryChanged.invoke(it)
                    },
                    placeholder = { Text("search") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                if (localSearchQuery.isNotEmpty()) {
                                    localSearchQuery = ""
                                } else {
                                    localSearchQuery = ""
                                    isSearchActive = false
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (searchQuery.isNotEmpty()) Icons.Default.Close else Icons.Default.Close,
                                contentDescription = ""
                            )
                        }
                    }
                )
            } else {
                Text(text = title)
            }
        },
        actions = {
            if (!isSearchActive) {
                IconButton(
                    onClick = {
                        isSearchActive = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Activate search"
                    )
                }
            }
        }
    )
}