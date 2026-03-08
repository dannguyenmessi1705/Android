package com.didan.jetpack.compose.jetpackecommerceapp.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.didan.jetpack.compose.jetpackecommerceapp.R
import com.didan.jetpack.compose.jetpackecommerceapp.model.Category
import com.didan.jetpack.compose.jetpackecommerceapp.model.Product
import com.didan.jetpack.compose.jetpackecommerceapp.screens.navigation.Screen
import com.didan.jetpack.compose.jetpackecommerceapp.viewmodels.CategoryViewModel
import com.didan.jetpack.compose.jetpackecommerceapp.viewmodels.ProductViewModel
import com.didan.jetpack.compose.jetpackecommerceapp.viewmodels.SearchViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    onProfileClick: () -> Unit,
    onCartClick: () -> Unit,
    productViewModel: ProductViewModel = hiltViewModel(), // Inject the ViewModel
    categoryViewModel: CategoryViewModel = hiltViewModel(), // Inject the ViewModel
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            MyTopAppBar(onProfileClick, onCartClick)
        },
        bottomBar = { BottomNavigationBar(navHostController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search Section
            val searchQuery = remember { mutableStateOf("") }
            val focusManager =
                LocalFocusManager.current // focusManager dùng để ẩn bàn phím khi người dùng nhấn nút tìm kiếm
            SearchBar(
                query = searchQuery.value,
                onQueryChange = { searchQuery.value = it },
                onSearch = {
                    searchViewModel.searchProducts(searchQuery.value)
                    focusManager.clearFocus()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            // Search Result Section
            if (searchQuery.value.isNotBlank()) {
                SearchResultsSection(navHostController)
            }

            // Categories Section
            SectionTitle("Categories", "See All") {
                navHostController.navigate(Screen.Category.route)
            }

            val categoriesState =
                categoryViewModel.categories.collectAsState() // collectAsState để convert Flow sang State (Compose sử dụng state)
            val categories = categoriesState.value


            val selectedCategory = remember { mutableStateOf(0) }

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories.size) {
                    CategoryChip(
                        icon = categories[it].iconUrl,
                        text = categories[it].name,
                        isSelected = selectedCategory.value == it,
                        onClick = {
                            selectedCategory.value = categories[it].id
                            navHostController.navigate(
                                Screen.ProductList.createRoute(selectedCategory.value.toString())
                            )
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Featured Products Section
            Spacer(modifier = Modifier.height(16.dp))
            SectionTitle("Featured", "See All") {
                navHostController.navigate(Screen.Category.route)
            }

            productViewModel.getAllProductsInFirestore()
            val allProductsState = productViewModel.allProducts.collectAsState()
            val productsList = allProductsState.value


            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(productsList) {
                    FeaturedProductCard(it) {
                        navHostController.navigate(Screen.ProductDetails.createRoute(it.id))
                    }
                }
            }


        }
    }
}