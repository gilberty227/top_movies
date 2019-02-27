package br.com.topmovies.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.topmovies.R
import br.com.topmovies.commons.BaseFragment
import br.com.topmovies.commons.Utils
import br.com.topmovies.data.models.Genres
import br.com.topmovies.data.models.Movies
import com.squareup.picasso.Picasso
import io.realm.RealmResults
import kotlinx.android.synthetic.main.details_fragment.*
import java.io.File


class DetailsFragment : BaseFragment(), DetailsContract.DetailsView {

    private lateinit var presenter: DetailsPresenter
    private var idMovie = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(br.com.topmovies.R.layout.details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            idMovie = bundle.getInt("id", 0)
        }

        init()
    }

    private fun init() {
        context?.let { context ->
            presenter = DetailsPresenter(context)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
        presenter.onStart(idMovie)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView()
    }

    override fun showDetailsMovies(movie: RealmResults<Movies>, genrers: RealmResults<Genres>) {

        var genresText = ""
        movie.first()?.genre_ids?.forEach { idGenre ->
            genrers.forEach { infoGenre ->
                if (infoGenre.id == idGenre) {
                    genresText = genresText + infoGenre.name + "/"
                }
            }
        }

        if (genresText.isEmpty()) {
            textViewDetailsGenders.text = ""
        } else {
            textViewDetailsGenders.text = getString(R.string.genre, genresText.substring(0, genresText.length - 1))
        }

        textViewDetailsTitle.text = movie.first()?.title
        textViewDetailsReleaseDate.text =
            getString(R.string.releaseDate, Utils().convertFormatDate(movie.first()?.release_date))
        textViewDetailsOverview.text = movie.first()?.overview
        movie.first()?.backdrop_path?.let { fileName ->
            val file = File(context?.filesDir, fileName)
            if (file.exists()) {
                Picasso.get()
                    .load(file)
                    .into(imageViewDetailsMovies)
            }
        }
    }


}