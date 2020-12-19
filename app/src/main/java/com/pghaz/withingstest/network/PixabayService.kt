package com.pghaz.withingstest.network

import com.pghaz.withingstest.domain.PixabayResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface PixabayService {

    @GET("/")
    fun searchImages(@Query("q") query: String?): Call<PixabayResult>
}