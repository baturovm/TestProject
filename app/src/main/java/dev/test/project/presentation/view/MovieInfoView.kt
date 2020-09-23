package dev.test.project.presentation.view

import dev.test.project.items.Movie
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd

interface MovieInfoView: MvpView {

    @AddToEnd
    fun initView(movie: Movie)

}