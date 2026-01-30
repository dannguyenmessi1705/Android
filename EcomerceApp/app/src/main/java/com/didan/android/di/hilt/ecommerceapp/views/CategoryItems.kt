package com.didan.android.di.hilt.ecommerceapp.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.didan.android.di.hilt.ecommerceapp.R
import com.didan.android.di.hilt.ecommerceapp.databinding.ActivityCategoryItemsBinding
import com.didan.android.di.hilt.ecommerceapp.databinding.ActivityMainBinding
import com.didan.android.di.hilt.ecommerceapp.model.Product
import com.didan.android.di.hilt.ecommerceapp.util.CategoryAdapter
import com.didan.android.di.hilt.ecommerceapp.util.ProductAdapter
import com.didan.android.di.hilt.ecommerceapp.viewmodel.MyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

// Chịu trách nhiệm hiển thị các mục trong một danh mục cụ thể.
@AndroidEntryPoint // Annotation @AndroidEntryPoint để Hilt có thể tiêm phụ thuộc vào Activity này, cho phép sử dụng ViewModel được quản lý bởi Hilt.
class CategoryItems : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryItemsBinding
    private lateinit var productAdapter: ProductAdapter
    private val viewModel: MyViewModel by viewModels() // Sử dụng `by viewModels()` để lấy instance của MyViewModel được quản lý bởi Hilt.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCategoryItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Khởi tạo ProductAdapter với lambda function xử lý sự kiện click.
        productAdapter = ProductAdapter { productClicked ->
            // Xử lý sự kiện khi một sản phẩm được nhấp.
            onProductClicked(productClicked)
        }

        binding.recyclerViewCategory.adapter = productAdapter

        binding.recyclerViewCategory.layoutManager = LinearLayoutManager(this)

        // Lấy tên danh mục từ Intent (được truyền từ MainActivity).
        val categoryName = intent.getStringExtra("CATEGORY_NAME") ?: ""

        // Lấy tất cả sản phẩm từ ViewModel.
        val results = viewModel.getProducts(categoryName)
        results.observe(this) { newProductList ->
            if (newProductList.isNotEmpty()) {
                // Cập nhật dữ liệu cho adapter khi có dữ liệu mới từ ViewModel.
                productAdapter.submitList(newProductList)
            } else {
                // Xử lý khi không có sản phẩm nào.
                Log.v("DANY1705", "No products found for category: $categoryName")
            }
        }
    }

    private fun onProductClicked(selectedProduct: Product) {
        val intent = Intent(
            this,
            ProductDetails::class.java
        ) // Tạo Intent để chuyển sang ProductDetails Activity.
        intent.putExtra(
            "PRODUCT_TITLE",
            selectedProduct.title
        ) // Truyền tiêu đề sản phẩm qua Intent.
        intent.putExtra("PRODUCT_PRICE", selectedProduct.price) // Truyền giá sản phẩm qua Intent.
        intent.putExtra(
            "PRODUCT_IMAGE_URL",
            selectedProduct.imageUrl
        ) // Truyền URL hình ảnh sản phẩm qua Intent.
        intent.putExtra("PRODUCT_ID", selectedProduct.id) // Truyền ID sản phẩm qua Intent.
        startActivity(intent) // Bắt đầu Activity ProductDetails.
    }
}