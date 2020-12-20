package com.pghaz.withingstest.adapter

import com.pghaz.withingstest.domain.internal.ImageViewModel

interface IItemClickListener {

    fun onItemClicked(imageViewModel: ImageViewModel, position: Int)

    fun onItemLongClicked(imageViewModel: ImageViewModel, position: Int)
}