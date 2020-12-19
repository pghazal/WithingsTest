package com.pghaz.withingstest.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


object RestClient {

    private const val PIXABAY_URL_API = "https://pixabay.com/"
    private const val PIXABAY_API_KEY = "5511001-7691b591d9508e60ec89b63c4"

    fun createPixabayServiceClient(): PixabayService {
        val retrofit = Retrofit.Builder()
            .client(createHttpClient(PIXABAY_API_KEY))
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PIXABAY_URL_API)
            .build()

        return retrofit.create(PixabayService::class.java)
    }

    private fun createHttpClient(apiKey: String): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ApiAuthenticator(apiKey))
            .build()
    }

    private class ApiAuthenticator(private val apiKey: String?) : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val originalHttpUrl = original.url()

            // Add key header to all API call
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("key", apiKey)
                .build()
            val requestBuilder = original.newBuilder()
                .url(url)
            val request = requestBuilder.build()

            return chain.proceed(request)
        }
    }
}