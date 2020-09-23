package dev.test.project.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import dev.test.project.R
import dev.test.project.abstracts.BaseFragment
import dev.test.project.adapters.MovieListAdapter
import dev.test.project.adapters.MovieListAdapter.Companion.GENRES_TYPE
import dev.test.project.adapters.MovieListAdapter.Companion.MOVIES_TYPE
import dev.test.project.adapters.MovieListAdapter.Companion.TITLE_TYPE
import dev.test.project.presentation.view.MovieListView
import dev.test.project.interfaces.OnMoviesClickListener
import dev.test.project.items.Genre
import dev.test.project.items.Movie
import dev.test.project.presentation.presenter.MovieListPresenter
import dev.test.project.utils.*
import kotlinx.android.synthetic.main.fragment_movie_list.*
import moxy.ktx.moxyPresenter

class MovieListFragment : BaseFragment(R.layout.fragment_movie_list), MovieListView {

    private val adapter = MovieListAdapter()
    private lateinit var layoutManager: GridLayoutManager
    private val presenter by moxyPresenter { MovieListPresenter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitleToolbar(getString(R.string.movies))
        setupRecyclerView()
        adapter.setOnItemClickListener(object : OnMoviesClickListener {
            override fun onGenreClick(item: Genre?) {
                presenter.filterData(item)
                presenter.checkedGenre = item
                adapter.setChecked(item)
            }

            override fun onMovieClick(item: Movie) {
                val bundle = Bundle().apply {
                    putParcelable(MOVIE_BUNDLE_KEY, item)
                }
                findNavController().navigate(R.id.movieInfoFragment, bundle)
            }

            override fun onFavoriteClick(favorited: Boolean, item: Movie) {
                presenter.changeFavorite(favorited, item)
            }
        })
    }

    //Сохранение состояния списка
    override fun onPause() {
        super.onPause()
        presenter.statePosition = layoutManager.onSaveInstanceState()
        presenter.checkedGenre = adapter.checkedGenre
    }

    //Заполнение данных
    override fun initView(list: MutableList<Any>) {
        showLoading(false)
        rv_movie_list.setVisibility(true)
        adapter.setData(list)
        adapter.setChecked(presenter.checkedGenre)
        setMovieResultChanges()
    }

    //При наличии результата, показываем изменения
    private fun setMovieResultChanges() {
        val movie = getNavigationResult<Movie>(MOVIE_RESULT_KEY)
        if (movie != null) {
            adapter.movieFavorited(movie)
        }
    }

    //Задаем список фильмов
    override fun showMovies(movies: List<Movie>) {
        adapter.changeMovies(movies)
    }

    //Показываем ошибку
    override fun showError(error: String?) {
        if (error!=null) {
            showLoading(false)
            val snack = Snackbar.make(
                root_movie_list,
                getString(R.string.error_internet),
                Snackbar.LENGTH_INDEFINITE
            )
            snack.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.snackColor))
            snack.setAction(getString(R.string.try_again)) {
                presenter.fetchMovies()
            }
            snack.show()
        }
    }

    //Показываем загрузку
    override fun showLoading(value: Boolean) {
        progress_bar_movie_list.setVisibility(value)
    }

    //Настройка RecyclerView
    private fun setupRecyclerView() {
        rv_movie_list.adapter = adapter
        setupLayoutManager()
    }

    //Настройка колонок и восстановление состояния списка
    private fun setupLayoutManager() {
        layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        presenter.statePosition?.let { layoutManager.onRestoreInstanceState(it) }
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
}