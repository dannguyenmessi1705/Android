package com.didan.jetpack.compose.jetpackbottomnavigation.naviagtion

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.didan.jetpack.compose.jetpackbottomnavigation.screen.HomeScreen
import com.didan.jetpack.compose.jetpackbottomnavigation.screen.ProfileScreen
import com.didan.jetpack.compose.jetpackbottomnavigation.screen.SettingsScreen

@Composable
fun NavGraph(navHostController: NavHostController) {
    // Sử dụng navHostController để điều hướng giữa các màn hình trong NavGraph

    // NavHost: Hoạt động như một container cho các màn hình trong NavGraph. Bạn có thể sử dụng NavHost để xác định các màn hình và cách chúng liên kết với nhau. Ví dụ, bạn có thể sử dụng NavHost để xác định các màn hình Home, Profile và Settings, và cách chúng liên kết với nhau thông qua các route.
    NavHost(
        navController = navHostController, // Sử dụng navHostController để điều hướng giữa các màn hình
        startDestination = NavRoute.Home.path // Đặt màn hình Home làm màn hình bắt đầu của NavGraph, đã được định nghĩa trong NavRoute
    ) {
        addHomeScreen(
            navHostController, // Truyền navHostController vào hàm adddHomeScreen để có thể điều hướng đến màn hình Home khi người dùng chọn tab Home trong BottomNavigationBar
            this // navGraphBuilder là đối tượng được sử dụng để xây dựng NavGraph, bạn có thể sử dụng nó để thêm các màn hình vào NavGraph. Ví dụ, bạn có thể sử dụng navGraphBuilder.composable để thêm mànình Home vào NavGraph. Truyền this để sử dụng navGraphBuilder trong hàm adddHomeScreen
        )

        addProfileScreen(
            navHostController, // Truyền navHostController vào hàm addProfileScreen để có thể điều hướng
            this // navGraphBuilder là đối tượng được sử dụng để xây dựng NavGraph, bạn có thể sử dụng nó để thêm các
        )

        addSettingsScreen(
            navHostController, // Truyền navHostController vào hàm addSettingsScreen để có thể điều hướng
            this // navGraphBuilder là đối tượng được sử dụng để xây dựng NavGraph, bạn có thể sử dụng
        )

    }

}

/**
 * Hàm này sẽ thêm màn hình Home vào NavGraph. Bạn có thể sử dụng navHostController để điều hướng đến màn hình Home khi người dùng chọn tab Home trong BottomNavigationBar.
 */
fun addHomeScreen(navHostController: NavHostController, navGraphBuilder: NavGraphBuilder) {
    // navGraphBuilder là đối tượng được sử dụng để xây dựng NavGraph, bạn có thể sử dụng nó để thêm các màn hình vào NavGraph. Ví dụ, bạn có thể sử dụng navGraphBuilder.composable để thêm màn hình Home vào NavGraph.

    // Định nghĩa route cho màn hình Home
    navGraphBuilder.composable(
        route = NavRoute.Home.path // Tên route cho màn hình Home, đã được định nghĩa trong NavRoute
    ) {
        HomeScreen(
            navigateToProfile = { id, showDetails ->
                navHostController.navigate(
                    NavRoute.Profile.path.plus("/$id/$showDetails") // Điều hướng đến màn hình Profile với các tham số id và showDetails, đã được định nghĩa trong NavRoute.Profile, với các tham số id và showDetails
                )
            },
            navigateToSettings = {
                navHostController.navigate(
                    NavRoute.Settings.path // Điều hướng đến màn hình Settings khi người dùng chọn tab Settings trong BottomNavigationBar
                )
            }
        )
    }
}

fun addProfileScreen(navHostController: NavHostController, navGraphBuilder: NavGraphBuilder) {
    // Định nghĩa route cho màn hình Profile
    navGraphBuilder.composable(
        route = NavRoute.Profile.path.plus("/{id}/{showDetails}"), // Tên route cho màn hình Profile, đã được định nghĩa trong NavRoute, với các tham số id và showDetails

        arguments = listOf(

            navArgument(NavRoute.Profile.id) {
                type = NavType.IntType // Định nghĩa kiểu dữ liệu cho tham số id là Int
            },

            navArgument(NavRoute.Profile.showDetails) {
                type = NavType.BoolType // Định nghĩa kiểu dữ liệu cho tham số showDetails là Boolean
            }
        )
    ) { navBackStackEntry ->
        val args =
            navBackStackEntry.arguments // Lấy các tham số từ NavBackStackEntry, bạn có thể sử dụng args để lấy giá trị của id và showDetails đã được truyền vào khi điều hướng
        ProfileScreen(
            id = args?.getInt(NavRoute.Profile.id)!!, // Lấy giá trị của id từ args và truyền vào màn hình Profile
            showDetails = args.getBoolean(NavRoute.Profile.showDetails), // Lấy giá trị của showDetails từ args và truyền vào màn hình Profile
            navigateToSettings = {
                navHostController.navigate(NavRoute.Settings.path)
            } // Điều hướng đến màn hình Settings khi người dùng chọn tab Settings trong BottomNavigationBar
        )
    }
}

fun addSettingsScreen(navHostController: NavHostController, navGraphBuilder: NavGraphBuilder) {
    // Định nghĩa route cho màn hình Settings
    navGraphBuilder.composable(
        route = NavRoute.Settings.path // Tên route cho màn hình Settings, đã được định nghĩa trong NavRoute
    ) {
        SettingsScreen(
            navigateToHome = {
                navHostController.navigate(NavRoute.Home.path)
            } // Điều hướng đến màn hình Home khi người dùng chọn tab Home trong BottomNavigationBar
        )
    }
}