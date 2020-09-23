package dev.test.project.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dev.test.project.R
import dev.test.project.abstracts.BaseFragment
import dev.test.project.adapters.FavoriteListAdapter
import dev.test.project.interfaces.OnMovieClickListener
import dev.test.project.items.Movie
import dev.test.project.presentation.presenter.FavoriteListPresenter
import dev.test.project.presentation.view.FavoriteListView
import dev.test.project.utils.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_favorite_list.*
import moxy.ktx.moxyPresenter

class FavoriteListFragment: BaseFragment(R.layout.fragment_favorite_list), FavoriteListView {

    private val adapter = FavoriteListAdapter()
    private lateinit var layoutManager: GridLayoutManager
    private val presenter by moxyPresenter { FavoriteListPresenter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().toolbar_title.text = getString(R.string.favorite)
        setupRecyclerView()
        adapter.setOnItemClickListener(object : OnMovieClickListener {
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
        to_movies_btn_favorite_list.setOnClickListener {
            setSelectedTab(R.id.movieListFragment)
        }
    }

    //Сохранение состояния списка
    override fun onPause() {
        super.onPause()
        presenter.statePosition = layoutManager.onSaveInstanceState()
    }

    //Задаем и показываем список фильмов
    override fun showMovies(movies: List<Movie>) {
        showEmptyList(false)
        showLoading(false)
        rv_favorite_list.setVisibility(true)
        adapter.setData(movies.toMutableList())
        setMovieResultChanges()
    }

    //При наличии результата, показываем изменения
    private fun setMovieResultChanges() {
        val movie = getNavigationResult<Movie>(MOVIE_RESULT_KEY)
        if (movie != null) {
            adapter.movieFavorited(movie)
        }
    }

    //Показываем загрузку
    override fun showLoading(value: Boolean) {
        progress_bar_favorite_list.setVisibility(value)
    }

    //Показываем пустой список
    override fun showEmptyList(value: Boolean) {
        if(value) {
            showLoading(false)
            rv_favorite_list.setVisibility(false)
        }
        empty_favorite_list.setVisibility(value)
    }

    //Настройка RecyclerView
    private fun setupRecyclerView() {
        rv_favorite_list.adapter = adapter
        setupLayoutManager()
    }

    //Настройка колонок и восстановление состояния списка
    private fun setupLayoutManager() {
        layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        presenter.statePosition?.let { layoutManager.onRestoreInstanceState(it) }
        rv_favorite_list.layoutManager = layoutManager
    }
}