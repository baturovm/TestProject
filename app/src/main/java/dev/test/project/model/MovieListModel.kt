package dev.test.project.model

import dev.test.project.interfaces.RetrofitCallback
import dev.test.project.items.Genre
import dev.test.project.items.Movie
import dev.test.project.items.MoviesObject

interface MovieListModel {

    /**
     * Отмена соединения
     */
    fun cancelAll()

    /**
     * Запрос данных
     */
    fun fetchMovies(callback: RetrofitCallback<MoviesObject>)

    /**
     * Добавить в избранное
     */
    fun addFavorite(item: Movie)

    /**
     * Удалить из избранного
     */
    fun deleteFavorite(id: Int)

    /**
     * Достаем список жанров
     * @param list список фильмов
     * @return список жанров
     */
    fun getGenres(list: List<Movie>): List<Genre>

    /**
     * Получение списка избранного
     * @param list список фильмов
     * @return список избранного
     */
    suspend fun getFavoritedMovies(list: List<Movie>): List<Movie>

    /**
     * Фильтр данных по жанру
     * @param genre фильтруемый жанр
     * @param movies список фильмов
     * @return отфильтрованный список фильмов
     */
    suspend fun filterData(genre: String, movies: List<Movie>): List<Movie>
}