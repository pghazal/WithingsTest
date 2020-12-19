package com.pghaz.withingstest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pghaz.withingstest.utils.Arguments

class SelectedImagesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_images)

        val selectedIds = intent.getLongArrayExtra(Arguments.ARGS_IMAGE_IDS_SELECTED)
    }

}