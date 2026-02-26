package com.didan.jetpack.compose.jetpackbottomnavigation.naviagtion

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * open class: Là một lớp có thể được kế thừa bởi các lớp khác. Nếu một lớp không được đánh dấu là open, nó sẽ không thể được kế thừa. Điều này giúp đảm bảo rằng các lớp không bị thay đổi một cách không mong muốn, và chỉ những lớp được đánh dấu là open mới có thể được mở rộng và tùy chỉnh bởi các lớp con. Trong trường hợp này, Item là một lớp mở (open class) có thể được kế thừa bởi các lớp khác trong ứng dụng của bạn, cho phép bạn tạo ra các loại item khác nhau trong ứng dụng của mình, chẳng hạn như các item trong BottomNavigationBar hoặc các item trong danh sách.
 */
open class Item(
    val path: String,
    val title: String,
    val icon: ImageVector
)