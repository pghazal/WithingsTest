package com.pghaz.withingstest

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.pghaz.withingstest.adapter.ImageAdapter
import com.pghaz.withingstest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var imageAdapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        val root = viewBinding.root
        setContentView(root)

        imageAdapter = initImageAdapter()
        configureRecyclerView(this, viewBinding, imageAdapter)
    }

    private fun initImageAdapter(): ImageAdapter {
        return ImageAdapter()
    }

    private fun configureRecyclerView(context: Context, viewBinding: ActivityMainBinding, imageAdapter: ImageAdapter) {
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        viewBinding.recyclerView.adapter = imageAdapter
    }
}