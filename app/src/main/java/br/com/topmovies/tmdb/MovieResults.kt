package br.com.topmovies.tmdb

import br.com.topmovies.tmdb.interfaces.InterfaceClassResults

class MovieResults : InterfaceClassResults.Results {

    var page: Int = 0
    var total_results: Int = 0
    var dates: DatesBean? = null
    var total_pages: Int = 0
    var results: List<ResultsBean>? = null

    class DatesBean {

        var maximum: String? = null
        var minimum: String? = null
    }

    class ResultsBean : InterfaceClassResults.ResultBean {

        var vote_count: Int = 0
        var id: Int = 0
        var isVideo: Boolean = false
        var vote_average: Double = 0.0
        var title: String? = null
        var popularity: Double = 0.toDouble()
        var poster_path: String? = null
        var original_language: String? = null
        var original_title: String? = null
        var backdrop_path: String? = null
        var isAdult: Boolean = false
        var overview: String? = null
        var release_date: String? = null
        var genre_ids: List<Int>? = null
    }

}