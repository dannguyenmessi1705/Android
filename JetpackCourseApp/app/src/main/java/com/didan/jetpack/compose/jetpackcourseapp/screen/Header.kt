package com.didan.jetpack.compose.jetpackcourseapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.didan.jetpack.compose.jetpackcourseapp.R

@Composable
fun ProfileImage(modifier: Modifier) {
    Image(
        painter = painterResource(R.drawable.profile),
        contentDescription = "Profile",
        modifier = modifier
            .padding(start = 16.dp)
            .clip(CircleShape) // Áp dụng hình dạng tròn cho hình ảnh bằng cách sử dụng CircleShape, điều này sẽ làm cho hình ảnh có dạng tròn, thường được sử dụng để hiển thị ảnh đại diện hoặc ảnh hồ sơ.
            .size(42.dp) // Đặt kích thước của hình ảnh thành 42dp x 42dp, điều này đảm bảo rằng hình ảnh sẽ có kích thước nhỏ gọn và phù hợp để hiển thị như một ảnh đại diện trong giao diện người dùng.
    )
}

@Composable
fun NotificationImg(modifier: Modifier) {
    Image(
        painter = painterResource(R.drawable.notification),
        contentDescription = "Notification",
        modifier = modifier
            .padding(end = 16.dp)
            .clip(CircleShape)
            .size(42.dp)

    )
}