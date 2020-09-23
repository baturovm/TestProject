package dev.test.project.presentation.presenter

import android.os.Parcelable
import dev.test.project.items.Movie
import dev.test.project.items.MoviesObject
import dev.test.project.model.FavoriteListModel
import dev.test.project.presentation.view.FavoriteListView
import moxy.MvpPresenter

class FavoriteListPresenter: MvpPresenter<FavoriteListView>() {

    var statePosition: Parcelable? = null

    private val model = FavoriteListModel()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getFavoriteList()
    }

    //Отмена соединения
    override fun onDestroy() {
        super.onDestroy()
        model.cancelAll()
    }

    private fun getFavoriteList() {
        val list = model.getFavoriteList()
        when {
            list.isEmpty() -> viewState.showEmptyList(true)
            else -> viewState.showMovies(list)
        }
    }

    fun changeFavorite(favorited: Boolean, item: Movie) {
        if (favorited)
            model.addFavorite(item)
        else
            model.deleteFavorite(item)
    }
}