package dev.test.project.presentation.view

import dev.test.project.items.Movie
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.SingleState

interface FavoriteListView: MvpView {
    @AddToEnd
    fun showMovies(movies: List<Movie>)
    @SingleState
    fun showLoading()
    @AddToEnd
    fun showEmptyList()
}