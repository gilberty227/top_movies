package br.com.topmovies.home

import br.com.topmovies.commons.BasePresenter
import br.com.topmovies.data.models.Movies
import io.realm.RealmResults

interface HomeContract {

    interface HomeView {
        fun showLoadScreen()
        fun showNoInternetScreen()
        fun showMoviesScreen()
    }

    interface HomePresenter : BasePresenter<HomeView> {
        fun onStart()
        fun onStartLoadingGenre()
        fun onStartLoadingMovies()
        fun getListMovies(): RealmResults<Movies>
    }

}