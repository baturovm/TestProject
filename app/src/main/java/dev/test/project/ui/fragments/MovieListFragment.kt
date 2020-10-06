package dev.test.project.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import dev.test.project.R
import dev.test.project.abstracts.BaseFragment
import dev.test.project.adapters.MovieListAdapter
import dev.test.project.adapters.MovieListAdapter.Companion.GENRES_TYPE
import dev.test.project.adapters.MovieListAdapter.Companion.MOVIES_TYPE
import dev.test.project.adapters.MovieListAdapter.Companion.TITLE_TYPE
import dev.test.project.interfaces.GenreListener
import dev.test.project.interfaces.MovieListener
import dev.test.project.items.Genre
import dev.test.project.items.Movie
import dev.test.project.items.MoviesObject
import dev.test.project.presentation.presenter.MovieListPresenter
import dev.test.project.presentation.view.MovieListView
import dev.test.project.utils.*
import kotlinx.android.synthetic.main.fragment_movie_list.*
import moxy.ktx.moxyPresenter

/**
 * Экран списка фильмов и жанров
 */
class MovieListFragment : BaseFragment(R.layout.fragment_movie_list), MovieListView {

    private val adapter = MovieListAdapter()
    private lateinit var layoutManager: GridLayoutManager
    private val presenter by moxyPresenter { MovieListPresenter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitleToolbar(getString(R.string.movies))
        setupRecyclerView()
        adapter.setGenreListener(object : GenreListener {
            override fun onGenreClick(item: Genre) {
                presenter.filterData(item)
                adapter.setCheckedItem(item)
            }
        })
        adapter.setMovieListener(object : MovieListener {
            override fun onMovieClick(item: Movie) {
                openMovieInfo(item)
            }

            override fun onFavoriteClick(item: Movie) {
                presenter.changeFavorite(item)
            }
        })
    }

    override fun showList(data: MoviesObject) {
        progress_bar_movie_list.setVisibility(false)
        rv_movie_list.setVisibility(true)
        adapter.setData(mutableListOf<Any>().apply {
            add(ResourceUtils.getString(R.string.genres))
            addAll(data.genres)
            add(ResourceUtils.getString(R.string.movies))
            addAll(data.movies)
        })
        adapter.setCheckedItem(presenter.checkedGenre)
        getMovieResultChanges()
    }

    override fun setMoviesList(movies: List<Movie>) {
        adapter.setMovies(movies)
    }

    override fun showError(error: String) {
        progress_bar_movie_list.setVisibility(false)
        val snack = Snackbar.make(
            root_movie_list,
            error,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.snackColor))
        snack.setAction(getString(R.string.try_again)) {
            presenter.fetchMovies()
        }
        snack.show()
    }

    override fun showLoading() {
        progress_bar_movie_list.setVisibility(true)
    }

    /**
     * При наличии результата, показываем изменения
     */
    private fun getMovieResultChanges() {
        val movie = getNavigationResult<Movie>(MOVIE_RESULT_KEY)
        if (movie != null) {
            adapter.movieFavorited(movie)
        }
    }

    /**
     * Настройка RecyclerView
     */
    private fun setupRecyclerView() {
        rv_movie_list.adapter = adapter
        setupLayoutManager()
    }

    /**
     * Настройка колонок и восстановление состояния списка
     */
    private fun setupLayoutManager() {
        layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
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
    }

    /**
     * Открытие информации о фильме
     */
    private fun openMovieInfo(item: Movie) {
        val bundle = bundleOf(
            MOVIE_BUNDLE_KEY to item
        )
        findNavController().navigate(R.id.movieInfoFragment, bundle)
    }
}