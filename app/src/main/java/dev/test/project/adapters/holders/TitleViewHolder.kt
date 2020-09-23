package dev.test.project.adapters.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_title.view.*

class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(title: String) {
        itemView.text_title_item.text = title
    }
}