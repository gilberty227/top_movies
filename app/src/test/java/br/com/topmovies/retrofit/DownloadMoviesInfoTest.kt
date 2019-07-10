package br.com.topmovies.retrofit

import android.content.Context
import br.com.topmovies.data.Repository
import br.com.topmovies.tmdb.MovieResults
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class DownloadMoviesInfoTest {

    @Mock
    internal var context: Context? = null

    @Mock
    internal var repository: Repository? = null

    @Mock
    internal var movieResultBean: MovieResults.ResultsBean? = null

    @Spy
    internal var downloadMoviesInfo = DownloadMoviesInfo()

    private fun builderResponseMoviesResults(): Response<MovieResults> {

        val movieBeanOne = MovieResults.ResultsBean()
        movieBeanOne.id = 1
        movieBeanOne.title = "Exterminador do Futuro"

        val movieBeanTwo = MovieResults.ResultsBean()
        movieBeanTwo.id = 1
        movieBeanTwo.title = "As Panteras"

        val listBeans = ArrayList<MovieResults.ResultsBean>()
        listBeans.add(movieBeanOne)
        listBeans.add(movieBeanTwo)

        val resultMovie = MovieResults()
        resultMovie.page = 1
        resultMovie.results = listBeans

        return Response.success(resultMovie)
    }

    @Test
    fun loadingInfoMoviesServer() {
        whenever(downloadMoviesInfo.loadingMovie(context!!, movieResultBean!!, repository!!, null)).then { }
        /*doAnswer(object : Answer<Response<MovieResults>>, (InvocationOnMock) -> Response<MovieResults> {
            override fun invoke(p1: InvocationOnMock): Response<MovieResults> {
                return builderResponseMoviesResults()
            }

            override fun answer(invocation: InvocationOnMock?): Response<MovieResults> {
                return builderResponseMoviesResults()
            }

        }).whenever(downloadMoviesInfo).initDownloadMovies(context!!, repository!!, null)*/

        downloadMoviesInfo.initDownloadMovies(context!!, repository!!, null)
        verify(downloadMoviesInfo).loadingMovie(context!!, movieResultBean!!, repository!!, null)
        Assert.assertTrue(true)
    }

}
