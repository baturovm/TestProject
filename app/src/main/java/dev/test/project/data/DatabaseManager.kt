package dev.test.project.data

import dev.test.project.items.Movie
import io.realm.Realm
import io.realm.kotlin.deleteFromRealm

/**
 * Работа с базой данных
 */
class DatabaseManager {

    /**
     * Запись в БД
     * @param item фильм
     */
    fun addItem(item: Movie) {
        val realm = Realm.getDefaultInstance()
        item.favorited = true
        realm.executeTransaction {
            realm.insert(item)
        }
        realm.close()
    }

    /**
     * Удаление из БД
     * @param id идентификатор фильма
     */
    fun deleteItem(id: Int) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            val item = realm.where(Movie::class.java)
                .equalTo("id", id)
                .findFirst()
            item?.deleteFromRealm()
        }
        realm.close()
    }

    /**
     * Получение списка из БД
     * @return отсортированный список избранных фильмов
     */
    fun getItems(): List<Movie> {
        val realm = Realm.getDefaultInstance()
        val realmList = realm.where(Movie::class.java).findAll()
        val items = realm.copyFromRealm(realmList).sortedBy { it.titleRU }
        realm.close()
        return items
    }

    /**
     * Проверка на существование в БД
     * @param id идентификатор фильма
     * @return существует(true), не существует(false)
     */
    fun containsItem(id: Int): Boolean {
        val realm = Realm.getDefaultInstance()
        val item = realm.where(Movie::class.java)
            .equalTo("id", id)
            .findFirst()
        realm.close()
        return item!=null
    }
}