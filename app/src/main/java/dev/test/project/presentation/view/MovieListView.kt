package dev.test.project.presentation.view

import dev.test.project.items.Movie
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.SingleState
import moxy.viewstate.strategy.alias.Skip

interface MovieListView: MvpView {
    @AddToEnd
    fun initView(list: MutableList<Any>)
    @AddToEnd
    fun showMovies(movies: List<Movie>)
    @AddToEnd
    fun showError(error: String?)
    @SingleState
    fun showLoading(value: Boolean)
}