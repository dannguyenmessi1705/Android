package com.didan.android.di.hilt.ecommerceapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.didan.android.di.hilt.ecommerceapp.databinding.ActivityMainBinding
import com.didan.android.di.hilt.ecommerceapp.util.CategoryAdapter
import com.didan.android.di.hilt.ecommerceapp.viewmodel.MyViewModel
import com.didan.android.di.hilt.ecommerceapp.views.CartActivity
import com.didan.android.di.hilt.ecommerceapp.views.CategoryItems
import dagger.hilt.android.AndroidEntryPoint

// Annotation @AndroidEntryPoint phải được đặt ở trên Activity để Hilt có thể tạo các thành phần cần thiết cho Dependency Injection (DI).
@AndroidEntryPoint // Annotation @AndroidEntryPoint để Hilt có thể tiêm phụ thuộc vào Activity này, cho phép sử dụng ViewModel được quản lý bởi Hilt.
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private val viewModel: MyViewModel by viewModels() // Sử dụng `by viewModels()` để lấy instance của MyViewModel được quản lý bởi Hilt.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding =
            ActivityMainBinding.inflate(layoutInflater) // Sử dụng View Binding để inflate layout.
        setContentView(binding.root) // Thiết lập content view với root của binding.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Xử lý sự kiện khi nút "View Cart" được nhấn.
        binding.viewCartButton.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java) // Tạo Intent để chuyển sang CartActivity.
            startActivity(intent) // Bắt đầu CartActivity.
        }

        // Khởi tạo CategoryAdapter với lambda function xử lý sự kiện click.
        categoryAdapter = CategoryAdapter { categoryName ->
            // Xử lý sự kiện khi một danh mục được nhấp.
            onCategoryClicked(categoryName)
        }

        binding.recyclerView.adapter = categoryAdapter

        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)


        // Lấy danh mục sản phẩm từ ViewModel.
        val results = viewModel.getCategories()
        results.observe(this) { newCategoryList ->
            if (newCategoryList.isNotEmpty()) {
                // Cập nhật dữ liệu cho adapter khi có dữ liệu mới từ ViewModel.
                categoryAdapter.submitList(newCategoryList)
            } else {
                Toast.makeText(this, "No categories found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onCategoryClicked(categoryName: String) {
        // Xử lý sự kiện khi một danh mục được nhấp.
        val intent = Intent(this, CategoryItems::class.java) // Tạo Intent để chuyển sang CategoryItems Activity.
        intent.putExtra("CATEGORY_NAME", categoryName) // Truyền tên danh mục đã chọn qua Intent.
        startActivity(intent) // Bắt đầu CategoryItems Activity.
    }
}