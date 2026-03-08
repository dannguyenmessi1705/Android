package com.didan.jetpack.compose.jetpackecommerceapp.room

import android.util.Log
import android.widget.Toast
import com.didan.jetpack.compose.jetpackecommerceapp.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val cartDao: CartDao
) {
    val allCartItems: Flow<List<Product>> = cartDao.getAllCartItems()

    suspend fun addToCart(product: Product) {
        val existingItem = cartDao.getCartItemById(product.id)
        if (existingItem != null) {
            Log.v("TAGY", "Product already exists in cart, process update")
            cartDao.updateCartItem(product)
        } else {
            cartDao.insertCartItem(product)
            Log.v("TAGY", "Product added to cart")
        }
    }

    suspend fun removeCartItem(product: Product) {
        cartDao.deleteCartItem(product)
        Log.v("TAGY", "Product removed from cart")
    }

    suspend fun clearCart() {
        cartDao.clearCart()
        Log.v("TAGY", "Cart cleared")
    }


}