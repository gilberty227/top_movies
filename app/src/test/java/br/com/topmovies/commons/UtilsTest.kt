package br.com.topmovies.commons

import org.junit.Test

class UtilsTest {

    @Test
    fun convertFormatBrDate() {
        val dateMovieTMDB = "2020-03-01"
        val dateResult = Utils().convertFormatDate(dateMovieTMDB)
        assert(dateResult == "01/03/2020")
    }
}