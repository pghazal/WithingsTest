package com.pghaz.withingstest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.pghaz.withingstest.R
import com.pghaz.withingstest.domain.internal.ImageViewModel
import com.pghaz.withingstest.imaging.ImageLoader
import com.pghaz.withingstest.utils.Arguments

class ScreenSlidePageFragment : Fragment() {

    private lateinit var model: ImageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            model = it.getParcelable(Arguments.ARGS_MODEL)!!
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

        ImageLoader.get().load(model.imageUrl).into(imageView)
    }

    companion object {
        fun newInstance(model: ImageViewModel): ScreenSlidePageFragment {
            val fragment = ScreenSlidePageFragment()

            val args = Bundle()
            args.putParcelable(Arguments.ARGS_MODEL, model)

            fragment.arguments = args

            return fragment
        }
    }
}