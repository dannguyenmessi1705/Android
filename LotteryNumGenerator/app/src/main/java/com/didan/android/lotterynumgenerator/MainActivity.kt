package com.didan.android.lotterynumgenerator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var titleTextView: TextView
    lateinit var editText: EditText
    lateinit var generateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Khởi tạo các thành phần giao diện
        titleTextView = findViewById(R.id.textView)
        editText = findViewById(R.id.editTextName)
        generateButton = findViewById(R.id.generateBtn)

        // Thiết lập sự kiện click cho nút Generate
        generateButton.setOnClickListener {
            val name: String = editText.text.toString() // Lấy tên người dùng từ EditText

            // Intent chỉ rõ màn đích
            val i = Intent(this, SecondActivity::class.java)

            // Truyền dữ liệu vào Intent
            i.putExtra("username", name)

            // Khởi động SecondActivity với Intent
            startActivity(i)
        }


    }
}