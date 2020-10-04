package dev.test.project.interfaces

import dev.test.project.items.Genre

interface GenreListener {
    fun onGenreClick(item: Genre)
}