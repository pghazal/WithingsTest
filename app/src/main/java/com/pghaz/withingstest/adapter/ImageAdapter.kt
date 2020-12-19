package com.pghaz.withingstest.adapter

import android.util.SparseLongArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.contains
import androidx.core.util.forEach
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.pghaz.withingstest.R
import com.pghaz.withingstest.viewmodel.ImageViewModel

class ImageAdapter(private var listener: IItemClickListener) :
    ListAdapter<ImageViewModel, ImageViewHolder>(DiffUtilCallback) {

    private val selectedIds = SparseLongArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_image, parent, false) as View

        return ImageViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val hit = getItem(position)
        holder.bind(hit, isSelected(position))
    }

    private fun isSelected(position: Int): Boolean {
        return selectedIds.contains(position)
    }

    fun toggleSelection(imageViewModel: ImageViewModel, position: Int) {
        if (selectedIds[position] != 0L) {
            selectedIds.delete(position)
        } else {
            selectedIds.put(position, imageViewModel.id)
        }

        notifyItemChanged(position)
    }

    fun getSelectedItemCount(): Int {
        return selectedIds.size()
    }

    fun getSelectedIds(): List<Long> {
        val items: MutableList<Long> = ArrayList(selectedIds.size())

        selectedIds.forEach { key, value ->
            items.add(value)
        }

        return items
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