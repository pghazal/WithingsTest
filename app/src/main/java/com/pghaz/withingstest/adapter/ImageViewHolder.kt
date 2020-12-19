package com.pghaz.withingstest.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.pghaz.withingstest.R
import com.pghaz.withingstest.domain.Hit
import com.pghaz.withingstest.imaging.ImageLoader

class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imageView = itemView.findViewById<ImageView>(R.id.imageView)

    fun bind(hit: Hit) {
        val imageUrl = when {
            hit.previewURL.isNotEmpty() -> {
                hit.previewURL
            }
            hit.imageURL.isNotEmpty() -> {
                hit.imageURL
            }
            /* else if other case like fullHD, etc... */
            else -> {
                null
            }
        }

        ImageLoader.get().load(imageUrl)
            .into(imageView)
    }
}