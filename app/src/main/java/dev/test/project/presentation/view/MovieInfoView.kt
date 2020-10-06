package dev.test.project.presentation.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.OneExecution
import moxy.viewstate.strategy.alias.SingleState

interface MovieInfoView: MvpView {

    /**
     * Показать информацию о фильме
     */
    @SingleState
    fun showMovieInfo()

    /**
     * Получение информации о фильме
     */
    @OneExecution
    fun setMovie()
}