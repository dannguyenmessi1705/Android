package com.didan.android.jetpack.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.didan.android.jetpack.navigation.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, // LayoutInflater
            R.layout.fragment_second, // Layout resource
            container, // ViewGroup
            false // Do not attach to ViewGroup immediately
        )

        val input = requireArguments().getString("name") // Lấy dữ liệu từ Bundle với key "name"
        binding.textViewSecondFrag.text = input

        return binding.root
    }

}