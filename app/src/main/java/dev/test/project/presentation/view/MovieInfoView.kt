package dev.test.project.presentation.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd

interface MovieInfoView: MvpView {

    /**
     * Показать информацию о фильме
     */
    @AddToEnd
    fun showMovieInfo()
}