package dev.test.project.model.prod

import dev.test.project.data.DatabaseManager
import dev.test.project.data.RetrofitManager
import dev.test.project.interfaces.RetrofitCallback
import dev.test.project.items.Genre
import dev.test.project.items.Movie
import dev.test.project.items.MoviesObject
import dev.test.project.model.MovieListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Model для списка фильмов и жанров
 */
class MovieListModelProd : MovieListModel {

    private val database: DatabaseManager = DatabaseManager()
    private val retrofitManager: RetrofitManager = RetrofitManager()

    override fun cancelAll() {
        retrofitManager.cancel()
    }

    override fun fetchMovies(callback: RetrofitCallback<MoviesObject>) {
        retrofitManager.fetchMovies(callback)
    }

    override fun addFavorite(item: Movie) {
        database.addItem(item)
    }

    override fun deleteFavorite(id: Int) {
        database.deleteItem(id)
    }

    override fun getGenres(list: List<Movie>): List<Genre> {
        var genres = mutableListOf<String>()
        list.forEach { movie ->
            genres.addAll(movie.genres!!)
        }
        genres = genres.distinct().toMutableList()
        return mutableListOf<Genre>().apply {
            genres.forEach {
                add(Genre(it, it.hashCode()))
            }
        }
    }

    override suspend fun getFavoritedMovies(list: List<Movie>): List<Movie>
            = withContext(Dispatchers.IO) {
        list.forEach { movie ->
            withContext(Dispatchers.Main) {
                movie.favorited = database.containsItem(movie.id)
            }
        }
        return@withContext list
    }

    override suspend fun filterData(genre: String, movies: List<Movie>): List<Movie>
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