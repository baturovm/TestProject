package dev.test.project.interfaces

interface RetrofitCallback<T> {

    /**
     * Произошла ошибка
     * @param error сообщение об ошибке
     */
    fun onError(error: String)

    /**
     * Успешный ответ
     * @param data полученные данные
     */
    fun onSuccess(data: T)
}