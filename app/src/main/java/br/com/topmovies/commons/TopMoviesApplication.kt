package br.com.topmovies.commons

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class TopMoviesApplication : Application() {

    private var fileName = "topMovies"
    var schemaVersion = 1L

    override fun onCreate() {
        super.onCreate()
        Realm.init(applicationContext)
        val realmConfiguration = RealmConfiguration.Builder()
            .name(fileName)
            .schemaVersion(schemaVersion)
            .deleteRealmIfMigrationNeeded()
            .build()

        Realm.setDefaultConfiguration(realmConfiguration)
    }

}