package dev.test.project.presentation.presenter

import dev.test.project.items.Movie
import dev.test.project.model.FavoriteListModel
import dev.test.project.presentation.view.FavoriteListView
import moxy.MvpPresenter

/**
 * Презентер для списка избранного
 */
class FavoriteListPresenter(private val model: FavoriteListModel) : MvpPresenter<FavoriteListView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getFavoriteList()
    }

    /**
     * Получения списка избранных
     */
    private fun getFavoriteList() {
        val list = model.getFavoriteList()
        when {
            list.isEmpty() -> viewState.showEmptyList()
            else -> viewState.showMovies(list)
        }
    }

    /**
     * Изменения статуса избранного фильма
     */
    fun changeFavorite(item: Movie) {
        if (item.favorited)
            model.addFavorite(item)
        else
            model.deleteFavorite(item)
    }
}