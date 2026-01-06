package com.didan.android.service.componentserviceapp

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var airplaneModeReceiver: AirplaneModeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val startBtn = findViewById<Button>(R.id.playRingtone)
        val stopBtn = findViewById<Button>(R.id.stopRingtone)

        // Khi người dùng nhấn nút "Play Ringtone", khởi động dịch vụ MyService
        startBtn.setOnClickListener {
            // Dùng Intent để khởi động dịch vụ MyService
            val startServiceIntent = Intent(
                applicationContext, // Sử dụng applicationContext để tránh rò rỉ bộ nhớ
                MyService::class.java // Chỉ định lớp dịch vụ cần khởi động
            )
            // Gọi phương thức startService để bắt đầu dịch vụ
            startService(startServiceIntent)
        }

        // Khi người dùng nhấn nút "Stop Ringtone", dừng dịch vụ MyService
        stopBtn.setOnClickListener {
            // Dùng Intent để dừng dịch vụ MyService
            val stopServiceIntent = Intent(
                applicationContext, // Sử dụng applicationContext để tránh rò rỉ bộ nhớ
                MyService::class.java // Chỉ định lớp dịch vụ cần dừng
            )
            // Gọi phương thức stopService để dừng dịch vụ
            stopService(stopServiceIntent)
        }

        /// Đăng ký BroadcastReceiver tự động để lắng nghe sự kiện thay đổi chế độ máy bay
        // Tạo filter intent để chỉ định loại sự kiện mà chúng ta muốn lắng nghe
        val intentFilter: IntentFilter = IntentFilter("android.intent.action.AIRPLANE_MODE")

        // Tạo một thể hiện của AirplaneModeReceiver
        airplaneModeReceiver = AirplaneModeReceiver()

        // Đăng ký receiver với intent filter đã tạo
        registerReceiver(airplaneModeReceiver, intentFilter)
    }

    override fun onDestroy() {
        // Hủy đăng ký BroadcastReceiver khi Activity bị hủy để tránh rò rỉ bộ nhớ
        unregisterReceiver(airplaneModeReceiver)
        super.onDestroy()
    }
}