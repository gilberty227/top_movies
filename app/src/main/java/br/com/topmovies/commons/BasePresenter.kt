package br.com.topmovies.commons

interface BasePresenter<V> {

    fun attachView(view: V)
    fun detachView()

}