package dev.test.project.adapters.holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.test.project.R
import dev.test.project.interfaces.GenreListener
import dev.test.project.items.Genre
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_genres.*
import java.util.*

class GenresViewHolder(private val listener: GenreListener,
                       private val parent: ViewGroup,
                       override val containerView: View = LayoutInflater.from(parent.context)
                           .inflate(R.layout.item_genres, parent, false))
    : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(item: Genre, checkedGenre: Genre?) {
        card_genre.isChecked = checkedGenre == item
        text_genre_item.text = item.genre.capitalize(Locale.ROOT)
        card_genre.setOnClickListener {
            listener.onGenreClick(item)
        }
    }
}