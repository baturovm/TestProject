package dev.test.project.model.prod

import dev.test.project.data.DatabaseManager
import dev.test.project.items.Movie
import dev.test.project.model.MovieInfoModel

/**
 * Model для работы с информацией о фильме
 */
class MovieInfoModelProd : MovieInfoModel {

    private val database: DatabaseManager = DatabaseManager()

    override fun addFavorite(item: Movie) {
        database.addItem(item)
    }

    override fun deleteFavorite(item: Movie) {
        database.deleteItem(item.id)
    }
}