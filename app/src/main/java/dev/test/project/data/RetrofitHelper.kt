package dev.test.project.data

import dev.test.project.interfaces.ApiService
import dev.test.project.items.MoviesObject
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Получение данных из сети и контроль соединения
 */
class RetrofitHelper {

    private val url = "https://s3-eu-west-1.amazonaws.com/"
    private var retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(url)
        .build()
    private var apiService = retrofit.create(ApiService::class.java)
    private var call = apiService.getMovies()

    //Отмена соединения
    fun cancel() {
        call.cancel()
    }

    //Получение списка фильмов
    fun fetchMovies(callback: Callback<MoviesObject>) {
        if(call.isCanceled or call.isExecuted) {
            call = call.clone()
        }
        call.enqueue(callback)
    }
}