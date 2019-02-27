package br.com.topmovies.data

import br.com.topmovies.data.models.Genres
import br.com.topmovies.data.models.Movies
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.RealmResults

class Repository() {

    private var realm: Realm = getRealm()

    fun getListAllMovies(): RealmResults<Movies> {
        return realm.where(Movies::class.java).findAll()
    }

    fun getListAllGenres(): RealmResults<Genres> {
        return realm.where(Genres::class.java).findAll()
    }

    fun getListSpecificMovies(int: Int): RealmResults<Movies> {
        return realm.where(Movies::class.java).equalTo("id", int).findAll()
    }

    private fun getRealm(): Realm {
        return Realm.getDefaultInstance()
    }

    fun <E : RealmModel> checkIdExists(clazz: Class<E>, id: Int): Boolean {
        return realm.where(clazz).equalTo("id", id).findAll().size > 0
    }

    fun saveData(model: RealmObject) {
        realm.beginTransaction()
        realm.copyToRealm(model)
        realm.commitTransaction()
    }


}