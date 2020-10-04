package dev.test.project.ui.fragments

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import dev.test.project.abstracts.BaseFragment
import dev.test.project.R
import dev.test.project.presentation.view.MovieInfoView
import dev.test.project.items.Movie
import dev.test.project.presentation.presenter.MovieInfoPresenter
import dev.test.project.utils.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_movie_info.*
import moxy.ktx.moxyPresenter
import java.text.DecimalFormat

/**
 * Экрна информации о фильме
 */
class MovieInfoFragment : BaseFragment(R.layout.fragment_movie_info), MovieInfoView {

    private val presenter by moxyPresenter { MovieInfoPresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            presenter.movie = it.getParcelable(MOVIE_BUNDLE_KEY)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBottomNavigation(false)
        showBackIcon(true)
        favorite_btn_movie_info.setOnClickListener {
            presenter.changeFavorite(it.toggleActive())
        }
    }

    override fun onDetach() {
        super.onDetach()
        setNavigationResult(MOVIE_RESULT_KEY, presenter.movie)    //Задаем результат работы фрагмента
    }

    override fun showMovieInfo() {
        val movie = presenter.movie!!
        requireActivity().toolbar_title.text = movie.titleEN
        favorite_btn_movie_info.isActivated = movie.favorited
        Glide.with(requireContext())
            .load(movie.imageURL)
            .transform(CenterCrop(), RoundedCorners(pxFromDp(requireContext(), 4)))
            .error(R.drawable.placeholder_movie_info)
            .into(poster_movie_info)
        title_movie_info.text = movie.titleRU
        infoText_movie_info.text = getInfoText(movie)
        rating_movie_info.text = getRating(movie.rating)
        desc_movie_info.text = movie.description
    }

    private fun getInfoText(movie: Movie) = if(movie.genres.isNullOrEmpty()) {
        "${movie.year} ${getString(R.string.year)}"
    } else {
        "${movie.genres!!.joinToString()}, ${movie.year} ${getString(R.string.year)}"
    }

    private fun getRating(rating: Double?): String {
        return if (rating != null) {
            DecimalFormat("#.#").format(rating)
        } else {
            getString(R.string.null_rating)
        }
    }
}