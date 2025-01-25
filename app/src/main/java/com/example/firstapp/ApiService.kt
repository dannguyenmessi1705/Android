package com.example.firstapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private val retrofit = Retrofit.Builder()
    .baseUrl("https://www.themealdb.com/api/json/v1/1/") // Đường dẫn API gốc, các API khác sẽ được nối vào sau
    .addConverterFactory(GsonConverterFactory.create()) // Chuyển đổi JSON sang Object và ngược lại
    .build() // Tạo ra một instance của Retrofit

val recipeService =
    retrofit.create(ApiService::class.java) // Tạo ra một instance của ApiService, sử dụng để gọi API từ chỗ khác

interface ApiService {
    @GET("categories.php") // API cần gọi, sẽ được nối vào sau đường dẫn API gốc
    suspend fun getCategories(): CategoriesResponse // Hàm gọi API, trả về một CategoriesResponse (đã được chuyển đổi từ JSON sang Object)
}

/*
suspend function là một hàm chạy bất đồng bộ, nghĩa là nó sẽ chạy mà không làm chương trình chờ đợi, giúp chương trình chạy mượt mà hơn
*/