package br.com.topmovies.data.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Movies : RealmObject() {

    @PrimaryKey
    var id: Int = 0
    var language: String? = null
    var title: String? = null
    var poster_path: String? = null
    var backdrop_path: String? = null
    var overview: String? = null
    var release_date: String? = null
    var genre_ids: RealmList<Int> = RealmList()
}