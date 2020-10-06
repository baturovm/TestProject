package dev.test.project.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.test.project.adapters.holders.MoviesViewHolder
import dev.test.project.adapters.holders.listeners.MovieListener
import dev.test.project.items.Movie

/**
 * Адаптер для списка избранных
 */
class FavoriteListAdapter : RecyclerView.Adapter<MoviesViewHolder>() {

    private var items: MutableList<Movie> = mutableListOf()
    private lateinit var movieListener: MovieListener

    fun setData(list: MutableList<Movie>) {
        items = list
        notifyDataSetChanged()
    }

    fun movieFavorited(item: Movie) {
        items.forEachIndexed { index, movie ->
            movie.favorited = item.favorited
            notifyItemChanged(index)
            return
        }
    }

    fun setMovieListener(listener: MovieListener) {
        this.movieListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MoviesViewHolder(parent, inflater)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(movieListener, items[position])
    }
}