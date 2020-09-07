package dev.test.project.fragments

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.transition.MaterialSharedAxis
import dev.test.project.R
import dev.test.project.items.Movie
import dev.test.project.utils.pxFromDp
import kotlinx.android.synthetic.main.fragment_movie_info.*
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlin.math.roundToInt

class MovieInfoFragment : Fragment(R.layout.fragment_movie_info) {

    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(
            MaterialSharedAxis.Z, true
        )
        returnTransition = MaterialSharedAxis(
            MaterialSharedAxis.Z, false
        )
        arguments?.let {
            movie = it.getParcelable("movie")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar_movie_info.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        initView()
    }

    private fun initView() {
        title_en_movie_info.text = movie?.titleEN
        Glide.with(requireContext())
            .load(movie?.imageURL)
            .transform(CenterCrop(), RoundedCorners(pxFromDp(requireContext(), 4)))
            .error(R.drawable.placeholder_movie_info)
            .into(poster_movie_info)
        title_movie_info.text = movie?.titleRU
        val infoText = if(movie?.genres.isNullOrEmpty()) {
            "${movie?.year} ${getString(R.string.year)}"
        } else {
            "${movie?.genres?.joinToString()}, ${movie?.year} ${getString(R.string.year)}"
        }
        val rating = if(movie?.rating.isNullOrEmpty()) {
            "--"
        } else {
            (movie!!.rating!!.toDouble() * 10.0).roundToInt() / 10.0
        }
        infoText_movie_info.text = infoText
        rating_movie_info.text = rating.toString()
        desc_movie_info.text = movie?.description
    }
}