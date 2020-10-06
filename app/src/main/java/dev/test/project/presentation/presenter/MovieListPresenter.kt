package dev.test.project.presentation.presenter

import dev.test.project.interfaces.RetrofitCallback
import dev.test.project.items.Genre
import dev.test.project.presentation.view.MovieListView
import dev.test.project.items.Movie
import dev.test.project.items.MoviesObject
import dev.test.project.model.MovieListModel
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope

/**
 * Презентер для списка жанров и фильмов
 */
class MovieListPresenter : MvpPresenter<MovieListView>() {

    var moviesObject: MoviesObject? = null

    var checkedGenre: Genre? = null

    private val model = MovieListModel()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        fetchMovies()
    }

    /**
     * Отмена соединения
     */
    override fun onDestroy() {
        super.onDestroy()
        model.cancelAll()
    }

    /**
     * Запрос данных
     */
    fun fetchMovies() {
        viewState.showLoading()
        model.fetchMovies(object : RetrofitCallback<MoviesObject> {
            override fun onError(error: String) {
                viewState.showError(error)
            }

            override fun onSuccess(data: MoviesObject) {
                moviesObject = data
                mapData()
            }
        })
    }

    /**
     * Фильтр данных по жанру
     */
    fun filterData(item: Genre) {
        if(checkedGenre == item) {
            checkedGenre = null
            viewState.setMoviesList(moviesObject!!.movies)
        } else {
            checkedGenre = item
            presenterScope.launch {
                val filteredList = model.filterData(item.genre, moviesObject!!.movies)
                viewState.setMoviesList(filteredList)
            }
        }
    }

    /**
     * Изменения статуса избранного фильма
     */
    fun changeFavorite(item: Movie) {
        if (item.favorited)
            model.addFavorite(item)
        else
            model.deleteFavorite(item.id)
    }

    private fun mapData() {
        presenterScope.launch {
            moviesObject?.let { data ->
                data.movies = data.movies.sortedBy { it.titleRU }
                data.movies = model.getFavoritedMovies(data.movies)
                data.genres = model.getGenres(data.movies)
                viewState.showList(data)
            }
        }
    }
}