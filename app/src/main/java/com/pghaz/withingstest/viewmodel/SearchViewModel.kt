package com.pghaz.withingstest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pghaz.withingstest.domain.Hit
import com.pghaz.withingstest.domain.PixabayResult
import com.pghaz.withingstest.network.RestClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    private val pixabayService = RestClient.createPixabayServiceClient()

    val hitsLiveData = MutableLiveData<List<ImageViewModel>>()

    fun searchImages(query: String?) {
        val call = pixabayService.searchImages(query)

        call.enqueue(object : Callback<PixabayResult> {
            override fun onResponse(call: Call<PixabayResult>, response: Response<PixabayResult>) {
                if (response.isSuccessful) {
                    response.body()?.hits?.let { hits ->
                        val imageViewModels = ArrayList<ImageViewModel>()

                        hits.forEach { hit ->
                            val imageViewModel = transform(hit)
                            imageViewModels.add(imageViewModel)
                        }

                        hitsLiveData.value = imageViewModels
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

    private fun transform(hit: Hit): ImageViewModel {
        val id = hit.id
        val imageUrl = getImageUrlToLoad(hit)

        return ImageViewModel(id, imageUrl)
    }

    private fun getImageUrlToLoad(hit: Hit): String? {
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
                null
            }
        }
    }

    fun clearSearch() {
        hitsLiveData.value = emptyList()
    }
}