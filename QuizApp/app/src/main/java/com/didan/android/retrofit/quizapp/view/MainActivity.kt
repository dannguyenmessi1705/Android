package com.didan.android.retrofit.quizapp.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.didan.android.retrofit.quizapp.R
import com.didan.android.retrofit.quizapp.databinding.ActivityMainBinding
import com.didan.android.retrofit.quizapp.model.Question
import com.didan.android.retrofit.quizapp.viewmodel.QuizViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
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

    @OptIn(DelicateCoroutinesApi::class)
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

        // Xử lý khi ấn nút Next
        var i = 1
        binding.apply {
            btnNext.setOnClickListener {
                val selectionOption = radioGroup.checkedRadioButtonId

                // Nếu đã chọn đáp án
                if (selectionOption != -1) {
                    // Tăng biến đếm câu hỏi
                    val radButton = findViewById<View>(selectionOption) as RadioButton

                    // Xử lý kiểm tra đáp án
                    questionList.let {
                        // Kiểm tra nếu vẫn còn câu hỏi
                        if (i < it.size) {
                            // Lấy số lượng câu hỏi
                            totalQuestion = it.size
                            // Nếu đáp án đúng thì tăng điểm
                            if (radButton.text.toString().equals(it[i - 1].correctOption)) {
                                result++
                                txtResult.text = "Correct Answers: $result"
                            }

                            // Hiển thị câu hỏi tiếp theo
                            txtQuestion.text = "Question ${i + 1}: ${questionList[i].question}"
                            radio1.text = it[i].option1
                            radio2.text = it[i].option2
                            radio3.text = it[i].option3
                            radio4.text = it[i].option4

                            // Kiểm tra liệu đã đến câu hỏi cuối cùng
                            if (i == it.size.minus(1)) {
                                btnNext.text = "FINISH"
                            }

                            // Xóa lựa chọn cũ
                            radioGroup.clearCheck()
                            i++ // Tăng biến đếm câu hỏi
                        }
                        // Ngược lại, nếu đã là câu hỏi cuối cùng
                        else {
                            // Nếu đáp án đúng thì tăng điểm
                            if (radButton.text.toString().equals(it[i - 1].correctOption)) {
                                result++
                                txtResult.text = "Correct Answers: $result"
                            } else {

                            }

                            // Chuyển sang màn hình kết quả
                            val intent = Intent(this@MainActivity, ResultActivity::class.java)
                            startActivity(intent)
                            finish() // Kết thúc MainActivity để không thể quay lại
                        }
                    }
                } else { // Nếu chưa chọn đáp án
                    Toast.makeText(
                        this@MainActivity,
                        "Please select an answer",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


}