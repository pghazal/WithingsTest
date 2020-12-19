package com.pghaz.withingstest.domain
import com.google.gson.annotations.SerializedName

data class PixabayResult(
    @SerializedName("total")
    val total: Int,
    @SerializedName("totalHits")
    val totalHits: Int,
    @SerializedName("hits")
    val hits: List<Hit>
)