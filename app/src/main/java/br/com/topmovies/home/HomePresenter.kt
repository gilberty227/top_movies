package br.com.topmovies.home

import android.content.Context
import br.com.topmovies.data.LoadingFinishListener
import br.com.topmovies.data.LoadingGenres
import br.com.topmovies.data.LoadingMovies
import br.com.topmovies.data.Repository
import br.com.topmovies.data.models.Movies
import com.blankj.utilcode.util.NetworkUtils
import io.realm.RealmResults
import org.jetbrains.anko.doAsync

class HomePresenter(private var context: Context) : HomeContract.HomePresenter {

    private var viewHome: HomeContract.HomeView? = null
    private var repository = Repository()

    override fun onStart() {
        if (repository.getListAllMovies().size <= 0) {
            if (!NetworkUtils.isConnected()) {
                viewHome?.showNoInternetScreen()
            } else {
                viewHome?.showLoadScreen()
                doAsync {
                    onStartLoadingGenre()
                    onStartLoadingMovies()
                }
            }
        } else {
            viewHome?.showMoviesScreen()
        }
    }

    override fun onStartLoadingMovies() {
        LoadingMovies(context, repository, object : LoadingFinishListener {
            override fun finish() {
                viewHome?.showMoviesScreen()
            }
        })
    }

    override fun onStartLoadingGenre() {
        LoadingGenres(repository)
    }

    override fun getListMovies(): RealmResults<Movies> {
        return repository.getListAllMovies()
    }

    override fun attachView(view: HomeContract.HomeView) {
        viewHome = view
    }

    override fun detachView() {
        viewHome = null
    }

}