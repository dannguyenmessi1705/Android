package com.didan.jetpack.compose.jetpackecommerceapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.didan.jetpack.compose.jetpackecommerceapp.model.Product
import com.didan.jetpack.compose.jetpackecommerceapp.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    // Encapsulation: mutable only internally, read-only externally
    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> get() = _product

    fun fetchProductDetails(productId: String) {
        viewModelScope.launch {
            try {
                val product = firebaseRepository.getProductById(productId)
                _product.value = product
            } catch (e: Exception) {
                Log.e("TAGY", "Error fetching product details: ${e.message}")
            }
        }
    }
}