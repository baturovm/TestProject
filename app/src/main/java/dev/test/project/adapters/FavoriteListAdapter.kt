package dev.test.project.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.test.project.R
import dev.test.project.adapters.holders.MoviesViewHolder
import dev.test.project.interfaces.OnMovieClickListener
import dev.test.project.items.Movie

class FavoriteListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: MutableList<Movie> = mutableListOf()
    private lateinit var clickListener: OnMovieClickListener

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

    fun setOnItemClickListener(clickListener: OnMovieClickListener) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MoviesViewHolder(clickListener,
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as MoviesViewHolder
        holder.bind(items[position])
    }
}