package dev.test.project.presentation.view

import dev.test.project.items.Movie
import moxy.MvpView
import moxy.viewstate.strategy.alias.SingleState

interface FavoriteListView: MvpView {

    /**
     * Показать список фильмов
     */
    @SingleState
    fun showMovies(movies: List<Movie>)

    /**
     * Показать процесс загрузки
     */
    @SingleState
    fun showLoading()

    /**
     * Показать placeholder пустого списка
     */
    @SingleState
    fun showEmptyList()
}