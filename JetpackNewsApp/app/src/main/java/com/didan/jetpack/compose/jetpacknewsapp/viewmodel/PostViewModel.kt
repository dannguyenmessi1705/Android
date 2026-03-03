package com.didan.jetpack.compose.jetpacknewsapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.didan.jetpack.compose.jetpacknewsapp.repository.PostRepository
import com.didan.jetpack.compose.jetpacknewsapp.retrofit.Post
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {

    private val repository = PostRepository()

    // posts là một biến trạng thái (state) được sử dụng để lưu trữ danh sách các bài viết (Post) mà ViewModel sẽ quản lý.
    // Thay vì sử dụng LiveData hoặc StateFlow, ở đây chúng ta sử dụng mutableStateOf để tạo ra một biến trạng thái có thể quan sát được trong Jetpack Compose.
    // mutableStateOf: Đây là một hàm trong Jetpack Compose được sử dụng để tạo ra một biến trạng thái có thể quan sát được. Khi giá trị của biến này thay đổi, các composable đang quan sát nó sẽ tự động được recomposed để phản ánh sự thay đổi đó.
    // by: Đây là một cú pháp trong Kotlin được gọi là "property delegation". Nó cho phép bạn ủy quyền việc quản lý getter và setter của một thuộc tính cho một đối tượng khác. Trong trường hợp này, mutableStateOf sẽ quản lý việc lưu trữ và cập nhật giá trị của posts.
    var posts by mutableStateOf<List<Post>>(emptyList())
        private set // Chỉ cho phép cập nhật giá trị của posts từ bên trong ViewModel, ngăn chặn việc thay đổi trực tiếp từ bên ngoài.

    init {
        viewModelScope.launch {
            // Fetching dữ liệu từ repository và gán nó cho biến posts. Khi posts được cập nhật, các composable đang quan sát nó sẽ tự động được recomposed để hiển thị dữ liệu mới.
            val fetchedPosts = repository.getPosts()

            // Cập nhật biến posts với dữ liệu đã lấy được từ repository. Điều này sẽ kích hoạt việc recomposition của các composable đang quan sát biến posts, giúp hiển thị danh sách bài viết mới trên giao diện người dùng.
            posts = fetchedPosts
        }
    }
}