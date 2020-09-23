package dev.test.project.adapters.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import dev.test.project.interfaces.OnMoviesClickListener
import dev.test.project.items.Genre
import kotlinx.android.synthetic.main.item_genres.view.*
import java.util.*

class GenresViewHolder(private val clickListener: OnMoviesClickListener,
                       itemView: View,) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Genre, checkedGenre: Genre?) {
        itemView as MaterialCardView
        itemView.isChecked = checkedGenre == item
        itemView.text_genre_item.text = item.genre.capitalize(Locale.ROOT)
        itemView.setOnClickListener {
            if(itemView.isChecked)
                clickListener.onGenreClick(null)
            else
                clickListener.onGenreClick(item)
        }
    }
}