package dev.test.project.interfaces

import dev.test.project.items.Movie

interface OnItemClickListener {
    fun onGenreClick(item: String?)
    fun onMovieClick(item: Movie)
}