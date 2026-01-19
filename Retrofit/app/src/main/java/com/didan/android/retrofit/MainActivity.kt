package com.didan.android.retrofit

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textView: TextView = findViewById(R.id.textView)

        // Tạo retrofit service để gọi API với interface AlbumService
        val retrofitService = RetrofitInstance
            .getRetrofitInstance() // Lấy thể hiện Retrofit
            .create(AlbumService::class.java) // Tạo thể hiện của AlbumService để gọi các phương thức API

        val responseLiveData: LiveData<Response<Albums>> = liveData {
//            val response =
//                retrofitService.getAlbums() // Gọi phương thức getAlbums() để lấy dữ liệu từ API
            val response2 =
                retrofitService.getSpecificAlbums(6) // Lấy danh sách Album của userId = 6
            emit(response2) // Phát dữ liệu nhận được từ API
        } // Biến LiveData để giữ phản hồi từ API realTime khi có thay đổi

        // Quan sát LiveData để nhận dữ liệu khi có thay đổi
        responseLiveData.observe(this, Observer {
            val albumsList = it.body()?.listIterator() // Lấy danh sách Album từ phản hồi

            // Nếu danh sách không rỗng, duyệt qua từng Album và in thông tin ra console (Logcat)
            if (albumsList != null) {
                while (albumsList.hasNext()) {
                    val albumItem = albumsList.next()
//                    Log.i("TAGY", albumItem.title)
                    val result = " Album Title: ${albumItem.title}\n"
                    textView.append(result)
                }
            }
        })
    }
}