package dev.test.project.model

import dev.test.project.data.DatabaseHelper
import dev.test.project.items.Movie

/**
 * Model для работы с информацией о фильме
 */
class MovieInfoModel {

    private val database: DatabaseHelper = DatabaseHelper()

    //Закрытие db
    fun cancelAll() {
        database.close()
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