package dev.test.project.data

import dev.test.project.R
import dev.test.project.items.Genre
import dev.test.project.items.Movie
import dev.test.project.items.MoviesObject
import dev.test.project.utils.ResourceUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Callback

/**
 * Получение и обработка данных
 * - Получение отсортированного списка фильмов и жанров
 * - Фильтр фильмов по жанру
 */
class DataManager(private val database: DatabaseManager) {

    private val retrofitHelper = RetrofitManager()

    /**
     * Отмена соединения
     */
    fun cancel() {
        retrofitHelper.cancel()
    }

    /**
     * Запрос данных
     */
    fun getMovies(callback: Callback<MoviesObject>) {
        retrofitHelper.fetchMovies(callback)
    }

    /**
     * Получение обработанного списка
     * @param data данные из сети
     * @return отсортированный список фильмов и жанры
     */
    suspend fun mapData(data: MoviesObject): MutableList<Any>
            = withContext(Dispatchers.IO) {
        data.movies = data.movies.sortedBy { it.titleRU }
        data.movies = getFavoritedMovies(data.movies)
        data.genres = getGenres(data.movies)
        return@withContext mutableListOf<Any>().apply {
            add(ResourceUtils.getString(R.string.genres))
            addAll(data.genres)
            add(ResourceUtils.getString(R.string.movies))
            addAll(data.movies)
        }
    }

    /**
     * Получение списка избранного
     * @param list список фильмов
     * @return список избранного
     */
    private suspend fun getFavoritedMovies(list: List<Movie>): List<Movie>
            = withContext(Dispatchers.IO) {
        list.forEach { movie ->
            withContext(Dispatchers.Main) {
                movie.favorited = database.containsItem(movie.id)
            }
        }
        return@withContext list
    }

    /**
     * Достаем список жанров
     * @param list список фильмов
     * @return список жанров
     */
    private suspend fun getGenres(list: List<Movie>): List<Genre>
            = withContext(Dispatchers.IO) {
        var genres = mutableListOf<String>()
        list.forEach { movie ->
            genres.addAll(movie.genres!!)
        }
        genres = genres.distinct().toMutableList()
        return@withContext mutableListOf<Genre>().apply {
            genres.forEach {
                add(Genre(it, it.hashCode()))
            }
        }
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