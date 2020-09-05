package dev.test.project.presenters

import dev.test.project.App
import dev.test.project.R
import dev.test.project.interfaces.ApiService
import dev.test.project.interfaces.MovieListView
import dev.test.project.items.Movie
import dev.test.project.items.MoviesObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.MvpPresenter
import moxy.presenterScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieListPresenter : MvpPresenter<MovieListView>() {


    private val url = "https://s3-eu-west-1.amazonaws.com/"
    private lateinit var retrofit: Retrofit
    private lateinit var apiService: ApiService
    private lateinit var call: Call<MoviesObject>

    private var moviesObject: MoviesObject? = null
    private var filteredList: List<Movie>? = null

    override fun onFirstViewAttach() {
        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .build()
        apiService = retrofit.create(ApiService::class.java)
        call = apiService.getMovies()
        fetchMovies()
    }

    override fun onDestroy() {
        super.onDestroy()
        call.cancel()
    }

    fun fetchMovies() {
        viewState.showLoading(true)
        if(call.isCanceled or call.isExecuted) {
            call = call.clone()
        }
        call.enqueue(object : Callback<MoviesObject> {
            override fun onFailure(call: Call<MoviesObject>, t: Throwable) {
                moviesObject = null
                viewState.showError()
            }
            override fun onResponse(call: Call<MoviesObject>, response: Response<MoviesObject>) {
                if (response.isSuccessful) {
                    moviesObject = response.body()
                    mapData(response.body())
                } else {
                    moviesObject = null
                    viewState.showError()
                }
            }
        })
    }

    //Подготовка данных для MovieListFragment
    fun mapData(data: MoviesObject?) {
        presenterScope.launch(Dispatchers.IO) {
            moviesObject = data
            if(data != null) {
                data.movies = data.movies.sortedBy { it.titleRU }
                data.genres = getGenres(data.movies)
                val list = mutableListOf<Any>().apply {
                    add(App.getInstance().getString(R.string.genres))
                    addAll(data.genres)
                    add(App.getInstance().getString(R.string.movies))
                    addAll(data.movies)
                }
                withContext(Dispatchers.Main) {
                    if(data.movies.isEmpty())
                        viewState.showEmptyList()
                    else {
                        viewState.initView(list)
                    }
                }
            }
        }
    }

    //Достаем список жанров
    private fun getGenres(list: List<Movie>): List<String> {
        val genres = mutableListOf<String>()
        list.forEach { movie ->
            genres.addAll(movie.genres!!)
        }
        return genres.distinct()
    }

    //Фильтр данных по жанру
    fun filterData(genre: String?) {
        if(genre.isNullOrEmpty()) {
            viewState.showMovies(moviesObject!!.movies)
        } else {
            presenterScope.launch(Dispatchers.IO) {
                val list = mutableListOf<Movie>()
                moviesObject!!.movies.forEach { movie ->
                    if (movie.genres!!.contains(genre)) {
                        list.add(movie)
                    }
                }
                filteredList = list
                withContext(Dispatchers.Main) {
                    viewState.showMovies(list)
                }
            }
        }
    }
}