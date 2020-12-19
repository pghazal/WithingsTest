package com.pghaz.withingstest.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.pghaz.withingstest.R
import com.pghaz.withingstest.domain.Hit

class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imageView = itemView.findViewById<ImageView>(R.id.imageView)

    fun bind(hit: Hit) {
        // TODO: fix image loading
        /*val uri = Uri.parse(hit.imageURL)
        imageView.setImageURI(uri)*/
    }
}