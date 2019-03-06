package br.com.topmovies.retrofit

import br.com.topmovies.tmdb.interfaces.InterfaceClassResults
import io.realm.RealmObject
import retrofit2.Call
import retrofit2.Retrofit

interface DownloadInterface<T : RealmObject, X : InterfaceClassResults.Results, U : InterfaceClassResults.ResultBean> {

    fun builderRetrofit(): Retrofit
    fun startCall(): Call<X>
    fun createObject(resultBean: U): T
}