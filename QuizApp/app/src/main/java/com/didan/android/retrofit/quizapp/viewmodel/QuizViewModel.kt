package com.didan.android.retrofit.quizapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.didan.android.retrofit.quizapp.model.QuestionList
import com.didan.android.retrofit.quizapp.repository.QuizRepository

class QuizViewModel : ViewModel() {
    val repository: QuizRepository = QuizRepository()

    lateinit var questionsLiveData: LiveData<QuestionList>

    init {
        questionsLiveData = repository.getQuestionsFromAPI()
    }

    fun getQuestionsFromLiveData(): LiveData<QuestionList> {
        return questionsLiveData
    }
}