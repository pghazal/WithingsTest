package com.pghaz.withingstest.imaging

import android.widget.ImageView
import com.pghaz.withingstest.R
import com.squareup.picasso.Picasso

class PicassoImageLoader : IImageLoader {

    private val picasso = Picasso.get()

    private var url: String? = null
    private var placeholderResId: Int? = null
    private var defaultPlaceholderResId: Int = R.drawable.ic_placeholder

    override fun load(url: String?): IImageLoader {
        this.url = url
        return this
    }

    override fun placeholder(placeholderResId: Int): IImageLoader {
        this.placeholderResId = placeholderResId
        return this
    }

    override fun into(imageView: ImageView?) {
        val requestCreator = picasso.load(url)

        placeholderResId?.let {
            requestCreator.placeholder(it)
        } ?: run {
            requestCreator.placeholder(defaultPlaceholderResId)
        }

        requestCreator.into(imageView)
    }
}