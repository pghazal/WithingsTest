package com.pghaz.withingstest.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.pghaz.withingstest.R
import com.pghaz.withingstest.domain.internal.ImageViewModel
import com.pghaz.withingstest.imaging.ImageLoader
import com.pghaz.withingstest.utils.Arguments
import com.pghaz.withingstest.viewmodel.DetailsViewModel

class DetailsActivity : AppCompatActivity() {

    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var model: ImageViewModel

    private lateinit var imageView: ImageView
    private lateinit var viewsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        initViews()
        initViewModel()

        model = intent.getParcelableExtra(Arguments.ARGS_MODEL)

        detailsViewModel.getImage(model.id.toString())
    }

    private fun initViews() {
        imageView = findViewById(R.id.imageView)
        viewsTextView = findViewById(R.id.viewsTextView)
    }

    private fun initViewModel() {
        detailsViewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        detailsViewModel.hitLiveData.observe(this, {
            ImageLoader.get().load(it.imageUrl).into(imageView)
            viewsTextView.text = it.views.toString()
        })
    }
}