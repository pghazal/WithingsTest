package com.pghaz.withingstest.network

import com.pghaz.withingstest.domain.Image
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ImageService {

    @GET("/")
    fun searchImages(@Query("q") query: String?): Call<List<Image>>
}