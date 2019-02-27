package br.com.topmovies.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.topmovies.R
import br.com.topmovies.commons.Utils
import br.com.topmovies.data.models.Movies
import br.com.topmovies.home.RecycleViewItemClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_item.view.*
import org.jetbrains.anko.sdk27.listeners.onClick
import java.io.File


open class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    /*Picasso.get().load(urlBaseImage + fileName)
                .placeholder(R.color.md_red_400)
                .into(itemView.imageViewMoviePoster)*/

    private val context = itemView.context

    fun setData(movie: Movies, listener: RecycleViewItemClickListener) {

        itemView.textViewMovieTitle.text = movie.title
        itemView.textViewMovieReleaseDate.text =
            context.getString(R.string.releaseDateSimple, Utils().convertFormatDate(movie.release_date))
        movie.poster_path?.let { fileName ->
            val file = File(context.filesDir, fileName)
            if (file.exists()) {
                Picasso.get()
                    .load(file)
                    .into(itemView.imageViewMoviePoster)
            }
        }

        itemView.onClick {
            listener.clickItem(movie.id)
        }
    }


}