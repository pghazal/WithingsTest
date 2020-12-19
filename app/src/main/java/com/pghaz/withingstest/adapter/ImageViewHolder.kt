package com.pghaz.withingstest.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.pghaz.withingstest.R
import com.pghaz.withingstest.imaging.ImageLoader
import com.pghaz.withingstest.viewmodel.ImageViewModel

class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imageView = itemView.findViewById<ImageView>(R.id.imageView)

    fun bind(imageViewModel: ImageViewModel) {
        ImageLoader.get().load(imageViewModel.imageUrl)
            .into(imageView)
    }
}