package com.didan.android.viewpagertablayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class SimplePageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val args = requireArguments()
        val title = args.getString(ARG_TITLE).orEmpty()
        val description = args.getString(ARG_DESCRIPTION).orEmpty()
        val imageResId = args.getInt(ARG_IMAGE_RES)
        val backgroundColor = args.getInt(ARG_BG_COLOR)

        val root = view.findViewById<View>(R.id.pageRoot)
        val imageView = view.findViewById<ImageView>(R.id.pageImage)
        val titleView = view.findViewById<TextView>(R.id.pageTitle)
        val descriptionView = view.findViewById<TextView>(R.id.pageDescription)

        root.setBackgroundColor(backgroundColor)
        imageView.setImageResource(imageResId)
        imageView.contentDescription = title
        titleView.text = title
        descriptionView.text = description
    }

    companion object {
        private const val ARG_TITLE = "arg_title"
        private const val ARG_DESCRIPTION = "arg_description"
        private const val ARG_IMAGE_RES = "arg_image_res"
        private const val ARG_BG_COLOR = "arg_bg_color"

        fun newInstance(
            title: String,
            description: String,
            imageResId: Int,
            backgroundColor: Int
        ): SimplePageFragment {
            val fragment = SimplePageFragment()
            fragment.arguments = Bundle().apply {
                putString(ARG_TITLE, title)
                putString(ARG_DESCRIPTION, description)
                putInt(ARG_IMAGE_RES, imageResId)
                putInt(ARG_BG_COLOR, backgroundColor)
            }
            return fragment
        }
    }
}
