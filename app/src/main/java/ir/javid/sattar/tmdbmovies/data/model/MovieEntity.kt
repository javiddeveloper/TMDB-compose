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

@Entity(tableName = "result_entity")
data class ResultEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "adult") val adult: Boolean,
    @ColumnInfo(name = "backdropPath") val backdropPath: String,
    @ColumnInfo(name = "genreIds") val genreIds: String,
    @ColumnInfo(name = "originalLanguage") val originalLanguage: String,
    @ColumnInfo(name = "originalTitle") val originalTitle: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "popularity") val popularity: Double,
    @ColumnInfo(name = "posterPath") val posterPath: String,
    @ColumnInfo(name = "releaseDate") val releaseDate: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "video") val video: Boolean,
    @ColumnInfo(name = "voteAverage") val voteAverage: Double,
    @ColumnInfo(name = "voteCount") val voteCount: Int
) : EntityModel