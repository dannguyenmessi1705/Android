package com.didan.android.jetpack.navigation

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.didan.android.jetpack.navigation.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout vào DataBindingUtil
        binding = DataBindingUtil.inflate(
            inflater, // LayoutInflater
            R.layout.fragment_home, // Layout resource
            container, // ViewGroup
            false // Không gắn vào ViewGroup ngay
        )


        // Handle các sự kiện và logic khác ở đây nếu cần
        binding.btnSumit.setOnClickListener {// Tạo Bundle để truyền dữ liệu giữa các Fragment
            // Nếu EditText không rỗng
            if (!TextUtils.isEmpty(binding.editText.text.toString())) {
                val bundle =
                    bundleOf("name" to binding.editText.text.toString()) // Tạo Bundle với key "name" và giá trị từ EditText

                it.findNavController() // Tìm NavController từ View
                    .navigate(
                        R.id.action_homeFragment_to_secondFragment, // Thực hiện action điều hướng đã định trong nav_graph.xml (HomeFragment -> SecondFragment)
                        bundle // Truyền Bundle chứa dữ liệu sang SecondFragment
                    )
            } else {
                Toast.makeText(activity, "Enter your name", Toast.LENGTH_SHORT).show()
            }
        }


        return binding.root // Trả về root view của binding
    }
}