package dev.test.project.utils

import androidx.annotation.StringRes
import com.google.gson.JsonSyntaxException
import dev.test.project.App
import dev.test.project.R
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ResourceUtils {

    fun getString(@StringRes resId: Int): String {
        return App.getContext().getString(resId)
    }

    /**
     * Получение сообщения ошибки
     * @return сообщение ошибки
     */
    fun getErrorString(t: Throwable): String {
        return when (t) {
            is UnknownHostException, is ConnectException -> {
                getString(R.string.error_internet)
            }
            is SocketTimeoutException -> {
                getString(R.string.error_slow_connection)
            }
            is JsonSyntaxException -> {
                getString(R.string.error_data_processing)
            }
            else -> {
                getString(R.string.error_unknown)
            }
        }
    }
}