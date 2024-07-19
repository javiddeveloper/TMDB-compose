package ir.javid.sattar.tmdbmovies.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class MovieEntity(
    val page: Int,
    val results: List<ResultEntity>,
    val totalPages: Int,
    val totalResults: Int
) : EntityModel

@Entity
data class ResultEntity(
    @PrimaryKey
     val id: Int,
     val adult: Boolean,
     val backdropPath: String?,
//     val genreIds: String,
     val originalLanguage: String,
     val originalTitle: String,
     val overview: String,
     val popularity: Double,
     val posterPath: String,
     val releaseDate: String,
     val title: String,
     val video: Boolean,
     val voteAverage: Double,
     val voteCount: Int,
) : EntityModel