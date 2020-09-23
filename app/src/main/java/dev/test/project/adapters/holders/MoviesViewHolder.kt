package dev.test.project.adapters.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import dev.test.project.R
import dev.test.project.interfaces.OnMovieClickListener
import dev.test.project.items.Movie
import dev.test.project.utils.pxFromDp
import dev.test.project.utils.toggleActive
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesViewHolder(private val clickListener: OnMovieClickListener, itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Movie) {
        itemView.title_movie_item.text = item.titleRU
        itemView.isActivated = item.favorited
        Glide.with(itemView)
            .load(item.imageURL)
            .transform(CenterCrop(), RoundedCorners(pxFromDp(itemView.context, 4)))
            .error(R.drawable.placeholder_movie_list)
            .into(itemView.poster_movie_item)
        itemView.card_movie.setOnClickListener {
            clickListener.onMovieClick(item)
        }
        itemView.favorite_btn_movie.setOnClickListener {
            clickListener.onFavoriteClick(it.toggleActive(), item)
        }
    }
}