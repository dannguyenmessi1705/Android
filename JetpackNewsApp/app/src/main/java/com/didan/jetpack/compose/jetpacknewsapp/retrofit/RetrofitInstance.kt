package com.didan.jetpack.compose.jetpacknewsapp.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // Base URL for the API
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    // by lazy: Trì hoãn việc khởi tạo cho đến khi api được truy cập lần đầu tiên
    val api: ApiService by lazy {
        // Tạo một instance của Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        // Trả về một instance của ApiService được tạo bởi Retrofit
        retrofit.create(ApiService::class.java)
    }
}