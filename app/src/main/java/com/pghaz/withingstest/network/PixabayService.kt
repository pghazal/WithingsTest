package com.pghaz.withingstest.network

import com.pghaz.withingstest.domain.PixabayResult
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayService {

    @GET("api/")
    fun searchImages(@Query("q") query: String?): Observable<PixabayResult>

    @GET("api/")
    fun getImage(@Query("id") id: String): Call<PixabayResult>
}