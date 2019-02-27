package br.com.topmovies.tmdb

class GenreResults {
    var genres: List<GenresBean>? = null

    class GenresBean {
        var id: Int = 0
        var name: String? = null
    }
}
