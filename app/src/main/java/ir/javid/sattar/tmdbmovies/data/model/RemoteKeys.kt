package ir.javid.sattar.tmdbmovies.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKeys(
    @PrimaryKey(autoGenerate = true)
    val movieId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)