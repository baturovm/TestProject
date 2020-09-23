package dev.test.project.presentation.presenter

import dev.test.project.presentation.view.MovieInfoView
import dev.test.project.items.Movie
import dev.test.project.model.MovieInfoModel
import moxy.MvpPresenter

class MovieInfoPresenter : MvpPresenter<MovieInfoView>() {

    var movie: Movie? = null
    private val model = MovieInfoModel()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        movie?.let { viewState.initView(it) }
    }

    //Отмена соединения
    override fun onDestroy() {
        super.onDestroy()
        model.cancelAll()
    }

    fun changeFavorite(favorited: Boolean) {
        movie?.favorited =favorited
        if (favorited)
            model.addFavorite(movie!!)
        else
            model.deleteFavorite(movie!!)
    }

}