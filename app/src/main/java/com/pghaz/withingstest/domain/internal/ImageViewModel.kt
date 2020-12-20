package com.pghaz.withingstest.domain.internal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageViewModel(
    val id: Long,
    val imageUrl: String
) : Parcelable
