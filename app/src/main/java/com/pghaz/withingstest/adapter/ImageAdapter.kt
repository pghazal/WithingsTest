package com.pghaz.withingstest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.pghaz.withingstest.R
import com.pghaz.withingstest.viewmodel.ImageViewModel

class ImageAdapter : ListAdapter<ImageViewModel, ImageViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_image, parent, false) as View

        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val hit = getItem(position)
        holder.bind(hit)
    }

    companion object {
        private val DiffUtilCallback = object : DiffUtil.ItemCallback<ImageViewModel>() {
            override fun areItemsTheSame(
                oldItem: ImageViewModel,
                newItem: ImageViewModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ImageViewModel,
                newItem: ImageViewModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}