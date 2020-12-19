package com.pghaz.withingstest.adapter

import com.pghaz.withingstest.viewmodel.ImageViewModel

interface IItemClickListener {

    fun onImageClickedListener(imageViewModel: ImageViewModel, position: Int)
}