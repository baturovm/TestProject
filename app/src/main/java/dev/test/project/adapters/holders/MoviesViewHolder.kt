package dev.test.project.adapters.holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import dev.test.project.R
import dev.test.project.interfaces.MovieListener
import dev.test.project.items.Movie
import dev.test.project.utils.pxFromDp
import dev.test.project.utils.toggleActive
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie.*

class MoviesViewHolder(private val parent: ViewGroup,
                       private val inflater: LayoutInflater,
                       override val containerView: View = inflater
                           .inflate(R.layout.item_movie, parent, false))
    : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(listener: MovieListener, item: Movie) {
        title_movie_item.text = item.titleRU
        favorite_btn_movie.isActivated = item.favorited
        Glide.with(poster_movie_item)
            .load(item.imageURL)
            .transform(CenterCrop(), RoundedCorners(pxFromDp(itemView.context, 4)))
            .error(R.drawable.placeholder_movie_list)
            .into(poster_movie_item)
        card_movie.setOnClickListener {
            listener.onMovieClick(item)
        }
        favorite_btn_movie.setOnClickListener {
            item.favorited = it.toggleActive()
            listener.onFavoriteClick(item)
        }
    }
}