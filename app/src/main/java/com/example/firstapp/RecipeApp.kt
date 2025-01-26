package com.example.firstapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun RecipeApp(navController: NavHostController) {
    val recipeViewModel: MainViewModel = viewModel(); // Lấy dữ liệu từ MainViewModel
    val viewState by recipeViewModel.categoryState // Lấy dữ liệu từ MainViewModel
    NavHost(navController = navController, startDestination = Screen.RecipeScreen.route) {
        composable(route = Screen.RecipeScreen.route) {
            RecipeScreen(viewState = viewState) { category ->
                navController
                    .currentBackStackEntry // Lấy entry hiện tại trong back stack
                    ?.savedStateHandle // Lấy savedStateHandle từ entry
                    ?.set("nav", category)  // Set dữ liệu category vào savedStateHandle
                // Lưu dữ liệu category vào savedStateHandle dùng để truyền dữ liệu giữa màn hình
                navController.navigate(Screen.DetailScreen.route) // Điều hướng sang màn hình DetailScreen
            }
        }
        composable(route = Screen.DetailScreen.route) {
            val category = navController
                .previousBackStackEntry // Lấy entry trước đó trong back stack
                ?.savedStateHandle // Lấy savedStateHandle từ entry
                ?.get<Category>("nav") // Lấy dữ liệu category từ savedStateHandle
                ?: Category("", "", "") // Nếu không có dữ liệu thì trả về một Category rỗng
            // Lấy dữ liệu category từ savedStateHandle dùng để truyền dữ liệu

            CategoryDetailScreen(category = category) {
                navController.popBackStack() // Điều hướng về màn hình trước đó
            }
        }
    }
}