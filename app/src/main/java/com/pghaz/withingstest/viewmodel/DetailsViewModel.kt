package com.pghaz.withingstest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pghaz.withingstest.domain.Hit
import com.pghaz.withingstest.domain.PixabayResult
import com.pghaz.withingstest.domain.internal.DetailsItemViewModel
import com.pghaz.withingstest.network.RestClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsViewModel : ViewModel() {

    private val pixabayService = RestClient.createPixabayServiceClient()

    val hitLiveData = MutableLiveData<DetailsItemViewModel>()

    fun getImage(id: String) {
        val call = pixabayService.getImage(id)

        call.enqueue(object : Callback<PixabayResult> {
            override fun onResponse(call: Call<PixabayResult>, response: Response<PixabayResult>) {
                if (response.isSuccessful) {
                    response.body()?.hits?.get(0)?.let {
                        hitLiveData.value = transform(it)
                    } ?: kotlin.run {
                        // TODO: call onFailure
                    }
                } else {
                    // TODO: call onFailure
                }
            }

            override fun onFailure(call: Call<PixabayResult>, t: Throwable) {
                // TODO
            }
        })
    }

    private fun transform(hit: Hit): DetailsItemViewModel {
        val id = hit.id
        val imageUrl = getImageUrlToLoad(hit)

        return DetailsItemViewModel(id, imageUrl, hit.views)
    }

    private fun getImageUrlToLoad(hit: Hit): String {
        return when {
            hit.webformatURL.isNotEmpty() -> {
                hit.webformatURL
            }
            hit.imageURL.isNotEmpty() -> {
                hit.imageURL
            }
            hit.previewURL.isNotEmpty() -> {
                hit.previewURL
            }
            else -> {
                ""
            }
        }
    }
}