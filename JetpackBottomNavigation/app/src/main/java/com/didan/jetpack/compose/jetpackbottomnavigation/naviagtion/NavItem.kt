package com.didan.jetpack.compose.jetpackbottomnavigation.naviagtion

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings

sealed class NavItem {
    object Home :
        Item(
            NavRoute.Home.path,
            "Home",
            Icons.Default.Home
        ) {} // Đây là một đối tượng (object) trong Kotlin, được sử dụng để định nghĩa một singleton (đối tượng duy nhất) của lớp NavItem. Đối tượng này đại diện cho một item trong ứng dụng của bạn, với đường dẫn (path), tiêu đề (title) và biểu tượng (icon) được xác định. Trong trường hợp này, NavItem.Home đại diện cho một item có đường dẫn là NavRoute.Home.path.toString(), tiêu đề là "Home" và biểu tượng là Icons.Default.Home.

    object Profile :
        Item(
            NavRoute.Profile.path,
            "Profile",
            Icons.Default.Person
        ) {} // Đây là một đối tượng (object) trong Kotlin, được sử dụng để định nghĩa một singleton (đối tượng duy nhất) của lớp NavItem. Đối tượng này đại diện cho một item trong ứng dụng của bạn, với đường dẫn (path), tiêu đề (title) và biểu tượng (icon) được xác định. Trong trường hợp này, NavItem.Profile đại diện cho một item có đường dẫn là NavRoute.Profile.path.toString(), tiêu đề là "Profile" và biểu tượng là Icons.Default.Person.

    object Settings :
        Item(
            NavRoute.Settings.path,
            "Settings",
            Icons.Default.Settings
        ) {} // Đây là một đối tượng (object) trong Kotlin, được sử dụng để định nghĩa một singleton (đối tượng duy nhất) của lớp NavItem. Đối tượng này đại diện cho một item trong ứng dụng của bạn, với đường dẫn (path), tiêu đề (title) và biểu tượng (icon) được xác định. Trong trường hợp này, NavItem.Settings đại diện cho một item có đường dẫn là NavRoute.Settings.path.toString(), tiêu đề là "Settings" và biểu tượng là Icons.Default.Settings.
}