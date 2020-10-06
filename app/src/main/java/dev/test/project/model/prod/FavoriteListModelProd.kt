package dev.test.project.model.prod

import dev.test.project.data.DatabaseManager
import dev.test.project.items.Movie
import dev.test.project.model.FavoriteListModel

/**
 * Model для списка избранного
 */
class FavoriteListModelProd : FavoriteListModel {

    private val database = DatabaseManager()

    override fun getFavoriteList(): List<Movie> {
        return database.getItems()
    }

    override fun addFavorite(item: Movie) {
        database.addItem(item)
    }

    override fun deleteFavorite(item: Movie) {
        database.deleteItem(item.id)
    }
}