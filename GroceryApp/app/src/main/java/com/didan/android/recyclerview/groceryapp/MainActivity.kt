package com.didan.android.recyclerview.groceryapp

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

        // 1 AdapterView: RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(
            this, // Sử dụng context của Activity hiện tại
            LinearLayoutManager.VERTICAL, // Thiết lập hướng dọc cho RecyclerView
            false // Không đảo ngược thứ tự hiển thị các mục
        )

        // 2 Data Source: ArrayList<ItemModel> Gán dữ liệu cho RecyclerView
        val v1 = ItemModel("Fruits", "Fresh Fruits from the Garden", R.drawable.fruit)
        val v2 = ItemModel("Vegetables", "Healthy and Green Vegetables", R.drawable.vegitables)
        val v3 = ItemModel("Bakery", "Freshly Baked Bread and Pastries", R.drawable.bread)
        val v4 = ItemModel("Beverage", "Refreshing Drinks and Juices", R.drawable.beverage)
        val v5 = ItemModel("Milk", "Dairy Products and Milk", R.drawable.milk)
        val v6 = ItemModel("Snacks", "Tasty Snacks for Every Occasion", R.drawable.popcorn)

        val groceryItems = ArrayList<ItemModel>()
        groceryItems.add(v1)
        groceryItems.add(v2)
        groceryItems.add(v3)
        groceryItems.add(v4)
        groceryItems.add(v5)
        groceryItems.add(v6)

        // 3 Adapter: MyCustomAdapter
        val myAdapter = MyCustomAdapter(groceryItems)
        recyclerView.adapter = myAdapter
    }
}