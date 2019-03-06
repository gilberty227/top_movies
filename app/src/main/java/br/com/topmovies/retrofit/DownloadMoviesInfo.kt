package br.com.topmovies.retrofit

import android.content.Context
import br.com.topmovies.data.LoadingFinishListener
import br.com.topmovies.data.Repository
import br.com.topmovies.data.models.Movies
import br.com.topmovies.tmdb.InfoTMDB.apiKey
import br.com.topmovies.tmdb.InfoTMDB.categoryUpcoming
import br.com.topmovies.tmdb.InfoTMDB.languageBrazil
import br.com.topmovies.tmdb.InfoTMDB.regionBrazil
import br.com.topmovies.tmdb.InfoTMDB.urlBaseApi
import br.com.topmovies.tmdb.MovieResults
import br.com.topmovies.tmdb.interfaces.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DownloadMoviesInfo : DownloadInterface<Movies, MovieResults, MovieResults.ResultsBean> {

    fun initDownloadMovies(context: Context, repository: Repository, listener: LoadingFinishListener?) {
        startCall().enqueue(object : Callback<MovieResults> {
            override fun onResponse(call: Call<MovieResults>, response: Response<MovieResults>) {
                val resultMovies = response.body()?.results
                resultMovies?.forEach {
                    if (it == response.body()?.results?.last()) {
                        loadingMovie(context, it, repository, listener)
                    } else {
                        loadingMovie(context, it, repository, null)
                    }
                }
            }

            override fun onFailure(call: Call<MovieResults>, t: Throwable) {}
        })
    }

    override fun startCall(): Call<MovieResults> {
        val apiInterface = builderRetrofit().create(ApiInterface::class.java)
        return apiInterface.getListMovies(categoryUpcoming, apiKey, languageBrazil, regionBrazil, 1)
    }

    override fun builderRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(urlBaseApi).addConverterFactory(GsonConverterFactory.create()).build()
    }

    override fun createObject(resultBean: MovieResults.ResultsBean): Movies {
        val movies = Movies()
        movies.id = resultBean.id
        movies.title = resultBean.title
        movies.poster_path = resultBean.poster_path?.substring(1)
        if (resultBean.backdrop_path == null) {
            movies.backdrop_path = resultBean.poster_path?.substring(1)
        } else {
            movies.backdrop_path = resultBean.backdrop_path?.substring(1)
        }
        movies.overview = resultBean.overview
        movies.release_date = resultBean.release_date
        movies.language = regionBrazil
        movies.genre_ids.addAll(resultBean.genre_ids!!)
        return movies
    }

    fun loadingMovie(
        context: Context,
        resultBean: MovieResults.ResultsBean,
        repository: Repository,
        listener: LoadingFinishListener?
    ) {

        if (!repository.checkIdExists(Movies::class.java, resultBean.id)) {
            val movies = createObject(resultBean)
            repository.saveData(movies)

            if (movies.backdrop_path != movies.poster_path && movies.backdrop_path != null) {
                callDownloadImage(context, movies.backdrop_path!!, null)
            }

            movies.poster_path?.let {
                callDownloadImage(context, it, listener)
            }
        }
    }

    private fun callDownloadImage(
        context: Context,
        fileImageName: String,
        listener: LoadingFinishListener?
    ) {
        DownloadImagesFile().downloadImage(context, fileImageName, listener)
    }
}