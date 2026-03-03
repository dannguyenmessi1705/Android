package com.didan.jetpack.compose.jetpacknewsapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.didan.jetpack.compose.jetpacknewsapp.screens.PostScreen
import com.didan.jetpack.compose.jetpacknewsapp.ui.theme.JetpackNewsAppTheme
import com.didan.jetpack.compose.jetpacknewsapp.viewmodel.PostViewModel

class MainActivity : ComponentActivity() {

    // ViewModel, sử dụng by viewModels() để tạo instance của PostViewModel và quản lý vòng đời của nó một cách tự động. Điều này giúp đảm bảo rằng ViewModel sẽ tồn tại qua các thay đổi cấu hình như xoay màn hình và sẽ được hủy khi Activity bị hủy, giúp tránh rò rỉ bộ nhớ và đảm bảo rằng dữ liệu trong ViewModel được giữ nguyên khi cấu hình thay đổi.
    // Ngắn gọn hơn thay vì sử dụng ViewModelProvider để tạo instance của ViewModel, chúng ta có thể sử dụng by viewModels() để tự động tạo và quản lý vòng đời của ViewModel một cách dễ dàng và hiệu quả hơn trong Activity.
    private val myViewModel: PostViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackNewsAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        HeaderComposable()
                        PostScreen(myViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderComposable() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "The News App",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Get the latest Posts & News",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
    }
}