package com.didan.android.coroutines

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.didan.android.coroutines.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private var counter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.countBtn.setOnClickListener {
            binding.counterText.text = counter++.toString()
        }
        binding.downloadBtn.setOnClickListener {
            // Không sử dụng Coroutines sẽ làm block UI Thread (UI bị đơ)
            // downloadBigFileFromNet()

            // Tạo một CoroutineScope với Dispatcher.IO để chạy công việc nặng trên luồng nền
            // Dispatcher.IO được thiết kế để xử lý các công việc I/O nặng như tải file, truy xuất cơ sở dữ liệu,...
            CoroutineScope(Dispatchers.IO).launch {
                downloadBigFileFromNet()
            }

            // Hoặc sử dụng GlobalScope (không khuyến khích sử dụng trong thực tế)
            // GlobalScope là một CoroutineScope toàn cục sống sót trong suốt vòng đời của ứng dụng
//            GlobalScope.launch {
//                downloadBigFileFromNet()
//            }
        }

//        SayHelloFromMainThread()
//        SayHelloFromBackgroundThread()
    }

    private suspend fun downloadBigFileFromNet() {
        for (i in 1..100000) {
            // Giả lập đang tải file lớn
//            Log.i("TAGY", "Downloading file: $i in thread ${Thread.currentThread().name}")

            // Sẽ gặp lỗi nếu cập nhật UI từ luồng nền, đang sử dụng Dispatcher.IO (Chỉ có UI Thread mới được phép cập nhật UI)
//            binding.downloadTextProgress.text = " $i in ${Thread.currentThread().name}"

            // Để cập nhật UI từ luồng nền, cần chuyển về Dispatcher.Main
            // hoặc sử dụng withContext(Dispatchers.Main) để chuyển ngữ cảnh sang luồng chính + thêm "suspend" vào khai báo hàm
            withContext(Dispatchers.Main) {
                binding.downloadTextProgress.text = " $i in ${Thread.currentThread().name}"
            }
        }
    }

    private fun SayHelloFromMainThread() {
        // Dispatchers.Main đại diện cho luồng chính (UI Thread) của ứng dụng Android
        CoroutineScope(Dispatchers.Main).launch {
            binding.counterText.text = "Hello from ${Thread.currentThread().name}"
        }
    }

    private fun SayHelloFromBackgroundThread() {
        // Dispatchers.IO đại diện cho một luồng nền tối ưu cho các công việc I/O nặng
        CoroutineScope(Dispatchers.IO).launch {
            binding.textView2.text = "Hello from ${Thread.currentThread().name}"
        }
    }
}