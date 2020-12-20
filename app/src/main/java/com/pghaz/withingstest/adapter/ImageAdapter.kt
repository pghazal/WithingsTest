package com.pghaz.withingstest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.pghaz.withingstest.R
import com.pghaz.withingstest.domain.internal.ImageViewModel

class ImageAdapter(private var listener: IItemClickListener) :
    ListAdapter<ImageViewModel, ImageViewHolder>(DiffUtilCallback) {

    private val selectedItems = HashMap<Int, ImageViewModel>()

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
        return selectedItems.contains(position)
    }

    fun toggleSelection(imageViewModel: ImageViewModel, position: Int) {
        if (selectedItems.containsKey(position)) {
            selectedItems.remove(position)
        } else {
            selectedItems[position] = imageViewModel
        }

        notifyItemChanged(position)
    }

    fun getSelectedItemCount(): Int {
        return selectedItems.size
    }

    fun getSelectedItems(): ArrayList<ImageViewModel> {
        val items: ArrayList<ImageViewModel> = ArrayList(selectedItems.size)

        selectedItems.forEach {
            items.add(it.value)
        }

        return items
    }

    fun clearSelection() {
        selectedItems.clear()
        notifyDataSetChanged()
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