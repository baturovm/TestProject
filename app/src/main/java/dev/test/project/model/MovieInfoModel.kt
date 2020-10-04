package dev.test.project.model

import dev.test.project.data.DatabaseManager
import dev.test.project.items.Movie

/**
 * Model для работы с информацией о фильме
 */
class MovieInfoModel {

    private val database: DatabaseManager = DatabaseManager()

    //Добавить в избранное
    fun addFavorite(item: Movie) {
        database.addItem(item)
    }

    //Удалить из избранного
    fun deleteFavorite(item: Movie) {
        database.deleteItem(item.id)
    }
}