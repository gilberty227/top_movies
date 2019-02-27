package br.com.topmovies.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Genres : RealmObject() {

    @PrimaryKey
    var id: Int = 0
    var name: String? = null
}