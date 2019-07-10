package br.com.topmovies.data

import br.com.topmovies.data.models.Genres
import br.com.topmovies.data.models.Movies
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.RealmResults

open class Repository {

    private fun getRealm(): Realm {
        return Realm.getDefaultInstance()
    }

    fun getListAllMovies(): RealmResults<Movies> {
        return getRealm().where(Movies::class.java).findAll()
    }

    fun getListAllGenres(): RealmResults<Genres> {
        return getRealm().where(Genres::class.java).findAll()
    }

    fun getListSpecificMovies(int: Int): RealmResults<Movies> {
        return getRealm().where(Movies::class.java).equalTo("id", int).findAll()
    }

    fun <E : RealmModel> checkIdExists(clazz: Class<E>, id: Int): Boolean {
        return getRealm().where(clazz).equalTo("id", id).findAll().size > 0
    }

    fun saveData(model: RealmObject) {
        getRealm().beginTransaction()
        getRealm().copyToRealm(model)
        getRealm().commitTransaction()
    }


}