package com.pghaz.withingstest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.pghaz.withingstest.imaging.ImageLoader
import com.pghaz.withingstest.utils.Arguments

class ScreenSlidePageFragment : Fragment() {

    private lateinit var imageUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            imageUrl = it.getString(Arguments.ARGS_URL).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_screen_slide_page, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageView = view.findViewById<ImageView>(R.id.imageView)

        ImageLoader.get().load(imageUrl).into(imageView)
    }

    companion object {
        fun newInstance(id: String): ScreenSlidePageFragment {
            val fragment = ScreenSlidePageFragment()

            val args = Bundle()
            args.putString(Arguments.ARGS_URL, id)

            fragment.arguments = args

            return fragment
        }
    }
}