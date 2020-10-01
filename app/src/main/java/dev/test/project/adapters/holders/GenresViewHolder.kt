package dev.test.project.adapters.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import dev.test.project.interfaces.OnMoviesClickListener
import dev.test.project.items.Genre
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_genres.*
import java.util.*

class GenresViewHolder(private val clickListener: OnMoviesClickListener,
                       override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(item: Genre, checkedGenre: Genre?) {
        card_genre.isChecked = checkedGenre == item
        text_genre_item.text = item.genre.capitalize(Locale.ROOT)
        card_genre.setOnClickListener {
            clickListener.onGenreClick(item)
        }
    }
}