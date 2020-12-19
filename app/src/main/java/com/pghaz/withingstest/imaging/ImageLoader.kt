package com.pghaz.withingstest.imaging

class ImageLoader {

    companion object {
        fun get(): IImageLoader {
            return PicassoImageLoader()
        }
    }
}