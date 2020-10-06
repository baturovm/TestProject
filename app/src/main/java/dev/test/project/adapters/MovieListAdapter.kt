package dev.test.project.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.test.project.adapters.holders.GenresViewHolder
import dev.test.project.adapters.holders.MoviesViewHolder
import dev.test.project.adapters.holders.TitleViewHolder
import dev.test.project.adapters.holders.listeners.GenreListener
import dev.test.project.adapters.holders.listeners.MovieListener
import dev.test.project.items.Genre
import dev.test.project.items.Movie

/**
 * Адаптер для списка фильмов и жанров
 */
class MovieListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TITLE_TYPE: Int = 0
        const val GENRES_TYPE: Int = 1
        const val MOVIES_TYPE: Int = 2
    }

    private var items: MutableList<Any> = mutableListOf()
    private lateinit var genreListener: GenreListener
    private lateinit var movieListener: MovieListener
    private var checkedGenre: Genre? = null

    fun setData(list: MutableList<Any>) {
        items = list
        notifyDataSetChanged()
    }

    fun setMovies(movies: List<Movie>) {
        val startPos = getFirstMoviePosition()
        items = items.take(startPos).toMutableList()
        items.addAll(startPos, movies)
        notifyDataSetChanged()
    }

    /**
     * @param item жанр который нужно выделить
     */
    fun setCheckedItem(item: Genre?) {
        when {
            item != null && checkedGenre != item -> {
                val position = items.indexOf(item)
                notifyItemChanged(position)
                checkedGenre = item
            }
            else -> {
                uncheckGenre()
            }
        }
    }

    private fun uncheckGenre() {
        checkedGenre?.let {
            val position = items.indexOf(it)
            checkedGenre = null
            notifyItemChanged(position)
        }
    }

    private fun getFirstMoviePosition(): Int {
        var position: Int? = null
        items.forEachIndexed { i: Int, any: Any ->
            if (any is Movie) {
                if(position == null) position = i
                return@forEachIndexed
            }
        }
        return position!!
    }

    fun movieFavorited(item: Movie) {
        items.forEachIndexed { index, any ->
            if(any is Movie && any.id == item.id) {
                any.favorited = item.favorited
                notifyItemChanged(index)
                return
            }
        }
    }

    fun setMovieListener(listener: MovieListener) {
        this.movieListener = listener
    }

    fun setGenreListener(listener: GenreListener) {
        this.genreListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            TITLE_TYPE -> TitleViewHolder(parent, inflater)
            MOVIES_TYPE -> MoviesViewHolder(parent, inflater)
            else -> GenresViewHolder(parent, inflater)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return when(items[position]) {
            is Movie -> MOVIES_TYPE
            is Genre -> GENRES_TYPE
            else -> TITLE_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)) {
            MOVIES_TYPE -> {
                holder as MoviesViewHolder
                holder.bind(movieListener, items[position] as Movie)
            }
            GENRES_TYPE -> {
                holder as GenresViewHolder
                holder.bind(genreListener, items[position] as Genre, checkedGenre)
            }
            TITLE_TYPE -> {
                holder as TitleViewHolder
                holder.bind(items[position].toString())
            }
        }
    }
}
