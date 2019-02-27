package br.com.topmovies.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.topmovies.R
import br.com.topmovies.data.models.Movies
import br.com.topmovies.home.RecycleViewItemClickListener
import io.realm.RealmResults

class MovieAdapter(
    private var context: Context,
    private var listMovies: RealmResults<Movies>,
    private var listener: RecycleViewItemClickListener
) : RecyclerView.Adapter<MoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.movie_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listMovies.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        listMovies[position]?.let {
            holder.setData(it, listener)
        }
    }

}