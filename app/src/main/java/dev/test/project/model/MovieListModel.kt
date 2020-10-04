package dev.test.project.model

import dev.test.project.data.DataHelper
import dev.test.project.data.DatabaseManager
import dev.test.project.items.Movie
import dev.test.project.items.MoviesObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Callback

/**
 * Model для списка фильмов и жанров
 */
class MovieListModel {

    private val database: DatabaseManager = DatabaseManager()
    private val dataHelper: DataHelper = DataHelper(database)

    //Отмена соединения и закрытие db
    fun cancelAll() {
        dataHelper.cancel()
    }

    //Запрос данных
    fun fetchMovies(callback: Callback<MoviesObject>) {
        dataHelper.getMovies(callback)
    }

    //Подготовка данных
    suspend fun mapData(data: MoviesObject): MutableList<Any> = withContext(Dispatchers.Main){
        return@withContext dataHelper.mapData(data)
    }

    //Фильтр данных по жанру
    suspend fun filterData(genre: String, movies: List<Movie>): List<Movie> {
        return dataHelper.filterData(genre, movies)
    }

    //Добавить в избранное
    fun addFavorite(item: Movie) {
        database.addItem(item)
    }

    //Удалить из избранного
    fun deleteFavorite(id: Int) {
        database.deleteItem(id)
    }
}