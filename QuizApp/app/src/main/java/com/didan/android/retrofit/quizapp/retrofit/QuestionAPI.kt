package com.didan.android.retrofit.quizapp.retrofit

import com.didan.android.retrofit.quizapp.model.QuestionList
import retrofit2.Response
import retrofit2.http.GET

interface QuestionAPI {
    @GET("questions.php")
    suspend fun getQuestions(): Response<QuestionList>

}