package com.didan.android.retrofit.quizapp.retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    val baseUrl = "http://localhost:80/quiz/"

    fun getRetrofitInstance(): Retrofit {
        // Implementation for creating and returning a Retrofit instance
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }
}