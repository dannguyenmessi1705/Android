package com.didan.android.di.hilt.ecommerceapp.views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.didan.android.di.hilt.ecommerceapp.R
import com.didan.android.di.hilt.ecommerceapp.databinding.ActivityCartBinding
import com.didan.android.di.hilt.ecommerceapp.databinding.ActivityCategoryItemsBinding
import com.didan.android.di.hilt.ecommerceapp.model.Product
import com.didan.android.di.hilt.ecommerceapp.util.CartAdapter
import com.didan.android.di.hilt.ecommerceapp.util.CategoryAdapter
import com.didan.android.di.hilt.ecommerceapp.util.ProductAdapter
import com.didan.android.di.hilt.ecommerceapp.viewmodel.MyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint // Bắt buộc có Annotation @AndroidEntryPoint để Hilt có thể tiêm phụ thuộc vào Activity này, cho phép sử dụng ViewModel được quản lý bởi Hilt.
class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter
    private val viewModel: MyViewModel by viewModels() // Sử dụng `by viewModels()` để lấy instance của MyViewModel được quản lý bởi Hilt.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Khởi tạo CartAdapter với lambda function xử lý sự kiện click.
        cartAdapter = CartAdapter { cartItem ->
            // Xử lý sự kiện khi một mục trong giỏ hàng được nhấp.
            removeCartItem(cartItem)
        }

        // Áp dụng hàng loạt các thuộc tính chọ binding.recyclerViewCart
        binding.recyclerViewCart.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(this@CartActivity)
        }

        // Xử lý sự kiện khi nút "Clear Cart" được nhấn.
        binding.clearCartButton.setOnClickListener {
            viewModel.clearCart()
            cartAdapter.submitList(emptyList()) // Cập nhật danh sách trong adapter thành rỗng.
        }

        // Xử lý sự kiện khi nút "Check Out" được nhấn.
        binding.checkOutButton.setOnClickListener {
            checkOutCart()
        }

        // Lấy danh sách sản phẩm trong giỏ hàng từ ViewModel.
        viewModel.getCartProducts().observe(this) { cartItems ->
            cartAdapter.submitList(cartItems) // Cập nhật danh sách trong adapter khi có dữ liệu mới.
        }
    }

    // Hàm để xử lý việc xóa một mục khỏi giỏ hàng.
    private fun removeCartItem(cartItem: Product) {
        viewModel.deleteCartProduct(cartItem.id)

        val updatedCartItems = viewModel.getCartProducts()
            .value?.toMutableList() // Lấy danh sách hiện tại và chuyển đổi sang MutableList để có thể sửa đổi.

        cartAdapter.submitList(updatedCartItems) // Cập nhật danh sách trong adapter.
    }

    // Hàm để xử lý việc thanh toán giỏ hàng (Upload đơn hàng thanh toán lên Firestore)
    private fun checkOutCart() {
        // Lấy danh sách sản phẩm trong giỏ hàng hiện tại
        viewModel.getCartProducts().observe(this) { purchasedItems ->
            for (item in purchasedItems) {
                // Gọi hàm trong ViewModel để lưu sản phẩm đã mua lên Firestore
                viewModel.savePurchaseInFirestore(item)
            }
            cartAdapter.submitList(emptyList()) // Cập nhật danh sách trong adapter thành rỗng.
        }
    }
}