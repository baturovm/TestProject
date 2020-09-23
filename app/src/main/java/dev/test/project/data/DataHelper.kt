package dev.test.project.data

import dev.test.project.R
import dev.test.project.items.Genre
import dev.test.project.items.Movie
import dev.test.project.items.MoviesObject
import dev.test.project.interfaces.ApiService
import dev.test.project.utils.ResourceUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/*
Работа с сетью и обработка данных
*/
class DataHelper {

    private val url = "https://s3-eu-west-1.amazonaws.com/"
    private var retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(url)
        .build()
    private var apiService = retrofit.create(ApiService::class.java)
    private var call = apiService.getMovies()

    //Отмена соединения
    fun close() {
        call.cancel()
    }

    //Запрос данных
    fun fetchMovies(callback: Callback<MoviesObject>) {
        if(call.isCanceled or call.isExecuted) {
            call = call.clone()
        }
        call.enqueue(callback)
    }

    //Подготовка данных
    suspend fun mapData(data: MoviesObject, database: DatabaseHelper): MutableList<Any>
            = withContext(Dispatchers.IO) {
        data.movies = data.movies.sortedBy { it.titleRU }
        data.movies.forEach { movie ->
            withContext(Dispatchers.Main) {
                movie.favorited = database.containsItem(movie.id)
            }
        }
        data.genres = getGenres(data.movies)
        return@withContext mutableListOf<Any>().apply {
            add(ResourceUtils.getString(R.string.genres))
            addAll(data.genres)
            add(ResourceUtils.getString(R.string.movies))
            addAll(data.movies)
        }
    }

    //Достаем список жанров
    private suspend fun getGenres(list: List<Movie>): List<Genre>
            = withContext(Dispatchers.IO) {
        var genres = mutableListOf<String>()
        list.forEach { movie ->
            genres.addAll(movie.genres!!)
        }
        genres = genres.distinct().toMutableList()
        return@withContext mutableListOf<Genre>().apply {
            genres.forEach {
                add(Genre(it, it.hashCode()))
            }
        }
    }

    //Фильтр данных по жанру
    suspend fun filterData(genre: String, movies: List<Movie>): List<Movie>
            = withContext(Dispatchers.IO){
        val filteredList = mutableListOf<Movie>()
        movies.forEach { movie ->
            if (movie.genres!!.contains(genre)) {
                filteredList.add(movie)
            }
        }
        return@withContext filteredList
    }
}