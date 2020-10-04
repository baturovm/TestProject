package dev.test.project.model

import dev.test.project.data.DatabaseManager
import dev.test.project.items.Movie

/**
 * Model для списка избранного
 */
class FavoriteListModel {

    private val database = DatabaseManager()

    //Получение списка избранного
    fun getFavoriteList(): List<Movie> {
        return database.getItems()
    }

    //Добавить в избранное
    fun addFavorite(item: Movie) {
        database.addItem(item)
    }

    //Удалить из избранного
    fun deleteFavorite(item: Movie) {
        database.deleteItem(item.id)
    }
}