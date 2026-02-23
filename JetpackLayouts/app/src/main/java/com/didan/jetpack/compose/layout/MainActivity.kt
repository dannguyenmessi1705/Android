package com.didan.jetpack.compose.layout

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.didan.jetpack.compose.layout.ui.theme.JetpackLayoutsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackLayoutsTheme {
                MyScaffold() // Sử dụng Scaffold để tạo cấu trúc giao diện người dùng cơ bản
            }
        }
    }
}

@Composable
fun MyRow() {
    Row(
        horizontalArrangement = Arrangement.SpaceAround, // Căn đều các phần tử theo chiều ngang
        verticalAlignment = Alignment.CenterVertically, // Căn giữa theo chiều dọc
        modifier = Modifier.fillMaxSize() // Thuộc tính này giúp Row chiếm toàn bộ không gian có sẵn
    ) {
        Text(text = "Hello")
        Text(text = "World")
    }
}

@Composable
fun MyColumn() {
    // Tương tự như Row nhưng sắp xếp theo chiều dọc
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, // Căn giữa theo chiều ngang
        verticalArrangement = Arrangement.SpaceAround, // Căn đều các phần tử theo chiều dọc
        modifier = Modifier.fillMaxSize() // Thuộc tính này giúp Column chiếm toàn bộ không gian có sẵn (mặc định chỉ chiếm theo độ rộng của nội dung)
    ) {
        Text(text = "Hello")
        Text(text = "World")
    }
}

@Composable
fun MyBox() {
    // Box cho phép xếp chồng các phần tử lên nhau, bạn có thể sử dụng Modifier.align để căn chỉnh từng phần tử trong Box
    Box(
        modifier = Modifier.fillMaxSize() // Thuộc tính này giúp Box chiếm toàn bộ không gian có sẵn

    ) {
        Text(
            text = "Hello World",
            modifier = Modifier.align(Alignment.Center) // Căn giữa nội dung trong Box
        )
        Text(
            text = "Welcome to Jetpack Compose",
            modifier = Modifier.align(Alignment.BottomCenter) // Căn dưới cùng và giữa theo chiều ngang
        )
    }
}

@Composable
fun MySurface() {
    // Surface là một composable đặc biệt được sử dụng để tạo ra một vùng có nền và bóng đổ, thường được sử dụng để tạo ra các phần tử giao diện người dùng như thẻ, nút, v.v.
    // Surface có thể chứa các composable khác bên trong nó và cung cấp các thuộc tính như màu nền, hình dạng, độ bóng, v.v.
    Surface(
        modifier = Modifier.size(100.dp), // Đặt kích thước cho Surface
        color = Color.Red, // Đặt màu nền cho Surface
        contentColor = colorResource(id = R.color.black), // Đặt màu nội dung cho Surface
        shadowElevation = 1.dp, // Đặt độ bóng cho Surface
        border = BorderStroke(1.dp, Color.Green) // Đặt đường viền cho Surface
    ) {
        MyRow()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScaffold() {
    // Scaffold là một composable cung cấp một cấu trúc cơ bản cho giao diện người dùng, bao gồm các phần như TopAppBar, BottomAppBar, FloatingActionButton, v.v.
    Scaffold(
        modifier = Modifier.fillMaxSize(), // Thuộc tính này giúp Scaffold chiếm toàn bộ không gian có sẵn
        topBar = {
            MyTopAppBar()
        }, // Phần trên cùng của Scaffold
        bottomBar = {
            MyBottomAppBar()
        }, // Phần dưới cùng của Scaffold
        floatingActionButton = {
            MyFloatingActionButton()
        } // Nút hành động nổi
    ) {
        // Nội dung chính của Scaffold sẽ được đặt ở đây, innerPadding là khoảng cách giữa nội dung và các phần khác của Scaffold
        MyColumn()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar() {
    val context = LocalContext.current
    TopAppBar(
        title = {
            Text(text = "Top App Bar")
        }, // Tiêu đề của TopAppBar
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Red, // Màu nền của TopAppBar
            titleContentColor = Color.Blue // Màu của tiêu đề trong TopAppBar
        ), // Cấu hình màu sắc cho TopAppBar
        navigationIcon = {
            IconButton(onClick = {
                Toast.makeText(
                    context,
                    "You clicked the navigation icon",
                    Toast.LENGTH_SHORT
                ).show() // Xử lý sự kiện khi biểu tượng điều hướng được nhấn
            }) {
                Icon(
                    painter = painterResource(R.drawable.outline_density_medium_24),
                    contentDescription = "Menu"
                ) // Biểu tượng điều hướng (ví dụ: menu)
            }
        }, // Biểu tượng điều hướng (ví dụ: menu) với sự kiện click
        actions = {
            IconButton(onClick = {
                Toast.makeText(
                    context,
                    "You clicked the Action icon",
                    Toast.LENGTH_SHORT
                ).show() // Xử lý sự kiện khi biểu tượng tìm kiếm được nhấn
            }) {
                Icon(
                    painter = painterResource(R.drawable.outline_build_circle_24),
                    contentDescription = "Settings"
                ) // Biểu tượng hành động (ví dụ: tìm kiếm)
            }
        }
    )
}

@Composable
fun MyBottomAppBar() {
    // BottomAppBar là một composable được sử dụng để tạo ra một thanh công cụ ở phía dưới của màn hình, thường chứa các nút hành động hoặc điều hướng.
    val context = LocalContext.current
    BottomAppBar(
        containerColor = Color.DarkGray, // Màu nền của BottomAppBar
        contentColor = Color.Yellow // Màu nội dung của BottomAppBar
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize() // Đảm bảo Row chiếm toàn bộ chiều rộng của BottomAppBar
        ) {
            IconButton(onClick = {
                Toast.makeText(
                    context,
                    "You clicked the 1st icon",
                    Toast.LENGTH_SHORT
                ).show() // Xử lý sự kiện khi biểu tượng Home được nhấn
            }) {
                Icon(
                    painter = painterResource(R.drawable.outline_add_home_24),
                    contentDescription = "Home"
                ) // Biểu tượng Home
            } // Biểu tượng Home với sự kiện click
            IconButton(onClick = {
                Toast.makeText(
                    context,
                    "You clicked the 2nd icon",
                    Toast.LENGTH_SHORT
                ).show() // Xử lý sự kiện khi biểu tượng Bookmark được nhấn
            }) {
                Icon(
                    painter = painterResource(R.drawable.outline_bookmark_24),
                    contentDescription = "Bookmark"
                ) // Biểu tượng Bookmark
            } // Biểu tượng Bookmark với sự kiện click
        }
    }
}

@Composable
fun MyFloatingActionButton() {
    // FloatingActionButton là một composable được sử dụng để tạo ra một nút hành động nổi, thường được sử dụng để thực hiện một hành động chính trong ứng dụng.
    val context = LocalContext.current
    FloatingActionButton(
        onClick = {
            Toast.makeText(
                context,
                "You clicked the FAB",
                Toast.LENGTH_SHORT
            ).show() // Xử lý sự kiện khi nút hành động nổi được nhấn
        },
        containerColor = Color.Green, // Màu nền của nút
        contentColor = Color.White // Màu nội dung của nút
    ) {
        Icon(
            painter = painterResource(R.drawable.outline_add_24),
            contentDescription = "Add"
        ) // Biểu tượng cho nút hành động nổi (ví dụ: dấu cộng)
    }
}