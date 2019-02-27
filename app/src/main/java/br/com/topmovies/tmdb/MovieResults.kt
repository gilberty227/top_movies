package br.com.topmovies.tmdb

class MovieResults {

    private var page: Int = 0
    private var total_results: Int = 0
    private var dates: DatesBean? = null
    private var total_pages: Int = 0
    private var results: List<ResultsBean>? = null

    fun getPage(): Int {
        return page
    }

    fun setPage(page: Int) {
        this.page = page
    }

    fun getTotal_results(): Int {
        return total_results
    }

    fun setTotal_results(total_results: Int) {
        this.total_results = total_results
    }

    fun getDates(): DatesBean? {
        return dates
    }

    fun setDates(dates: DatesBean) {
        this.dates = dates
    }

    fun getTotal_pages(): Int {
        return total_pages
    }

    fun setTotal_pages(total_pages: Int) {
        this.total_pages = total_pages
    }

    fun getResults(): List<ResultsBean>? {
        return results
    }

    fun setResults(results: List<ResultsBean>) {
        this.results = results
    }

    class DatesBean {

        var maximum: String? = null
        var minimum: String? = null
    }

    class ResultsBean {

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