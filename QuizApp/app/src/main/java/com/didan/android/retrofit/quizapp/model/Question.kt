package com.didan.android.retrofit.quizapp.model

import com.google.gson.annotations.SerializedName

data class Question(
    val question: String,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    @SerializedName("correct_option")
    val correctOption: String
)
