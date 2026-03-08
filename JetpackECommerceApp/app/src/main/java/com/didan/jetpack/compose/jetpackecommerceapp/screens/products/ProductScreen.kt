package com.didan.jetpack.compose.jetpackecommerceapp.screens.products

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.didan.jetpack.compose.jetpackecommerceapp.model.Product
import com.didan.jetpack.compose.jetpackecommerceapp.screens.navigation.Screen
import com.didan.jetpack.compose.jetpackecommerceapp.viewmodels.CartViewModel
import com.didan.jetpack.compose.jetpackecommerceapp.viewmodels.ProductViewModel

@Composable
fun ProductScreen(
    categoryId: String,
    navHostController: NavHostController,
    productViewModel: ProductViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {

    // Fetch products khi categoryId thay đổi
    LaunchedEffect(categoryId) {
        productViewModel.fetchProducts(categoryId)
    }

    val productsState = productViewModel.products.collectAsState() // collectAsState để convert Flow sang State
    val products = productsState.value

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            "Products of Category ID: $categoryId",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )
        if (products.isEmpty()) {
            Text(
                "No Product Found!",
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(products) {
                    ProductItem(
                        product = it,
                        onClick = { navHostController.navigate(Screen.ProductDetails.createRoute(it.id)) },
                        onAddToCart = {
                            cartViewModel.addToCart(it)
                            Log.v("TAGY", "Product added to cart")
                        })
                }

            }

        }
    }
}