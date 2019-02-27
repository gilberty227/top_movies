package br.com.topmovies.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import br.com.topmovies.R
import br.com.topmovies.commons.BaseFragment
import br.com.topmovies.details.DetailsFragment
import br.com.topmovies.home.adapter.MovieAdapter
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.loading.*


class HomeFragment : BaseFragment(), HomeContract.HomeView, RecycleViewItemClickListener {

    private lateinit var adapter: MovieAdapter
    private val numberOfColumns = 2
    private lateinit var presenter: HomePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(br.com.topmovies.R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        context?.let { context ->
            presenter = HomePresenter(context)
        }
    }

    private fun initAdapter(context: Context) {
        adapter = MovieAdapter(context, presenter.getListMovies(), this)
        recyclerViewMovie.adapter = adapter
        recyclerViewMovie.layoutManager = GridLayoutManager(context, numberOfColumns)
        adapter.notifyDataSetChanged()
    }

    override fun clickItem(id: Int) {
        val bundle = Bundle()
        bundle.putInt("id", id)
        switchToFragment(DetailsFragment(), R.id.fragment_container, bundle, true, true)
    }

    override fun showLoadScreen() {
        homeAnimationLoading.playAnimation()
        setAllVisibility()
        includeLoading.visibility = View.VISIBLE
    }

    override fun showNoInternetScreen() {
        homeAnimationLoading.cancelAnimation()
        setAllVisibility()
        includeNoInternet.visibility = View.VISIBLE
    }

    override fun showMoviesScreen() {
        activity?.runOnUiThread {
            homeAnimationLoading.cancelAnimation()
            context?.let { initAdapter(it) }
            setAllVisibility()
            recyclerViewMovie.visibility = View.VISIBLE
        }
    }

    private fun setAllVisibility() {
        includeNoInternet.visibility = View.GONE
        includeLoading.visibility = View.GONE
        recyclerViewMovie.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
        presenter.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView()
    }
}