package dev.test.project.interfaces

import dev.test.project.items.Genre
import dev.test.project.items.Movie

interface MovieListener {
    fun onMovieClick(item: Movie)
    fun onFavoriteClick(item: Movie)
}