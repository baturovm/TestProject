package dev.test.project.model

import dev.test.project.data.DatabaseManager
import dev.test.project.data.RetrofitManager
import dev.test.project.interfaces.RetrofitCallback
import dev.test.project.items.Genre
import dev.test.project.items.Movie
import dev.test.project.items.MoviesObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Model для списка фильмов и жанров
 */
class MovieListModel {

    private val database: DatabaseManager = DatabaseManager()
    private val retrofitManager: RetrofitManager = RetrofitManager()

    /**
     * Отмена соединения
     */
    fun cancelAll() {
        retrofitManager.cancel()
    }

    /**
     * Запрос данных
     */
    fun fetchMovies(callback: RetrofitCallback<MoviesObject>) {
        retrofitManager.fetchMovies(callback)
    }

    /**
     * Добавить в избранное
     */
    fun addFavorite(item: Movie) {
        database.addItem(item)
    }

    /**
     * Удалить из избранного
     */
    fun deleteFavorite(id: Int) {
        database.deleteItem(id)
    }

    /**
     * Достаем список жанров
     * @param list список фильмов
     * @return список жанров
     */
    fun getGenres(list: List<Movie>): List<Genre> {
        var genres = mutableListOf<String>()
        list.forEach { movie ->
            genres.addAll(movie.genres!!)
        }
        genres = genres.distinct().toMutableList()
        return mutableListOf<Genre>().apply {
            genres.forEach {
                add(Genre(it, it.hashCode()))
            }
        }
    }

    /**
     * Получение списка избранного
     * @param list список фильмов
     * @return список избранного
     */
    suspend fun getFavoritedMovies(list: List<Movie>): List<Movie>
            = withContext(Dispatchers.IO) {
        list.forEach { movie ->
            withContext(Dispatchers.Main) {
                movie.favorited = database.containsItem(movie.id)
            }
        }
        return@withContext list
    }

    /**
     * Фильтр данных по жанру
     * @param genre фильтруемый жанр
     * @param movies список фильмов
     * @return отфильтрованный список фильмов
     */
    suspend fun filterData(genre: String, movies: List<Movie>): List<Movie>
            = withContext(Dispatchers.IO){
        val filteredList = mutableListOf<Movie>()
        movies.forEach { movie ->
            if (movie.genres!!.contains(genre)) {
                filteredList.add(movie)
            }
        }
        return@withContext filteredList
    }
}