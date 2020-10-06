package dev.test.project.presentation.view

import dev.test.project.items.Movie
import dev.test.project.items.MoviesObject
import moxy.MvpView
import moxy.viewstate.strategy.alias.SingleState

interface MovieListView: MvpView {

    /**
     * Показать список
     */
    @SingleState
    fun showList(data: MoviesObject)

    /**
     * Задать список фильмов
     */
    @SingleState
    fun setMoviesList(movies: List<Movie>)

    /**
     * Показать сообщение об ошибке
     */
    @SingleState
    fun showError(error: String)

    /**
     * Показать процесс загрузки
     */
    @SingleState
    fun showLoading()
}