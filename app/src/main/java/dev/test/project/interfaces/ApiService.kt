package dev.test.project.interfaces

import dev.test.project.items.MoviesObject
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("/sequeniatesttask/films.json")
    fun getMovies(): Call<MoviesObject>
}