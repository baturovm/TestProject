package dev.test.project.fragments

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis
import dev.test.project.R
import dev.test.project.utils.setVisibility
import dev.test.project.adapters.MovieListAdapter
import dev.test.project.adapters.MovieListAdapter.Companion.GENRES_TYPE
import dev.test.project.adapters.MovieListAdapter.Companion.MOVIES_TYPE
import dev.test.project.adapters.MovieListAdapter.Companion.TITLE_TYPE
import dev.test.project.interfaces.MovieListView
import dev.test.project.interfaces.OnItemClickListener
import dev.test.project.items.Movie
import dev.test.project.presenters.MovieListPresenter
import kotlinx.android.synthetic.main.fragment_movie_list.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


class MovieListFragment : MvpAppCompatFragment(R.layout.fragment_movie_list), MovieListView {

    private val adapter = MovieListAdapter()
    private var statePosition: Parcelable? = null
    private lateinit var layoutManager: GridLayoutManager
    private val presenter by moxyPresenter { MovieListPresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reenterTransition = MaterialSharedAxis(
            MaterialSharedAxis.Z, false)
        exitTransition = MaterialSharedAxis(
            MaterialSharedAxis.Z, true)
        statePosition = savedInstanceState?.getParcelable("statePosition")
        val checkedPosition = savedInstanceState?.getInt("checkedPosition")
        checkedPosition?.let { adapter.setChecked(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_movie_list.adapter = adapter
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onGenreClick(item: String?) {
                presenter.filterData(item)
            }
            override fun onMovieClick(item: Movie) {
                val bundle = Bundle().apply {
                    putParcelable("movie", item)
                }
                findNavController().navigate(R.id.movieInfoFragment, bundle)
            }
        })
        rv_movie_list.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                statePosition = layoutManager.onSaveInstanceState()
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("statePosition", statePosition)
        outState.putInt("checkedPosition", adapter.checkedPosition)
    }

    override fun initView(list: MutableList<Any>) {
        empty_movie_list.setVisibility(false)
        showLoading(false)
        rv_movie_list.setVisibility(true)
        setupRecyclerView(list)
    }

    override fun showMovies(movies: List<Movie>) {
        adapter.setMovies(movies)
    }

    override fun showError() {
        showLoading(false)
        val snack = Snackbar.make(root_movie_list,
            getString(R.string.error_internet),
            Snackbar.LENGTH_INDEFINITE)
        snack.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.snackColor))
        snack.setAction(getString(R.string.try_again)) {
            presenter.fetchMovies()
        }
        snack.show()
    }

    override fun showLoading(value: Boolean) {
        when(value) {
            true ->
                progress_bar_movie_list.setVisibility(true)
            false ->
                progress_bar_movie_list.setVisibility(false)
        }
    }

    override fun showEmptyList() {
        showLoading(false)
        rv_movie_list.setVisibility(false)
        empty_movie_list.setVisibility(true)
    }

    private fun setupRecyclerView(list: MutableList<Any>) {
        layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        statePosition?.let { layoutManager.onRestoreInstanceState(it) }
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    TITLE_TYPE, GENRES_TYPE -> 2
                    MOVIES_TYPE -> 1
                    else -> 2
                }
            }
        }
        rv_movie_list.layoutManager = layoutManager
        adapter.setData(list)
    }
}