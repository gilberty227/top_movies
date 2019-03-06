package br.com.topmovies.tmdb

import br.com.topmovies.tmdb.interfaces.InterfaceClassResults

class GenreResults : InterfaceClassResults.Results {
    var genres: List<GenresBean>? = null

    class GenresBean : InterfaceClassResults.ResultBean {
        var id: Int = 0
        var name: String? = null
    }
}
