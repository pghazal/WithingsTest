package com.pghaz.withingstest.network

import com.pghaz.withingstest.domain.PixabayResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayService {

    @GET("api/")
    fun searchImages(@Query("q") query: String?): Call<PixabayResult>

    @GET("api/")
    fun getImage(@Query("id") id: String): Call<PixabayResult>
}