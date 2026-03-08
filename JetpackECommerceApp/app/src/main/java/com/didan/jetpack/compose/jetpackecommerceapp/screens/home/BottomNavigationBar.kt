package com.didan.jetpack.compose.jetpackecommerceapp.screens.home

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.didan.jetpack.compose.jetpackecommerceapp.screens.navigation.Screen

@Composable
fun BottomNavigationBar(navHostController: NavHostController) {

    val currentRoute = ""

    val items = listOf(
        BottomNavItem(
            title = "Home",
            icon = Icons.Default.Home,
            route = Screen.Home.route
        ),
        BottomNavItem(
            title = "Categories",
            icon = Icons.Default.Search,
            route = Screen.Cart.route
        ),
        BottomNavItem(
            title = "Wishlist",
            icon = Icons.Default.Favorite,
            route = Screen.Cart.route,
            badgeCount = 5
        ),
        BottomNavItem(
            title = "Cart",
            icon = Icons.Default.ShoppingCart,
            route = Screen.Cart.route,
            badgeCount = 3
        ),
        BottomNavItem(
            title = "Profile",
            icon = Icons.Default.Person,
            route = Screen.Profile.route
        ),
    )

    NavigationBar(
        modifier = Modifier.height(82.dp),
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        val navBackEntry by navHostController.currentBackStackEntryAsState()
        val currentRoute = navBackEntry?.destination?.route
        items.forEach {
            NavigationBarItem(
                icon = {
                    if (it.badgeCount > 0) {
                        BadgedBox(
                            badge = {
                                Badge {
                                    Text(it.badgeCount.toString())
                                }
                            },

                            ) {
                            Icon(
                                imageVector = it.icon,
                                contentDescription = it.title,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    } else {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = it.title,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                label = { Text(it.title) },
                selected = currentRoute == it.route,
                onClick = {
                    navHostController.navigate(it.route) {
                        popUpTo(navHostController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                alwaysShowLabel = true
            )
        }
    }
}

data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: String,
    val badgeCount: Int = 0
)