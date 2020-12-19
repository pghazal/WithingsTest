package com.pghaz.withingstest.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


object RestClient {

    private const val PIXABAY_URL_API = "https://pixabay.com/api"
    private const val PIXABAY_API_KEY = "5511001-7691b591d9508e60ec89b63c4"

    fun createImageServiceClient(): ImageService {
        val retrofit = Retrofit.Builder()
            .client(createHttpClient(PIXABAY_API_KEY))
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PIXABAY_URL_API)
            .build()

        return retrofit.create(ImageService::class.java)
    }

    private fun createHttpClient(apiKey: String): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ApiAuthenticator(apiKey))
            .build()
    }

    private class ApiAuthenticator(private val apiKey: String?) : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()

            if (apiKey != null) {
                val authRequest = request.newBuilder()
                    .addHeader("key", apiKey)
                    .build()

                return chain.proceed(authRequest)
            }

            return chain.proceed(request)
        }
    }
}