package dev.test.project.data

import dev.test.project.items.Movie
import io.realm.Realm
import io.realm.kotlin.deleteFromRealm

/*
Работа с БД
*/
class DatabaseHelper {

    private val realm: Realm = Realm.getDefaultInstance()

    //Закрытие БД
    fun close() {
        realm.close()
    }

    //Запись в БД
    fun addItem(item: Movie) {
        item.favorited = true
        realm.executeTransaction { realm ->
            realm.insert(item)
        }
    }

    //Удаление из БД по id
    fun deleteItem(id: Int) {
        realm.executeTransaction {
            val item = realm.where(Movie::class.java)
                .equalTo("id", id)
                .findFirst()
            item?.deleteFromRealm()
        }
    }

    //Получение списка из БД
    fun getItems(): List<Movie> {
        val list = realm.where(Movie::class.java).findAll()
        return realm.copyFromRealm(list).sortedBy { it.titleRU }
    }

    //Проверка на существование в БД
    fun containsItem(id: Int): Boolean {
        val item = realm.where(Movie::class.java)
            .equalTo("id", id)
            .findFirst()
        return item!=null
    }
}