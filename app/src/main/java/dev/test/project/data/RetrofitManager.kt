package dev.test.project.data

import com.google.gson.JsonSyntaxException
import dev.test.project.R
import dev.test.project.interfaces.ApiService
import dev.test.project.interfaces.RetrofitCallback
import dev.test.project.items.MoviesObject
import dev.test.project.utils.ResourceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Получение данных из сети и контроль соединения
 */
class RetrofitManager {

    private val url = "https://s3-eu-west-1.amazonaws.com/"
    private var retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(url)
        .build()
    private var apiService = retrofit.create(ApiService::class.java)
    private var call = apiService.getMovies()

    /**
     * Отмена соединения
     */
    fun cancel() {
        call.cancel()
    }

    /**
     * Получение списка фильмов
     */
    fun fetchMovies(callback: RetrofitCallback<MoviesObject>) {
        if(call.isCanceled or call.isExecuted) {
            call = call.clone()
        }
        call.enqueue(object : Callback<MoviesObject> {
            override fun onResponse(call: Call<MoviesObject>, response: Response<MoviesObject>) {
                if(response.isSuccessful) {
                    callback.onSuccess(response.body()!!)
                } else {
                    callback.onError(ResourceUtils.getString(R.string.server_error))
                }
            }

            override fun onFailure(call: Call<MoviesObject>, t: Throwable) {
                val error = when (t) {
                    is UnknownHostException, is ConnectException -> {
                        ResourceUtils.getString(R.string.error_internet)
                    }
                    is SocketTimeoutException -> {
                        ResourceUtils.getString(R.string.error_slow_connection)
                    }
                    is JsonSyntaxException -> {
                        ResourceUtils.getString(R.string.error_data_processing);
                    }
                    else -> {
                        ResourceUtils.getString(R.string.error_unknown)
                    }
                }
                callback.onError(error)
            }
        })
    }
}