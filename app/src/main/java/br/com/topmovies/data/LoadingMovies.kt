package br.com.topmovies.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import br.com.topmovies.data.models.Movies
import br.com.topmovies.tmdb.ApiInterface
import br.com.topmovies.tmdb.InfoTMDB.apiKey
import br.com.topmovies.tmdb.InfoTMDB.categoryUpcoming
import br.com.topmovies.tmdb.InfoTMDB.languageBrazil
import br.com.topmovies.tmdb.InfoTMDB.regionBrazil
import br.com.topmovies.tmdb.InfoTMDB.urlBaseApi
import br.com.topmovies.tmdb.InfoTMDB.urlBaseImage
import br.com.topmovies.tmdb.MovieResults
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class LoadingMovies(context: Context, private var repository: Repository, private var listener: LoadingFinishListener) {

    private var result: Retrofit =
        Retrofit.Builder().baseUrl(urlBaseApi).addConverterFactory(GsonConverterFactory.create()).build()
    private var countMovies = 0
    private var countImages = 0
    private var countImagesNull = 0

    init {
        val call: Call<MovieResults> = call()
        call.enqueue(object : Callback<MovieResults> {

            override fun onResponse(call: Call<MovieResults>, response: Response<MovieResults>) {
                response.body()?.getResults()?.let { listMovies ->
                    countMovies = listMovies.size
                    listMovies.forEach { resultsBean ->
                        if (!repository.checkIdExists(Movies::class.java, resultsBean.id)) {
                            val movies = Movies()
                            createMovies(movies, resultsBean)
                            repository.saveData(movies)

                            saveImage(context, movies.poster_path)
                            if (movies.backdrop_path == movies.poster_path) {
                                countImagesNull++
                            } else {
                                saveImage(context, movies.backdrop_path)
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<MovieResults>, t: Throwable) {}
        })
    }

    private fun createMovies(movies: Movies, resultsBean: MovieResults.ResultsBean): Movies {

        movies.id = resultsBean.id
        movies.title = resultsBean.title
        movies.poster_path = resultsBean.poster_path?.substring(1)
        if (resultsBean.backdrop_path == null) {
            movies.backdrop_path = resultsBean.poster_path?.substring(1)
        } else {
            movies.backdrop_path = resultsBean.backdrop_path?.substring(1)
        }
        movies.overview = resultsBean.overview
        movies.release_date = resultsBean.release_date
        movies.language = regionBrazil
        movies.genre_ids.addAll(resultsBean.genre_ids!!)
        return movies
    }

    private fun call(): Call<MovieResults> {
        val apiInterface = result.create(ApiInterface::class.java)
        return apiInterface.getListMovies(categoryUpcoming, apiKey, languageBrazil, regionBrazil, 1)
    }


    fun saveImage(context: Context, fileName: String?) {

        fileName?.let { file ->
            val request = Request.Builder().url(urlBaseImage + file).build()
            OkHttpClient().newCall(request).enqueue(object : okhttp3.Callback {

                override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                    val inputStream = response.body()?.byteStream()
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    val fos = context.openFileOutput(file, Context.MODE_PRIVATE)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)

                    countImages++
                    if (countImages >= ((countMovies * 2) - countImagesNull)) {
                        listener.finish()
                    }
                }

                override fun onFailure(call: okhttp3.Call, e: IOException) {}

            })
        }
    }
}