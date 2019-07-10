package br.com.topmovies.home

import android.content.Context
import br.com.topmovies.data.LoadingFinishListener
import br.com.topmovies.data.Repository
import br.com.topmovies.data.models.Movies
import br.com.topmovies.retrofit.DownloadGenresInfo
import br.com.topmovies.retrofit.DownloadMoviesInfo
import com.blankj.utilcode.util.NetworkUtils
import io.realm.RealmResults

class HomePresenter(private var context: Context) : HomeContract.HomePresenter {

    private var viewHome: HomeContract.HomeView? = null
    private var repository = Repository()

    override fun onStart() {
        if (repository.getListAllMovies().size <= 0) {
            if (!NetworkUtils.isConnected()) {
                viewHome?.showNoInternetScreen()
            } else {
                viewHome?.showLoadScreen()
                onStartLoadingGenre()
            }
        } else {
            viewHome?.showMoviesScreen()
        }
    }

    override fun onStartLoadingMovies() {
        DownloadMoviesInfo().initDownloadMovies(context, repository, object : LoadingFinishListener {
            override fun finish() {
                Thread.sleep(8 * 1000)
                viewHome?.showMoviesScreen()
            }
        })
    }

    override fun onStartLoadingGenre() {
        DownloadGenresInfo().initDownloadGenres(repository, object : LoadingFinishListener {
            override fun finish() {
                onStartLoadingMovies()
            }
        })
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