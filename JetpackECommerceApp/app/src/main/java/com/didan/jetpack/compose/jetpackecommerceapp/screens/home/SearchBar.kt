package com.didan.jetpack.compose.jetpackecommerceapp.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.didan.jetpack.compose.jetpackecommerceapp.screens.navigation.Screen
import com.didan.jetpack.compose.jetpackecommerceapp.screens.products.ProductItem
import com.didan.jetpack.compose.jetpackecommerceapp.viewmodels.CartViewModel
import com.didan.jetpack.compose.jetpackecommerceapp.viewmodels.SearchViewModel

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(50.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(Color.LightGray.copy(alpha = 0.2f)),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Search, "Search button")
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        "Search products...",
                        color = Color.Gray, fontSize = 16.sp
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search // imeAction là chế độ nhập liệu mặc định trong TextField, ở đây là chế độ tìm kiếm
                ),
                keyboardActions = KeyboardActions(
                    onSearch = { onSearch() } // thực hiện hành động tìm kiếm khi người dùng nhấn nút tìm kiếm trên bàn phím
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent, // Set màu nền cho TextField khi được focus
                    unfocusedLabelColor = Color.Transparent, // Set màu nhãn cho TextField khi không được focus
                    unfocusedContainerColor = Color.Transparent, // Set màu nền cho TextField khi không được focus
                    focusedIndicatorColor = Color.Transparent, // Set màu viền cho TextField khi được focus
                    disabledContainerColor = Color.Transparent, // Set màu nền cho TextField khi bị vô hiệu hóa
                    unfocusedIndicatorColor = Color.Transparent, // Set màu viền cho TextField khi không được focus
                    disabledIndicatorColor = Color.Transparent // Set màu viền cho TextField khi bị vô hiệu hóa
                )
            )
        }

    }
}

@Composable
fun SearchResultsSection(
    navHostController: NavHostController,
    searchViewModel: SearchViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val searchResults = searchViewModel.searchResults.collectAsState().value
    val isSearching = searchViewModel.isSearching.collectAsState().value

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "Search Results",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        )

        if (isSearching) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(searchResults.size) {
                    val product = searchResults[it]
                    ProductItem(
                        product,
                        onClick = {
                            navHostController.navigate(
                                Screen.ProductDetails.createRoute(
                                    product.id
                                )
                            )
                        },
                        onAddToCart = { cartViewModel.addToCart(product) })
                }
            }
        }
    }
}