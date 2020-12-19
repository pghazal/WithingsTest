package com.pghaz.withingstest

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pghaz.withingstest.adapter.IItemClickListener
import com.pghaz.withingstest.adapter.ImageAdapter
import com.pghaz.withingstest.databinding.ActivityMainBinding
import com.pghaz.withingstest.viewmodel.ISearchView
import com.pghaz.withingstest.viewmodel.SearchViewModel

class MainActivity : AppCompatActivity(), ISearchView, IItemClickListener {

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
        configureSearchView(viewBinding)
    }

    private fun initViewModel() {
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        searchViewModel.hitsLiveData.observe(this, { hits ->
            imageAdapter.submitList(hits)
        })
    }

    private fun initImageAdapter(): ImageAdapter {
        return ImageAdapter(this)
    }

    private fun configureRecyclerView(context: Context, viewBinding: ActivityMainBinding, imageAdapter: ImageAdapter) {
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        viewBinding.recyclerView.adapter = imageAdapter
    }

    private fun configureSearchView(viewBinding: ActivityMainBinding) {
        val searchView = viewBinding.searchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()

                if (query.isEmpty()) {
                    clearSearch()
                } else {
                    searchImages(query)
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Is it intuitive to remove result from list when text is cleared..?
                if (newText.isNullOrEmpty()) {
                    clearSearch()
                }

                return false
            }
        })
    }

    override fun searchImages(query: String?) {
        searchViewModel.searchImages(query)
    }

    override fun clearSearch() {
        searchViewModel.clearSearch()
    }

    override fun onImageClickedListener(position: Int) {
        imageAdapter.toggleSelection(position)
    }
}