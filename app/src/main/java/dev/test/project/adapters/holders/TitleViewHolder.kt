package dev.test.project.adapters.holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.test.project.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_title.*

class TitleViewHolder(private val parent: ViewGroup,
                      override val containerView: View = LayoutInflater.from(parent.context)
                          .inflate(R.layout.item_title, parent, false))
    : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(title: String) {
        text_title_item.text = title
    }
}