package dev.test.project.presentation.presenter

import android.os.Parcelable
import dev.test.project.R
import dev.test.project.items.Genre
import dev.test.project.presentation.view.MovieListView
import dev.test.project.items.Movie
import dev.test.project.items.MoviesObject
import dev.test.project.model.MovieListModel
import dev.test.project.utils.ResourceUtils
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Презентер для списка жанров и фильмов
 */
class MovieListPresenter : MvpPresenter<MovieListView>() {

    var moviesObject: MoviesObject? = null

    var scrollPosition: Parcelable? = null
    var checkedGenre: Genre? = null

    private val model = MovieListModel()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        fetchMovies()
    }

    //Отмена соединения
    override fun onDestroy() {
        super.onDestroy()
        model.cancelAll()
    }

    //Запрос данных
    fun fetchMovies() {
        viewState.showLoading(true)
        model.fetchMovies(object : Callback<MoviesObject> {
            override fun onResponse(call: Call<MoviesObject>, response: Response<MoviesObject>) {
                if(response.isSuccessful) {
                    moviesObject = response.body()
                    mapData()
                } else {
                    viewState.showError(ResourceUtils.getString(R.string.bad_code))
                }
            }

            override fun onFailure(call: Call<MoviesObject>, t: Throwable) {
                moviesObject = null
                viewState.showError(ResourceUtils.getString(R.string.error_internet))
            }
        })
    }

    //Подготовка данных
    fun mapData() {
        presenterScope.launch {
            moviesObject?.let {
                val data = model.mapData(it)
                viewState.initView(data)
            }
        }
    }

    //Фильтр данных по жанру
    fun filterData(item: Genre?) {
        presenterScope.launch {
            val filteredList = if (item != null) {
                model.filterData(item.genre, moviesObject!!.movies)
            } else {
                moviesObject!!.movies
            }
            viewState.showMovies(filteredList)
        }
    }

    //Добавление или удаление из избранного
    fun changeFavorite(favorited: Boolean, item: Movie) {
        item.favorited = favorited
        if (favorited)
            model.addFavorite(item)
        else
            model.deleteFavorite(item.id)
    }
}