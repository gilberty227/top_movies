package br.com.topmovies.commons

import android.app.Application
import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration

class TopMoviesApplication : Application() {

    private var dataBaseName = "topMovies.realm"
    var schemaVersion = 1L

    override fun onCreate() {
        super.onCreate()

        initRealm(applicationContext, dataBaseName, schemaVersion)
    }

    private fun initRealm(applicationContext: Context, dataBase: String, schemaVersion: Long) {
        Realm.init(applicationContext)
        val realmConfiguration = RealmConfiguration.Builder()
            .name(dataBase)
            .schemaVersion(schemaVersion)
            .deleteRealmIfMigrationNeeded()
            .build()

        Realm.setDefaultConfiguration(realmConfiguration)
    }
}