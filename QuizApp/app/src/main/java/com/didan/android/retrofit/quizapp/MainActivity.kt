package com.didan.android.retrofit.quizapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.didan.android.retrofit.quizapp.databinding.ActivityMainBinding
import com.didan.android.retrofit.quizapp.model.Question
import com.didan.android.retrofit.quizapp.model.QuestionList
import com.didan.android.retrofit.quizapp.viewmodel.QuizViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var quizViewModel: QuizViewModel
    lateinit var questionList: List<Question>

    companion object {
        var result = 0
        var totalQuestion = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Reset lại điểm số và tổng số câu hỏi khi hoạt động được tạo lại
        result = 0
        totalQuestion = 0

        // Lấy dữ liệu câu hỏi từ ViewModel
        quizViewModel = ViewModelProvider(this)[QuizViewModel::class.java]

        // Hiển thị câu hỏi đầu tiên
        GlobalScope.launch(Dispatchers.Main) {
            quizViewModel.getQuestionsFromLiveData().observe(this@MainActivity, Observer {
                if (it.isNotEmpty()) {
                    questionList = it
                    Log.i("TAGY", "This is the first question: ${questionList[0].question}")
                    binding.apply {
                        txtQuestion.text = questionList[0].question
                        radio1.text = questionList[0].option1
                        radio2.text = questionList[0].option2
                        radio3.text = questionList[0].option3
                        radio4.text = questionList[0].option4
                    }
                }
            })
        }
    }


}