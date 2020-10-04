package dev.test.project.presentation.view

import dev.test.project.items.Movie
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.SingleState

interface MovieListView: MvpView {

    /**
     * Показать список
     */
    @AddToEnd
    fun initView(list: MutableList<Any>)

    /**
     * Показать список фильмов
     */
    @AddToEnd
    fun showMovies(movies: List<Movie>)

    /**
     * Показать сообщение об ошибке
     */
    @AddToEnd
    fun showError(error: String)

    /**
     * Показать процесс загрузки
     */
    @SingleState
    fun showLoading()
}