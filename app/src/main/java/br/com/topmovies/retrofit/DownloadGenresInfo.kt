package br.com.topmovies.retrofit

import br.com.topmovies.data.LoadingFinishListener
import br.com.topmovies.data.Repository
import br.com.topmovies.data.models.Genres
import br.com.topmovies.tmdb.GenreResults
import br.com.topmovies.tmdb.InfoTMDB.apiKey
import br.com.topmovies.tmdb.InfoTMDB.categoryGenre
import br.com.topmovies.tmdb.InfoTMDB.languageBrazil
import br.com.topmovies.tmdb.InfoTMDB.urlBaseApi
import br.com.topmovies.tmdb.interfaces.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DownloadGenresInfo : DownloadInterface<Genres, GenreResults, GenreResults.GenresBean> {

    fun initDownloadGenres(repository: Repository, listener: LoadingFinishListener) {
        startCall().enqueue(object : Callback<GenreResults> {
            override fun onResponse(call: Call<GenreResults>, response: Response<GenreResults>) {
                response.body()?.genres?.forEach { resultsBean ->
                    if (!repository.checkIdExists(Genres::class.java, resultsBean.id)) {

                        val genres = createObject(resultsBean)
                        repository.saveData(genres)

                        if (response.body()?.genres?.last() == resultsBean) {
                            listener.finish()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<GenreResults>, t: Throwable) {}
        })
    }

    override fun createObject(resultBean: GenreResults.GenresBean): Genres {
        val genres = Genres()
        genres.id = resultBean.id
        genres.name = resultBean.name
        return genres
    }

    override fun startCall(): Call<GenreResults> {
        val apiInterface = builderRetrofit().create(ApiInterface::class.java)
        return apiInterface.getListGenres(categoryGenre, apiKey, languageBrazil)
    }

    override fun builderRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(urlBaseApi).addConverterFactory(GsonConverterFactory.create()).build()
    }
}