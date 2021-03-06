package dev.test.project.adapters.holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.test.project.R
import dev.test.project.adapters.holders.listeners.GenreListener
import dev.test.project.items.Genre
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_genres.*
import java.util.*

class GenresViewHolder(private val parent: ViewGroup,
                       private val inflater: LayoutInflater,
                       override val containerView: View = inflater
                           .inflate(R.layout.item_genres, parent, false))
    : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(listener: GenreListener, item: Genre, checkedGenre: Genre?) {
        card_genre.isChecked = checkedGenre == item
        text_genre_item.text = item.genre.capitalize(Locale.ROOT)
        card_genre.setOnClickListener {
            listener.onGenreClick(item)
        }
    }
}