package com.example.firstapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun CategoryDetailScreen(
    category: Category,
    navigateToCategoryScreen: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = category.strCategory, textAlign = TextAlign.Center)
        Image(
            painter = rememberAsyncImagePainter(model = category.strCategoryThumb),
            contentDescription = "${category.strCategory} Thumbnail",
            modifier = Modifier
                .wrapContentSize() // Chỉnh sửa kích thước của Image theo kích thước của hình ảnh
                .aspectRatio(1f) // Chỉnh sửa tỉ lệ của hình ảnh
        )
        Text(
            text = category.strCategoryDescription,
            textAlign = TextAlign.Justify, // Chỉnh sửa vị trí của Text theo chiều ngang theo kiểu justify
            modifier = Modifier.verticalScroll(rememberScrollState()) // Chỉnh sửa vị trí của Text theo chiều dọc và sử dụng rememberScrollState để lưu trạng thái scroll, chỉ scroll khi nội dung vượt quá kích thước của Text, và scroll theo vùng chứa của Text, không scroll toàn bộ
        )
    }
}