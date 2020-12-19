package com.pghaz.withingstest

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pghaz.withingstest.adapter.ImageAdapter
import com.pghaz.withingstest.databinding.ActivityMainBinding
import com.pghaz.withingstest.viewmodel.SearchViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var imageAdapter: ImageAdapter

    private lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        val root = viewBinding.root
        setContentView(root)

        initViewModel()
        imageAdapter = initImageAdapter()
        configureRecyclerView(this, viewBinding, imageAdapter)
    }

    private fun initViewModel() {
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        searchViewModel.hitsLiveData.observe(this, { hits ->
            imageAdapter.submitList(hits)
        })
    }

    private fun initImageAdapter(): ImageAdapter {
        return ImageAdapter()
    }

    private fun configureRecyclerView(context: Context, viewBinding: ActivityMainBinding, imageAdapter: ImageAdapter) {
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        viewBinding.recyclerView.adapter = imageAdapter
    }

    override fun onStart() {
        super.onStart()

        // TODO: test -> remove when search view is working
        searchViewModel.searchImages("yellow flowers")
    }
}