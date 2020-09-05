package dev.test.project.adapters

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.card.MaterialCardView
import dev.test.project.App
import dev.test.project.R
import dev.test.project.utils.pxFromDp
import dev.test.project.interfaces.OnItemClickListener
import dev.test.project.items.Movie
import dev.test.project.utils.dpFromPx
import kotlinx.android.synthetic.main.item_genres.view.*
import kotlinx.android.synthetic.main.item_movie.view.*
import kotlinx.android.synthetic.main.item_title.view.*


class MovieListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TITLE_TYPE: Int = 0
        const val GENRES_TYPE: Int = 1
        const val MOVIES_TYPE: Int = 2
    }

    private var items: MutableList<Any> = mutableListOf()
    private lateinit var clickListener: OnItemClickListener

    private val moviesString = App.getInstance().getString(R.string.movies)
    private val genresString = App.getInstance().getString(R.string.genres)

    var checkedPosition: Int = -1

    fun setData(list: MutableList<Any>) {
        items = list
        notifyDataSetChanged()
    }

    fun setMovies(movies: List<Movie>) {
        val startPos = items.indexOf(moviesString) + 1
        items = items.take(startPos).toMutableList()
        items.addAll(startPos, movies)
        notifyDataSetChanged()
    }

    fun setChecked(position: Int) {
        checkedPosition = position
        notifyItemChanged(checkedPosition)
    }

    fun setOnItemClickListener(clickListener: OnItemClickListener) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            TITLE_TYPE -> TitleViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_title, parent, false)
            )
            MOVIES_TYPE -> MoviesViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_movie, parent, false)
            )
            else -> GenresViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_genres, parent, false)
            )
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return when(items[position]) {
            moviesString, genresString -> TITLE_TYPE
            is Movie -> MOVIES_TYPE
            else -> GENRES_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)) {
            TITLE_TYPE -> {
                holder as TitleViewHolder
                holder.bind(items[position].toString())
            }
            GENRES_TYPE -> {
                holder as GenresViewHolder
                holder.bind(items[position].toString())
            }
            else -> {
                holder as MoviesViewHolder
                holder.bind(items[position] as Movie)
            }
        }
    }

    inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Movie) {
            itemView.title_movie_item.text = item.titleRU
            Glide.with(itemView)
                .load(item.imageURL)
                .transform(CenterCrop(), RoundedCorners(pxFromDp(itemView.context, 4)))
                .error(R.drawable.placeholder_movie_list)
                .into(itemView.poster_movie_item)
            itemView.card_movie.setOnClickListener {
                clickListener.onMovieClick(item)
            }
        }
    }

    inner class GenresViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(genre: String) {
            itemView as MaterialCardView
            if (checkedPosition == -1) {
                itemView.isChecked = false
            } else {
                itemView.isChecked = checkedPosition == adapterPosition
            }
            itemView.text_genre_item.text = genre.capitalize()
            itemView.setOnClickListener {
                if (checkedPosition != adapterPosition) {
                    itemView.isChecked = true
                    notifyItemChanged(checkedPosition)
                    checkedPosition = adapterPosition
                    clickListener.onGenreClick(genre)
                } else {
                    itemView.isChecked = false
                    checkedPosition = -1
                    clickListener.onGenreClick(null)
                }
            }
        }
    }

    class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(title: String) {
            itemView.text_title_item.text = title
        }
    }
}
