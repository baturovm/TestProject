package dev.test.project.data

import dev.test.project.items.Movie
import io.realm.Realm
import io.realm.kotlin.deleteFromRealm

/**
 * Работа с базой данных
 * - Добавление в БД
 * - Удаление из БД
 * - Получение списка из БД
 */
class DatabaseHelper {

    private val realm: Realm = Realm.getDefaultInstance()

    //Закрытие БД
    fun close() {
        realm.close()
    }

    /**
     * Запись в БД
     * @param item фильм
     */
    fun addItem(item: Movie) {
        item.favorited = true
        realm.executeTransaction { realm ->
            realm.insert(item)
        }
    }

    /**
     * Удаление из БД
     * @param id идентификатор фильма
     */
    fun deleteItem(id: Int) {
        realm.executeTransaction {
            val item = realm.where(Movie::class.java)
                .equalTo("id", id)
                .findFirst()
            item?.deleteFromRealm()
        }
    }

    /**
     * Получение списка из БД
     * @return отсортированный список избранных фильмов
     */
    fun getItems(): List<Movie> {
        val list = realm.where(Movie::class.java).findAll()
        return realm.copyFromRealm(list).sortedBy { it.titleRU }
    }

    /**
     * Проверка на существование в БД
     * @param id идентификатор фильма
     * @return существует(true), не существует(false)
     */
    fun containsItem(id: Int): Boolean {
        val item = realm.where(Movie::class.java)
            .equalTo("id", id)
            .findFirst()
        return item!=null
    }
}