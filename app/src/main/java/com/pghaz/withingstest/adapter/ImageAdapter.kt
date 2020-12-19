package com.pghaz.withingstest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.pghaz.withingstest.R
import com.pghaz.withingstest.viewmodel.ImageViewModel

class ImageAdapter(private var listener: IItemClickListener) :
    ListAdapter<ImageViewModel, ImageViewHolder>(DiffUtilCallback) {

    private val selectedIds = HashMap<Int, String>()

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
        if (selectedIds.containsKey(position)) {
            selectedIds.remove(position)
        } else {
            selectedIds[position] = imageViewModel.imageUrl
        }

        notifyItemChanged(position)
    }

    fun getSelectedItemCount(): Int {
        return selectedIds.size
    }

    fun getSelectedItems(): List<String> {
        val items: MutableList<String> = ArrayList(selectedIds.size)

        selectedIds.forEach {
            items.add(it.value)
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