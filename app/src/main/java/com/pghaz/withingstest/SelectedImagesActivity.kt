package com.pghaz.withingstest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.pghaz.withingstest.utils.Arguments

class SelectedImagesActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var selectedIds: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_images)

        if (intent.hasExtra(Arguments.ARGS_IMAGE_URL_SELECTED)) {
            selectedIds = intent.getStringArrayExtra(Arguments.ARGS_IMAGE_URL_SELECTED)
        }

        viewPager = findViewById(R.id.viewPager)

        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter

    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = selectedIds.size

        override fun createFragment(position: Int): Fragment =
            ScreenSlidePageFragment.newInstance(selectedIds[position])
    }
}