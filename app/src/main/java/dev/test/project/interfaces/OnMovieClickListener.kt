package dev.test.project.interfaces

import dev.test.project.items.Genre
import dev.test.project.items.Movie

interface OnMovieClickListener {
    fun onMovieClick(item: Movie)
    fun onFavoriteClick(favorited: Boolean, item: Movie)
}