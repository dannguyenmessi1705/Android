package com.didan.jetpack.compose.jetpacklist

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.didan.jetpack.compose.jetpacklist.ui.theme.JetpackListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    ScrollingColumn()
//                    ScrollingRow()
                    MyLazyColumn()
//                    MyLazyRow()
                }
            }
        }
    }
}

@Composable
fun ScrollingColumn() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()) // Cho phép cuộn dọc, rememberScrollState() để lưu trạng thái cuộn
    ) {
        Image(
            painter = painterResource(R.drawable.pic1),
            contentDescription = "Picture 1",
            contentScale = ContentScale.FillBounds // FillBounds: Làm đầy toàn bộ không gian của Image, có thể làm biến dạng hình ảnh nếu tỷ lệ không khớp.
        )

        Image(
            painter = painterResource(R.drawable.pic2),
            contentDescription = "Picture 2",
            contentScale = ContentScale.FillBounds // FillBounds: Làm đầy toàn bộ không gian của Image, có thể làm biến dạng hình ảnh nếu tỷ lệ không khớp.
        )

        Image(
            painter = painterResource(R.drawable.pic3),
            contentDescription = "Picture 3",
            contentScale = ContentScale.FillBounds // FillBounds: Làm đầy toàn bộ không gian của Image, có thể làm biến dạng hình ảnh nếu tỷ lệ không khớp.
        )
    }
}

@Composable
fun ScrollingRow() {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState()) // Cho phép cuộn ngang, rememberScrollState() để lưu trạng thái cuộn
    ) {
        Image(
            painter = painterResource(R.drawable.pic1),
            contentDescription = "Picture 1",
            contentScale = ContentScale.FillBounds // FillBounds: Làm đầy toàn bộ không gian của Image, có thể làm biến dạng hình ảnh nếu tỷ lệ không khớp.
        )

        Image(
            painter = painterResource(R.drawable.pic2),
            contentDescription = "Picture 2",
            contentScale = ContentScale.FillBounds // FillBounds: Làm đầy toàn bộ không gian của Image, có thể làm biến dạng hình ảnh nếu tỷ lệ không khớp.
        )

        Image(
            painter = painterResource(R.drawable.pic3),
            contentDescription = "Picture 3",
            contentScale = ContentScale.FillBounds // FillBounds: Làm đầy toàn bộ không gian của Image, có thể làm biến dạng hình ảnh nếu tỷ lệ không khớp.
        )
    }
}

@Composable
fun MyLazyColumn() {
    val itemsList = listOf<String>(
        "Item 1",
        "Item 2",
        "Item 3",
        "Item 4",
        "Item 5",
        "Item 6",
        "Item 7",
        "Item 8",
        "Item 9",
        "Item 10",
        "Item 11",
        "Item 12",
        "Item 13",
        "Item 14",
        "Item 15",
        "Item 16"
    )

    // Sử dụng LazyColumn để hiển thị danh sách các item, chỉ tạo những item cần thiết khi cuộn đến chúng
    LazyColumn {
        stickyHeader {
            Text(
                text = "Sticky Header",
                fontSize = 42.sp,
                modifier = Modifier.background(Color.Blue)
            )
        } // stickyHeader() là một hàm tiện ích của LazyColumn để tạo một header cố định ở đầu danh sách, nó sẽ luôn hiển thị khi cuộn qua các item khác
        items(itemsList) { item ->
            MyCustomItem(item)
        } // items() là một hàm tiện ích của LazyColumn để tạo các item từ một danh sách, item là phần tử hiện tại trong danh sách được truyền vào lambda
    }
}

@Composable
fun MyCustomItem(itemTitle: String) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .padding(8.dp) // Thêm khoảng cách xung quanh mỗi item để tránh bị dính sát vào nhau
            .fillMaxWidth() // Đảm bảo mỗi item chiếm toàn bộ chiều rộng của màn hình,
//            .clickable {
//                Toast.makeText(
//                    context,
//                    "Clicked on $itemTitle",
//                    Toast.LENGTH_SHORT
//                ).show()
//            } // Thêm sự kiện click cho mỗi item, khi người dùng nhấn vào item sẽ hiển thị một Toast với tên của item đó (Tuy nhiên, nên dùng pointerInput đặt ở các item để tránh việc click vào header cũng kích hoạt Toast)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = itemTitle,
            fontSize = 42.sp,
            modifier = Modifier
                .background(Color.Green)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            Toast.makeText(
                                context,
                                "Long Pressed on $itemTitle",
                                Toast.LENGTH_SHORT
                            ).show()
                        }, // Thêm sự kiện long press cho mỗi item, khi người dùng nhấn giữ vào item sẽ hiển thị một Toast với tên của item đó

                        onTap = {
                            Toast.makeText(
                                context,
                                "Clicked on $itemTitle",
                                Toast.LENGTH_SHORT
                            ).show()
                        } // Thêm sự kiện click cho mỗi item, khi người dùng nhấn vào item sẽ hiển thị một Toast với tên của item đó
                    ) // detectTapGestures() là một hàm tiện ích của pointerInput để phát hiện các cử chỉ chạm, trong đó onLongPress được sử dụng để xử lý sự kiện nhấn giữ (long press) trên item
                }
        )
    }
}

@Composable
fun MyLazyRow() {
    val itemList = listOf<String>(
        "Item 1",
        "Item 2",
        "Item 3",
        "Item 4",
        "Item 5",
        "Item 6",
        "Item 7",
        "Item 8",
        "Item 9",
        "Item 10",
        "Item 11",
        "Item 12",
        "Item 13",
        "Item 14",
        "Item 15",
        "Item 16"
    )

    LazyRow {
        items(itemList) { item ->
            MyCustomItem(item)
        }
    }
}

@Composable
fun MyCard() {
    // Card là một thành phần giao diện người dùng trong Jetpack Compose, thường được sử dụng để hiển thị thông tin trong một khối có viền và bóng đổ, giúp tạo ra một giao diện đẹp mắt và dễ đọc. Card thường được sử dụng để nhóm các thành phần liên quan lại với nhau, như hình ảnh, tiêu đề, mô tả, v.v.
    Card(
        modifier = Modifier
            .fillMaxWidth() // Đảm bảo Card chiếm toàn bộ chiều rộng của màn hình
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp), // Thêm hiệu ứng bóng đổ cho Card để tạo cảm giác nổi bật hơn trên giao diện
        colors = CardDefaults.cardColors(Color.Yellow) // Đặt màu nền cho Card, trong trường hợp này là màu vàng để làm nổi bật nội dung bên trong Card
    ) {
        Text(
            text = "This is a simple card",
            modifier = Modifier.padding(16.dp) // Thêm khoảng cách xung quanh nội dung của Card để tránh bị dính sát vào viền của Card, giúp nội dung dễ đọc hơn
        )
    }
}