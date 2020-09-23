package dev.test.project.items

import com.google.gson.annotations.SerializedName

data class MoviesObject(
    @SerializedName("films")
    var movies: List<Movie>,
    var genres: List<Genre>
)