package dev.test.project.presentation.presenter

import dev.test.project.presentation.view.MovieInfoView
import dev.test.project.items.Movie
import dev.test.project.model.MovieInfoModel
import moxy.MvpPresenter

/**
 * Презентер для экрана информации по фильму
 */
class MovieInfoPresenter(private val model: MovieInfoModel) : MvpPresenter<MovieInfoView>() {

    var movie: Movie? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setMovie()
        viewState.showMovieInfo()
    }

    /**
     * Изменения статуса избранного фильма
     */
    fun changeFavorite(favorited: Boolean) {
        movie!!.favorited = favorited
        if (favorited)
            model.addFavorite(movie!!)
        else
            model.deleteFavorite(movie!!)
    }

}