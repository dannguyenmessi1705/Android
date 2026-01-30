package com.didan.android.di.hilt.ecommerceapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.didan.android.di.hilt.ecommerceapp.model.Category
import com.didan.android.di.hilt.ecommerceapp.model.Product
import com.didan.android.di.hilt.ecommerceapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Lớp ViewModel quản lý dữ liệu và logic cho UI.
 * Được sử dụng để tách biệt logic kinh doanh khỏi UI, giúp code dễ bảo trì và kiểm thử hơn.
 * @HiltViewModel annotation đánh dấu lớp này là một ViewModel được quản lý bởi Hilt, cho phép tiêm các dependencies vào ViewModel này.
 * @param repository Repository được tiêm vào để truy cập dữ liệu.
 */
@HiltViewModel // Annotation @HiltViewModel đánh dấu lớp này là một ViewModel được quản lý bởi Hilt, cho phép tiêm các dependencies vào ViewModel này.
class MyViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    // Hàm lấy danh mục sản phẩm từ Repository.
    fun getCategories(): MutableLiveData<List<Category>> {
        return repository.fetchCategories()
    }

    // Hàm lấy sản phẩm theo danh mục từ Repository.
    fun getProducts(categoryName: String): MutableLiveData<List<Product>> {
        return repository.fetchProducts(categoryName)
    }

    // Hàm thêm sản phẩm vào giỏ hàng thông qua Repository, viewModelScope đảm bảo coroutine được hủy khi ViewModel bị hủy.
    fun addToCart(product: Product) = viewModelScope.launch {
        repository.addProductToCart(product)
    }

    // Hàm lấy tất cả sản phẩm trong giỏ hàng từ Repository.
    fun getCartProducts(): MutableLiveData<List<Product>> {
        val cartItems = MutableLiveData<List<Product>>()
        viewModelScope.launch {
            val items = repository.getAllCartItems()
            cartItems.postValue(items)
        }
        return cartItems
    }

    // Hàm xóa sản phẩm khỏi giỏ hàng thông qua Repository.
    fun deleteCartProduct(productId: String) = viewModelScope.launch {
        repository.removeProductFromCart(productId)
    }

    // Hàm xóa tất cả sản phẩm khỏi giỏ hàng thông qua Repository.
    fun clearCart() = viewModelScope.launch {
        repository.clearCart()
    }

    // Hàm upload sản phẩm đã mua lên Firestore
    fun savePurchaseInFirestore(product: Product) = viewModelScope.launch {
        repository.savePurchaseInFirestore(product)
    }
}