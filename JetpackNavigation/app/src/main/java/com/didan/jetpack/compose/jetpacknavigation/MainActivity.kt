package com.didan.jetpack.compose.jetpacknavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.didan.jetpack.compose.jetpacknavigation.ui.theme.JetpackNavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackNavigationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    // 1. NavController: Đây là đối tượng trung tâm để quản lý điều hướng giữa các màn hình
                    val navController =
                        rememberNavController() // rememberNavController() tạo một NavController và đảm bảo rằng nó được nhớ qua các lần tái tạo lại giao diện người dùng.

                    // 2. NavHost: Đây là thành phần chứa các màn hình (composable) mà bạn muốn điều hướng đến. Bạn sẽ định nghĩa các tuyến đường (routes) và các màn hình tương ứng trong NavHost.
                    NavHost(
                        navController = navController, // Truyền NavController vào NavHost để nó có thể quản lý điều hướng
                        startDestination = "first" // Đặt màn hình đầu tiên mà bạn muốn hiển thị khi ứng dụng bắt đầu
                    ) {
                        // Định nghĩa các tuyến đường và các màn hình tương ứng
                        composable("first") {
                            FirstScreen(innerPadding, navController)
                        }

                        composable(
                            route = "second/{userName}?userAge={userAge}", // Định nghĩa tuyến đường với một đối số (userName) được truyền qua đường dẫn, ?userAge={userAge} là một đối số tùy chọn
                            arguments = listOf(
                                // 2. navArgument() được sử dụng để định nghĩa các đối số (arguments) mà bạn muốn truyền giữa các màn hình. Ví dụ: nếu bạn muốn truyền một chuỗi văn bản từ màn hình đầu tiên đến màn hình thứ hai, bạn có thể định nghĩa một đối số như sau:
                                navArgument("userName") {
                                    type =
                                        NavType.StringType // Định nghĩa kiểu dữ liệu của đối số là String
                                },
                                navArgument("userAge") {
                                    type = NavType.StringType // Định nghĩa kiểu dữ liệu của đối số là String
                                    defaultValue = "30" // Đặt giá trị mặc định cho đối số userAge nếu nó không được truyền qua đường dẫn
                                    nullable = true // Cho phép đối số userAge có thể nhận giá trị null nếu nó không được truyền qua đường dẫn
                                }
                            )) { backStackEntry -> // backStackEntry chứa thông tin về tuyến đường hiện tại, bao gồm các đối số đã được truyền qua đường dẫn
                            SecondScreen(
                                innerPadding,
                                navController, // Truyền NavController vào SecondScreen để nó có thể quản lý điều hướng
                                backStackEntry.arguments?.getString("userName").toString(), // Truyền đối số userName đã được lấy từ backStackEntry
                                backStackEntry.arguments?.getString("userAge").toString() // Truyền đối số userAge đã được lấy từ backStackEntry
                            )
                        }
                    }

                }
            }
        }
    }
}

// Định nghĩa màn hình đầu tiên
@Composable
fun FirstScreen(innerPaddingValues: PaddingValues, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPaddingValues)
    ) {
        Text(text = "This is the first screen")

        // Sử dụng remember và mutableStateOf để tạo một biến trạng thái (state) để lưu trữ văn bản đã nhập vào TextField. Biến này sẽ được cập nhật mỗi khi người dùng nhập văn bản mới.
        var enteredText by remember {
            mutableStateOf("")
        }

        TextField(
            value = enteredText,
            onValueChange = {
                enteredText = it
            }, // Cập nhật biến trạng thái khi người dùng nhập văn bản mới
            label = {
                Text(
                    text = "Enter Your Name"
                )
            }
        )

        // Sử dụng remember và mutableStateOf để tạo một biến trạng thái (state) để lưu trữ văn bản đã nhập vào TextField. Biến này sẽ được cập nhật mỗi khi người dùng nhập văn bản mới.
        var enteredText2 by remember {
            mutableStateOf("")
        }

        TextField(
            value = enteredText2,
            onValueChange = {
                enteredText2 = it
            }, // Cập nhật biến trạng thái khi người dùng nhập văn bản mới
            label = {
                Text(
                    text = "Enter Your Age"
                )
            }
        )

        Button(onClick = {
            // 1. Truyền enteredText qua NavController như là một đối số để màn hình thứ hai có thể sử dụng nó. Bạn có thể làm điều này bằng cách thêm enteredText vào đường dẫn của tuyến đường khi điều hướng. Ví dụ: navController.navigate("second/$enteredText")
            navController.navigate("second/$enteredText?userAge=$enteredText2") // Sử dụng NavController để điều hướng đến màn hình thứ hai khi nút được nhấn, ?userAge=$enteredText2 là cách truyền đối số userAge (không bắt buộc) qua đường dẫn cùng với đối số userName (bắt buộc)
        }) {
            Text(text = "Go to second screen")
        }
    }
}

// Định nghĩa màn hình thứ hai
@Composable
fun SecondScreen(
    innerPaddingValues: PaddingValues,
    navController: NavController,
    username: String,
    userage: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPaddingValues)
    ) {
        Text(text = "This is the second screen, welcome $username, your age are $userage") // Hiển thị tên người dùng đã nhập từ màn hình đầu tiên

        Button(onClick = {
            navController.navigateUp() // Sử dụng NavController để điều hướng trở lại màn hình trước đó (first screen) khi nút được nhấn
        }) {
            Text(text = "Go to first screen")
        }
    }
}
