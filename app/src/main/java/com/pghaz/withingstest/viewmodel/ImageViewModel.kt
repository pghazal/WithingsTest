package com.pghaz.withingstest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pghaz.withingstest.domain.Hit
import com.pghaz.withingstest.domain.PixabayResult
import com.pghaz.withingstest.network.RestClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageViewModel : ViewModel() {

    private val pixabayService = RestClient.createPixabayServiceClient()

    val hitsLiveData = MutableLiveData<List<Hit>>()

    fun searchImages(query: String?) {
        val call = pixabayService.searchImages(query)

        call.enqueue(object : Callback<PixabayResult> {
            override fun onResponse(call: Call<PixabayResult>, response: Response<PixabayResult>) {
                if (response.isSuccessful) {
                    response.body()?.hits?.let {
                        hitsLiveData.value = it
                    }
                } else {
                    // TODO: call onFailure()
                }
            }

            override fun onFailure(call: Call<PixabayResult>, t: Throwable) {
                // TODO: handle error case
            }
        })
    }
}