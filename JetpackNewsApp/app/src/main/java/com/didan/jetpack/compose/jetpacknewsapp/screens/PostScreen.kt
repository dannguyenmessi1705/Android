package com.didan.jetpack.compose.jetpacknewsapp.screens

import androidx.compose.runtime.Composable
import com.didan.jetpack.compose.jetpacknewsapp.viewmodel.PostViewModel

@Composable
fun PostScreen(viewModel: PostViewModel) {
    // KHông cần sử dụng observableAsState() cho Jetpack Compose nữa vì chúng ta không sử dụng LiveData hoặc StateFlow để quản lý trạng thái.
    // Thay vào đó, chúng ta đã sử dụng mutableStateOf trong ViewModel để tạo ra một biến trạng thái có thể quan sát được. Khi giá trị của biến này thay đổi, các composable đang quan sát nó sẽ tự động được recomposed để phản ánh sự thay đổi đó.
    // Bất kỳ sự thay đổi nào đối với biến posts trong ViewModel sẽ tự động kích hoạt việc recomposition của các composable đang quan sát nó, giúp hiển thị dữ liệu mới trên giao diện người dùng mà không cần phải sử dụng observableAsState() để chuyển đổi LiveData hoặc StateFlow thành một biến trạng thái có thể quan sát được trong Jetpack Compose.
    val posts = viewModel.posts
    PostList(posts)
}