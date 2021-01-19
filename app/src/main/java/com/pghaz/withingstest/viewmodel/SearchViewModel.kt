package com.pghaz.withingstest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pghaz.withingstest.domain.Hit
import com.pghaz.withingstest.domain.internal.ImageViewModel
import com.pghaz.withingstest.network.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchViewModel : ViewModel() {

    private val pixabayService = RestClient.createPixabayServiceClient()

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    val hitsLiveData = MutableLiveData<List<ImageViewModel>>()
    val errorLiveData = MutableLiveData<Throwable>()

    fun searchImages(query: String?) {
        val searchImageObservable = pixabayService.searchImages(query)

        val searchDisposable = searchImageObservable
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map {
                val imageViewModels = ArrayList<ImageViewModel>()

                it.hits.let { hits ->
                    hits.forEach { hit ->
                        val imageViewModel = transform(hit)
                        imageViewModels.add(imageViewModel)
                    }
                }

                return@map imageViewModels
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                hitsLiveData.value = it
            }

        compositeDisposable.add(searchDisposable)

        /*call.enqueue(object : Callback<PixabayResult> {
            override fun onResponse(call: Call<PixabayResult>, response: Response<PixabayResult>) {
                if (response.isSuccessful) {
                    response.body()?.hits?.let { hits ->
                        val imageViewModels = ArrayList<ImageViewModel>()

                        hits.forEach { hit ->
                            val imageViewModel = transform(hit)
                            imageViewModels.add(imageViewModel)
                        }

                        hitsLiveData.value = imageViewModels
                    } ?: kotlin.run {
                        onFailure(call, ErrorException(response.errorBody()?.string()))
                    }
                } else {
                    onFailure(call, ErrorException(response.errorBody()?.string()))
                }
            }

            override fun onFailure(call: Call<PixabayResult>, t: Throwable) {
                errorLiveData.value = t
            }
        })*/
    }

    private fun transform(hit: Hit): ImageViewModel {
        val id = hit.id
        val imageUrl = getImageUrlToLoad(hit)

        return ImageViewModel(id, imageUrl)
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

    fun clearSearch() {
        hitsLiveData.value = emptyList()
    }
}