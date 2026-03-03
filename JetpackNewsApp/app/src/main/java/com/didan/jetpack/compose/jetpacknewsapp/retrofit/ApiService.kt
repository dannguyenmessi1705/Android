package com.didan.jetpack.compose.jetpacknewsapp.retrofit

import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    suspend fun getPosts(): List<Post>
}