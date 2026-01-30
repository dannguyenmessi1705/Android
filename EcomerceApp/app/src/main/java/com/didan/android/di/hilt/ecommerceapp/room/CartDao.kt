package com.didan.android.di.hilt.ecommerceapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.didan.android.di.hilt.ecommerceapp.model.Product

// Định nghĩa các phương thức truy cập cơ sở dữ liệu cho bảng giỏ hàng.
@Dao
interface CartDao {

    @Insert
    suspend fun addToCart(cartItem: Product)

    @Query("SELECT * FROM cart_items")
    suspend fun getCartItems(): List<Product>

    @Query("DELETE FROM cart_items WHERE id = :productId")
    suspend fun removeFromCart(productId: String)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
}