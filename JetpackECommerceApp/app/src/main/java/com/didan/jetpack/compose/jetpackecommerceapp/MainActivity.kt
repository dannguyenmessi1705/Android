package com.didan.jetpack.compose.jetpackecommerceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.didan.jetpack.compose.jetpackecommerceapp.screens.cart.CartScreen
import com.didan.jetpack.compose.jetpackecommerceapp.screens.categories.CategoryScreen
import com.didan.jetpack.compose.jetpackecommerceapp.screens.home.HomeScreen
import com.didan.jetpack.compose.jetpackecommerceapp.screens.navigation.Screen
import com.didan.jetpack.compose.jetpackecommerceapp.screens.products.ProductDetailsScreen
import com.didan.jetpack.compose.jetpackecommerceapp.screens.products.ProductScreen
import com.didan.jetpack.compose.jetpackecommerceapp.screens.profile.LoginScreen
import com.didan.jetpack.compose.jetpackecommerceapp.screens.profile.ProfileScreen
import com.didan.jetpack.compose.jetpackecommerceapp.screens.profile.SignUpScreen
import com.didan.jetpack.compose.jetpackecommerceapp.ui.theme.JetpackECommerceAppTheme
import com.didan.jetpack.compose.jetpackecommerceapp.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navHostController = rememberNavController()
            val authViewModel: AuthViewModel = hiltViewModel()

            val isLoggedIn by remember {
                derivedStateOf {
                    authViewModel.isLoggedIn
                }
            }

            JetpackECommerceAppTheme {
                NavHost(
                    navController = navHostController,
                    startDestination = Screen.Home.route
                ) {
                    composable(Screen.Home.route) {
                        HomeScreen(
                            navHostController,
                            onProfileClick = { navHostController.navigate(Screen.Profile.route) },
                            onCartClick = { navHostController.navigate(Screen.Cart.route) }
                        )
                    }
                    composable(Screen.Cart.route) {
                        CartScreen(navHostController)
                    }
                    composable(Screen.Profile.route) {
                        ProfileScreen(navHostController, onSignOut = {
                            authViewModel.signOut()
                            navHostController.navigate(Screen.Login.route)
                        })
                    }
                    composable(Screen.Category.route) {
                        CategoryScreen(
                            navHostController,
                            onCartClick = {
                                navHostController.navigate(Screen.Cart.route)
                            },
                            onProfileClick = {
                                navHostController.navigate(Screen.Profile.route)
                            }
                        )
                    }
                    composable(Screen.ProductDetails.route) {
                        val productId = it.arguments?.getString("productId")
                        if (productId != null) {
                            ProductDetailsScreen(productId)
                        }
                    }
                    composable(Screen.ProductList.route) {
                        val categoryId = it.arguments?.getString("categoryId")
                        if (categoryId != null) {
                            ProductScreen(categoryId, navHostController)
                        }
                    }
                    composable(Screen.SignUp.route) {
                        SignUpScreen(
                            onNavigationToLogin = {
                                navHostController.navigate(Screen.Login.route)
                            },
                            onSignUpSuccess = {
                                navHostController.navigate(Screen.Home.route)
                            }
                        )
                    }
                    composable(Screen.Login.route) {
                        LoginScreen(
                            onNavigationToSignUp = {
                                navHostController.navigate(Screen.SignUp.route)
                            },
                            onLoginSuccess = {
                                navHostController.navigate(Screen.Category.route)
                            }
                        )
                    }
                }
            }
        }
    }
}