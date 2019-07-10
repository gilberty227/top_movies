package br.com.topmovies.details

import android.content.Context
import br.com.topmovies.data.Repository

class DetailsPresenter(private var context: Context) : DetailsContract.DetailsPresenter {

    private var viewHome: DetailsContract.DetailsView? = null
    private var repository = Repository()

    override fun onStart(idMovie: Int) {
        onStartLoadingInfo(idMovie)
    }

    override fun onStartLoadingInfo(idMovie: Int) {
        viewHome?.showDetailsMovies(repository.getListSpecificMovies(idMovie), repository.getListAllGenres())
    }

    override fun attachView(view: DetailsContract.DetailsView) {
        viewHome = view
    }

    override fun detachView() {
        viewHome = null
    }

}