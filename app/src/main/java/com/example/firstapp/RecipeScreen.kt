package com.example.firstapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun RecipeScreen(modifier: Modifier = Modifier) {
    val recipeViewModel: MainViewModel = viewModel(); // Lấy dữ liệu từ MainViewModel
    val viewState by recipeViewModel.categoryState // Lấy dữ liệu từ MainViewModel
    Box(
        modifier = Modifier.fillMaxSize()  // Chỉnh sửa kích thước của Box tối đa
    ) {
        when {
            viewState.loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center) // Chỉnh sửa vị trí của vòng tròn loading ở giữa
                ) // Render một vòng tròn loading
            } // Nếu đang loading thì hiển thị một vòng tròn loading
            viewState.error != null -> {
                Text(text = "Error orccured: ${viewState.error}") // Component Text để hiển thị lỗi
            } // Nếu có lỗi thì hiển thị lỗi
            else -> {
                CategoryScreen(categories = viewState.list)
            } // Nếu không có lỗi thì hiển thị dữ liệu
        }
    } // Box là một container, giống như div trong HTML

}

@Composable
fun CategoryScreen(categories: List<Category>) {
    LazyVerticalGrid(
        GridCells.Fixed(2), // Chia lưới thành 2 cột
        modifier = Modifier.fillMaxSize() // Chỉnh sửa kích thước của LazyVerticalGrid tối đa
    ) {
        items(categories) { category ->
            CategoryItem(category = category) // Render một CategoryItem với dữ liệu từ categories
        }
    } // LazyVerticalGrid là một container, giống như div trong HTML nhưng có thể scroll, và lazy load dữ liệu
}

@Composable
fun CategoryItem(category: Category) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(), // Chỉnh sửa kích thước và padding của Column
        horizontalAlignment = Alignment.CenterHorizontally // Chỉnh sửa vị trí của Column theo chiều ngang
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = category.strCategoryThumb), // Render một hình ảnh từ đường dẫn category.strCategoryThumb, sử dụng rememberAsyncImagePainter để cache hình ảnh
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f) // Chỉnh sửa kích thước của hình ảnh theo tỉ lệ 1:1
        )
        Text(
            text = category.strCategory,
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 4.dp)
        ) // Render một component Text với dữ liệu từ category.strCategory
    }
} // CategoryItem là một component, giống như một component trong React