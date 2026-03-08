package com.didan.jetpack.compose.jetpackecommerceapp.screens.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.didan.jetpack.compose.jetpackecommerceapp.model.Category
import com.didan.jetpack.compose.jetpackecommerceapp.screens.navigation.Screen
import com.didan.jetpack.compose.jetpackecommerceapp.viewmodels.CategoryViewModel

@Composable
fun CategoryScreen(
    navHostController: NavHostController,
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    onCartClick: () -> Unit,
    onProfileClick: () -> Unit,
) {
    val categoriesState = categoryViewModel.categories.collectAsState() // collectAsState để convert Flow sang State
    val categories = categoriesState.value

    Column() {
        if (categories.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "No Categories Found",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            Text(
                "Categories",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                items(categories) {
                    CategoryItem(
                        category = it,
                        onClick = {
                            navHostController.navigate(Screen.ProductList.createRoute(it.id.toString()))
                        }
                    )
                }
            }
        }
    }
}