package dev.test.project.utils

import androidx.annotation.StringRes
import dev.test.project.App
import dev.test.project.R

object ResourceUtils {
    fun getString(@StringRes resId: Int): String {
        return App.getContext().getString(resId)
    }

    /**
     * Получения сообщения ошибки
     * @param code ответ сервера
     * @return сообщение ошибки
     */
    fun getErrorString(code: Int): String {
        return when (code) {
            in 400..499 -> {
                if(code==400) getString(R.string.bad_request)
                else getString(R.string.client_error)
            }
            in 500..599 -> {
                getString(R.string.server_error)
            }
            else -> getString(R.string.unknown_error)
        }
    }
}