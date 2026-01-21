package com.didan.android.retrofit.quizapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.didan.android.retrofit.quizapp.model.QuestionList
import com.didan.android.retrofit.quizapp.retrofit.QuestionAPI
import com.didan.android.retrofit.quizapp.retrofit.RetrofitInstance
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class QuizRepository {

    var questionAPI: QuestionAPI

    init {
        questionAPI = RetrofitInstance().getRetrofitInstance()
            .create(QuestionAPI::class.java)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getQuestionsFromAPI(): LiveData<QuestionList> {

        // Tạo MutableLiveData để giữ dữ liệu câu hỏi
        val data = MutableLiveData<QuestionList>()

        var questionList: QuestionList

        GlobalScope.launch(Dispatchers.IO) {

            val response = questionAPI.getQuestions()

            if (response.body() != null) {
                questionList = response.body()!!

                data.postValue(questionList) // Post dữ liệu lên LiveData
                Log.i("TAGY", "" + data.value)
            }
        }
        return data
    }
}