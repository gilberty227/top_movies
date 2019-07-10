package br.com.topmovies.details

import br.com.topmovies.commons.BasePresenter
import br.com.topmovies.data.models.Genres
import br.com.topmovies.data.models.Movies
import io.realm.RealmResults

interface DetailsContract {

    interface DetailsView {
        fun showDetailsMovies(movie: RealmResults<Movies>, genrers: RealmResults<Genres>)
    }

    interface DetailsPresenter : BasePresenter<DetailsView> {
        fun onStart(idMovie: Int)
        fun onStartLoadingInfo(idMovie: Int)
    }

}