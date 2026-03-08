package com.didan.jetpack.compose.jetpackecommerceapp.screens.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import com.didan.jetpack.compose.jetpackecommerceapp.viewmodels.CartViewModel
import com.didan.jetpack.compose.jetpackecommerceapp.viewmodels.ProductDetailsViewModel

@Composable
fun ProductDetailsScreen(
    productId: String,
    productDetailsViewModel: ProductDetailsViewModel = hiltViewModel(), // Auto Inject from hilt
    cartViewModel: CartViewModel = hiltViewModel()
) {

    // Fetch product details khi màn hình được khởi tạo lần đầu (khi tham số trong LaunchedEffect productId thay đổi)
    LaunchedEffect(productId) {
        productDetailsViewModel.fetchProductDetails(productId)
    }

    val productState = productDetailsViewModel.product.collectAsState() // collectAsState để convert Flow sang State (Compose sử dụng state)
    val product = productState.value

    if (product == null) {
        Text("Product Not Found")
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(product.imageUrl),
                contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                product.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "$${product.price}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                product.categoryId ?: "No Description Found",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        IconButton(
            onClick = {
                cartViewModel.addToCart(product)
            },
            modifier = Modifier
                .padding(16.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary, shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Add to Cart",
                tint = Color.White
            )
        }
    }
}