package br.com.topmovies.data

import br.com.topmovies.data.models.Genres
import br.com.topmovies.tmdb.ApiInterface
import br.com.topmovies.tmdb.GenreResults
import br.com.topmovies.tmdb.InfoTMDB.apiKey
import br.com.topmovies.tmdb.InfoTMDB.categoryGenre
import br.com.topmovies.tmdb.InfoTMDB.languageBrazil
import br.com.topmovies.tmdb.InfoTMDB.urlBaseApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoadingGenres(private var repository: Repository) {

    private var result: Retrofit =
        Retrofit.Builder().baseUrl(urlBaseApi).addConverterFactory(GsonConverterFactory.create()).build()

    init {
        val call: Call<GenreResults> = call()
        call.enqueue(object : Callback<GenreResults> {

            override fun onResponse(call: Call<GenreResults>, response: Response<GenreResults>) {
                response.body()?.genres?.forEach { resultsBean ->
                    if (!repository.checkIdExists(Genres::class.java, resultsBean.id)) {
                        val genres = Genres()
                        createGenres(genres, resultsBean)
                        repository.saveData(genres)
                    }
                }
            }

            override fun onFailure(call: Call<GenreResults>, t: Throwable) {}
        })
    }

    private fun createGenres(genres: Genres, resultsBean: GenreResults.GenresBean): Genres {
        genres.id = resultsBean.id
        genres.name = resultsBean.name
        return genres
    }

    private fun call(): Call<GenreResults> {
        val apiInterface = result.create(ApiInterface::class.java)
        return apiInterface.getListGenres(categoryGenre, apiKey, languageBrazil)
    }
}