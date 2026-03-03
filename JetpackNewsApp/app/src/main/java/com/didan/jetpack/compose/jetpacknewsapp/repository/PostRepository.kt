package com.didan.jetpack.compose.jetpacknewsapp.repository

import com.didan.jetpack.compose.jetpacknewsapp.retrofit.Post
import com.didan.jetpack.compose.jetpacknewsapp.retrofit.RetrofitInstance

class PostRepository {
    // Repository này sẽ chịu trách nhiệm lấy dữ liệu từ API thông qua Retrofit
    // làm cầu nối giữa ViewModel và RetrofitInstance để tách biệt logic lấy dữ liệu khỏi UI

    private val apiService = RetrofitInstance.api // Lấy instance của ApiService từ RetrofitInstance

    suspend fun getPosts(): List<Post> {
        return apiService.getPosts()
    }
}