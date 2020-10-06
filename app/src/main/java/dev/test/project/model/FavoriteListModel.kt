package dev.test.project.model

import dev.test.project.items.Movie

interface FavoriteListModel {

    /**
     * Получение списка избранного
     */
    fun getFavoriteList(): List<Movie>

    /**
     * Добавить в избранное
     */
    fun addFavorite(item: Movie)

    /**
     * Удалить из избранного
     */
    fun deleteFavorite(item: Movie)
}