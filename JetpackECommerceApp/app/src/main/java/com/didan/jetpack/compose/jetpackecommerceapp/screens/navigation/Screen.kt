package com.didan.jetpack.compose.jetpackecommerceapp.screens.navigation

sealed class Screen(val route: String) {

    object Cart : Screen("Cart")
    object ProductDetails : Screen("product_details/{productId}") {
        fun createRoute(productId: String) = "product_details/$productId"
    }
    object ProductList: Screen("product_list/{categoryId}") {
        fun createRoute(categoryId: String) = "product_list/$categoryId"
    }

    object Products : Screen("Products")
    object Home : Screen("Home")
    object Profile : Screen("Profile")
    object Category : Screen("Category")
    object CategoryList: Screen("category_list")
    object Login: Screen("Login")
    object SignUp: Screen("SignUp")
}