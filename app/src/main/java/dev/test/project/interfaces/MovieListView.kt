package dev.test.project.interfaces

import dev.test.project.items.Movie
import dev.test.project.items.MoviesObject
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
    @Skip
    fun showError()
    @SingleState
    fun showLoading(value: Boolean)
    @AddToEndSingle
    fun showEmptyList()
}