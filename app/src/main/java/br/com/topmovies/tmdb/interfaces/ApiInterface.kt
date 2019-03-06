package br.com.topmovies.tmdb.interfaces

import br.com.topmovies.tmdb.GenreResults
import br.com.topmovies.tmdb.MovieResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("3/movie/{category}")
    fun getListMovies(
        @Path("category") category: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("region") region: String,
        @Query("page") page: Int
    ): Call<MovieResults>

    @GET("3/genre/movie/{category}")
    fun getListGenres(
        @Path("category") category: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<GenreResults>
}