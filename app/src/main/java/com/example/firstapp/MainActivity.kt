package com.example.firstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firstapp.ui.theme.FirstAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        AppScreen()
                        RecipeScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun AppScreen() {
    val navController =
        rememberNavController() // Tạo một navController để điều hướng giữa các man hình
    NavHost(
        navController = navController, // Truyền navController vào NavHost để điều hướng
        startDestination = "firstScreen" // Màn hình mặc định khi chạy ứng dụng sẽ là firstScreen
    ) {
        composable(
            route = "firstScreen" // Định nghĩa route cho màn hình tên là firstScreen
        ) {
            FirstScreen(navigationToSecondScreen = { name ->
                navController.navigate("secondScreen/$name") // Điều hướng tới route secondScreen với tham số name
            })
        }
        composable(
            route = "secondScreen/{name}" // Định nghĩa route cho màn hình tên là secondScreen với tham số name
        ) {
            val name = it.arguments?.getString("name")
                ?: "no name" // Lấy tham số name từ route hoặc mặc định là "no name"
            SecondScreen(
                name,
                navigateToFirstScreen = {
                    navController.navigate("firstScreen")
                }
            )
        }
    }
}