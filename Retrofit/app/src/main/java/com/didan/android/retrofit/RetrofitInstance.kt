package com.didan.android.retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        // Base URL của API
        val BASE_URL = "https://jsonplaceholder.typicode.com"

        // Hàm để tạo và trả về một thể hiện Retrofit
        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL) // Thiết lập URL cơ sở cho các yêu cầu API
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())) // Thêm bộ chuyển đổi Gson để tự động chuyển đổi JSON sang đối tượng Kotlin
                .build() // Xây dựng và trả về thể hiện Retrofit
        }
    }
}