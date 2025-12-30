package com.didan.android.currencyconvertapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Khai báo các biến cho các thành phần giao diện (sử dụng lateinit để tránh việc khởi tạo sớm, chỉ khi được sử dụng)
    lateinit var titleTextView: TextView
    lateinit var resultTextView: TextView
    lateinit var editText: EditText
    lateinit var converterButton: Button

    // Hàm onCreate được gọi khi Activity được tạo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Khởi tạo các thành phần giao diện khi Activity được tạo (bằng cách tìm kiếm chúng trong layout)
        titleTextView = findViewById(R.id.textView) // Lấy TextView từ layout có id là "textView"
        resultTextView = findViewById(R.id.resultText) // Lấy TextView từ layout có id là "resultText"
        editText = findViewById(R.id.editText) // Lấy EditText từ layout có id là "editText"
        converterButton = findViewById(R.id.converterBTN) // Lấy Button từ layout có id là "converterBTN"

        converterButton.setOnClickListener {
            var enteredUSD: String = editText.text.toString()
            var enteredUSDDouble: Double = enteredUSD.toDouble()

            var euros = makeConversion(enteredUSDDouble)

            resultTextView.text = "$euros Euros"
        }


    }

    fun makeConversion(usd: Double): Double {
        val euros: Double = usd * 0.94
        return euros
    }
}