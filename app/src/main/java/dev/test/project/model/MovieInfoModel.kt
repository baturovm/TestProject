package dev.test.project.model

import dev.test.project.items.Movie

interface MovieInfoModel {

    /**
     * Добавить в избранное
     */
    fun addFavorite(item: Movie)

    /**
     * Удалить из избранного
     */
    fun deleteFavorite(item: Movie)
}