package com.didan.android.lotterynumgenerator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {

    // Khởi tạo các thành phần giao diện
    lateinit var textViewTitle: TextView
    lateinit var textGeneratedNumbers: TextView
    lateinit var shareButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Khởi tạo các thành phần giao diện
        textViewTitle = findViewById(R.id.textView2)
        textGeneratedNumbers = findViewById(R.id.resultTextView)
        shareButton = findViewById(R.id.shareBtn)

        // Tạo random chuỗi
        val randomNumbers = genRandom(6)
        textGeneratedNumbers.text = randomNumbers // Hiển thị chuỗi số ngẫu nhiên lên TextView

        // Lấy tên người dùng từ Intent và hiển thị trên TextView
        var username = receiveUserName()

        // Chia sẻ username & randomNumber qua Email
        shareButton.setOnClickListener {
            shareResult(username, randomNumbers)
        } // Thiết lập sự kiện click cho nút Share

    }

    fun genRandom(count: Int): String {
        var rdNum = List(count) {
            (0..42).random()
        }
        return rdNum.joinToString(" ")
    }

    fun receiveUserName(): String {
        val bundle: Bundle? = intent.extras // Lấy dữ liệu từ Intent (được truyền từ Activity khác)
        val name = bundle?.getString("username") // Lấy tên người dùng có key là "username" (truyền từ MainActivity)
        return name.toString()
    }

    fun shareResult(username: String, generatedNums: String): Unit {
        // Intent khai báo action, hệ thống tìm component phù hợp
        val i = Intent(Intent.ACTION_SEND) // Khởi tạo intent có action là ACTION_SEND
        i.putExtra(Intent.EXTRA_SUBJECT, "$username generates these numbers") // Thêm tiêu đề email
        i.putExtra(Intent.EXTRA_TEXT, "The Lottery Numbers are: $generatedNums") // Thêm nội dung email
        i.setType("text/plain") // Đặt kiểu dữ liệu là text/plain cho intent
        startActivity(i) // Khởi động activity để chia sẻ
    }
}