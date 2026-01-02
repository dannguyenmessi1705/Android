package com.didan.android.cardview.sportsapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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

        // 1 - Get the RecyclerView from layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        ) // Tạo LayoutManager cho RecyclerView với hướng dọc, không đảo ngược

        // 2 - Tạo danh sách dữ liệu
        val sportList = ArrayList<SportModel>()
        sportList.add(SportModel(R.drawable.basketball, "BASKETBALL"))
        sportList.add(SportModel(R.drawable.football, "FOOTBALL"))
        sportList.add(SportModel(R.drawable.tennis, "TENNIS"))
        sportList.add(SportModel(R.drawable.ping, "PING PONG"))
        sportList.add(SportModel(R.drawable.volley, "VOLLEYBALL"))

        // 3 - Tạo và thiết lập Adapter cho RecyclerView
        val adapter = SportAdapter(sportList)
        recyclerView.adapter = adapter
    }
}