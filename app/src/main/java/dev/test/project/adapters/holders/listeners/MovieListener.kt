package dev.test.project.adapters.holders.listeners

import dev.test.project.items.Genre
import dev.test.project.items.Movie

interface MovieListener {
    fun onMovieClick(item: Movie)
    fun onFavoriteClick(item: Movie)
}