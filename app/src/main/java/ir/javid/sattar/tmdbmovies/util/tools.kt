package ir.javid.sattar.tmdbmovies.util

fun String.imageURLW300():String = buildString{
    append("https://image.tmdb.org/t/p/w300")
    append(this@imageURLW300)
}

fun String.imageURLW500():String = buildString{
    append("https://image.tmdb.org/t/p/w500")
    append(this@imageURLW500)
}

fun String.imageURL():String = buildString{
    append("https://image.tmdb.org/t/p/original")
    append(this@imageURL)
}