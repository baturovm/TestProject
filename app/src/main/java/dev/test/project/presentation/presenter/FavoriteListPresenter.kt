package dev.test.project.presentation.presenter

import dev.test.project.items.Movie
import dev.test.project.model.FavoriteListModel
import dev.test.project.presentation.view.FavoriteListView
import moxy.MvpPresenter

/**
 * Презентер для списка избранного
 */
class FavoriteListPresenter: MvpPresenter<FavoriteListView>() {

    private val model = FavoriteListModel()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getFavoriteList()
    }

    //Получения списка избранных
    private fun getFavoriteList() {
        val list = model.getFavoriteList()
        when {
            list.isEmpty() -> viewState.showEmptyList(true)
            else -> viewState.showMovies(list)
        }
    }

    //Добавление или удаление из избранного
    fun changeFavorite(favorited: Boolean, item: Movie) {
        item.favorited = favorited
        if (favorited)
            model.addFavorite(item)
        else
            model.deleteFavorite(item)
    }
}