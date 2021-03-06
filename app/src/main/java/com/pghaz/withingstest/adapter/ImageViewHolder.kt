package com.pghaz.withingstest.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.pghaz.withingstest.R
import com.pghaz.withingstest.imaging.ImageLoader
import com.pghaz.withingstest.domain.internal.ImageViewModel

class ImageViewHolder(itemView: View, private var listener: IItemClickListener) :
    RecyclerView.ViewHolder(itemView) {

    private val imageView = itemView.findViewById<ImageView>(R.id.imageView)
    private val selectionIndicatorView =
        itemView.findViewById<ImageView>(R.id.selectionIndicatorView)

    fun bind(imageViewModel: ImageViewModel, selected: Boolean) {
        itemView.setOnClickListener {
            listener.onItemClicked(imageViewModel, adapterPosition)
        }

        itemView.setOnLongClickListener {
            listener.onItemLongClicked(imageViewModel, adapterPosition)
            return@setOnLongClickListener true
        }

        ImageLoader.get().load(imageViewModel.imageUrl)
            .into(imageView)

        selectionIndicatorView.visibility = if (selected) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}