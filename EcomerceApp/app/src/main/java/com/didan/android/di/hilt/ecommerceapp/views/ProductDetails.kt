package com.didan.android.di.hilt.ecommerceapp.views

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.didan.android.di.hilt.ecommerceapp.R
import com.didan.android.di.hilt.ecommerceapp.databinding.ActivityProductDetailsBinding
import com.didan.android.di.hilt.ecommerceapp.model.Product
import com.didan.android.di.hilt.ecommerceapp.viewmodel.MyViewModel
import dagger.hilt.android.AndroidEntryPoint

// Bắt buộc phải dùng annotation @AndroidEntryPoint trên tất cả các Activity/Fragment sử dụng Hilt để tiêm phụ thuộc.
@AndroidEntryPoint // Annotation @AndroidEntryPoint để Hilt có thể tiêm phụ thuộc vào Activity này, cho phép sử dụng ViewModel được quản lý bởi Hilt.
class ProductDetails : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding
    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Lấy dữ liệu sản phẩm từ Intent và hiển thị trên UI.
        val productTitle = intent.getStringExtra("PRODUCT_TITLE") ?: "N/A"
        val productPrice = intent.getDoubleExtra("PRODUCT_PRICE", 0.0)
        val productImageUrl = intent.getStringExtra("PRODUCT_IMAGE_URL") ?: ""
        val productId = intent.getStringExtra("PRODUCT_ID") ?: "0"

        // Hiển thị dữ liệu sản phẩm trên UI.
        binding.productTitleDetail.text = productTitle
        binding.productPriceDetail.text = "$ $productPrice"

        // Sử dụng Glide để tải và hiển thị hình ảnh sản phẩm.
        Glide.with(this)
            .load(productImageUrl)
            .into(binding.productImageDetail)

        // Xử lý sự kiện khi nút "Add to Cart" được nhấn.
        binding.addToCartButton.setOnClickListener {
            addToCart(Product(productId, productTitle, productPrice, productImageUrl))
        }
    }

    private fun addToCart(product: Product) {
        // Logic thêm sản phẩm vào giỏ hàng (chưa được triển khai trong ví dụ này).
        // Có thể sử dụng ViewModel để quản lý giỏ hàng và cập nhật dữ liệu.
        viewModel.addToCart(product)
        Toast.makeText(
            this,
            "Added ${product.title} to cart",
            Toast.LENGTH_SHORT
        ).show()
    }
}