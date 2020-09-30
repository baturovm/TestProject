package dev.test.project.presentation.presenter

import dev.test.project.presentation.view.MovieInfoView
import dev.test.project.items.Movie
import dev.test.project.model.MovieInfoModel
import moxy.MvpPresenter

/**
 * Презентер для экрана информации по фильму
 */
class MovieInfoPresenter : MvpPresenter<MovieInfoView>() {

    var movie: Movie? = null
    private val model = MovieInfoModel()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initView()
    }

    //Добавление или удаление из избранного
    fun changeFavorite(favorited: Boolean) {
        movie?.favorited =favorited
        if (favorited)
            model.addFavorite(movie!!)
        else
            model.deleteFavorite(movie!!)
    }

}