package dev.test.project.adapters.holders.listeners

import dev.test.project.items.Genre

interface GenreListener {
    fun onGenreClick(item: Genre)
}