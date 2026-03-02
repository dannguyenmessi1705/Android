package com.didan.jetpack.compose.jetpackcourseapp.screen

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.didan.jetpack.compose.jetpackcourseapp.R

@Composable
fun BackgroundGradient(modifier: Modifier) {
    Image(
        painter = painterResource(R.drawable.gradient_background),
        contentDescription = "Main background",
        contentScale = ContentScale.FillBounds, // ContentScale.FillBounds có nghĩa là hình ảnh sẽ được mở rộng hoặc thu nhỏ để lấp đầy toàn bộ không gian mà nó được đặt trong, bất kể tỷ lệ gốc của hình ảnh. Điều này có thể dẫn đến việc hình ảnh bị méo nếu tỷ lệ của nó không khớp với tỷ lệ của không gian chứa nó. Tuy nhiên, nó đảm bảo rằng không có phần nào của hình ảnh bị cắt bỏ và toàn bộ không gian được sử dụng để hiển thị hình ảnh.
        modifier = modifier.alpha(0.8f) // Áp dụng hiệu ứng alpha (độ mờ) cho hình ảnh, với giá trị 0.8f có nghĩa là hình ảnh sẽ có độ mờ 80%, giúp tạo ra một hiệu ứng mờ nhẹ cho hình ảnh nền, làm cho nó không quá nổi bật và cho phép các thành phần khác trên giao diện người dùng dễ dàng nhìn thấy hơn.
    )
}