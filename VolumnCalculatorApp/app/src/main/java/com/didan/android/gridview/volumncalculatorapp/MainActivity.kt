package com.didan.android.gridview.volumncalculatorapp

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

        // Lấy ra GridView từ layout
        val gridView: GridView = findViewById(R.id.gridView)

        // Tạo danh sách các đối tượng Shape để hiển thị trong GridView
        val shape1 = Shape(R.drawable.cube, "Cube")
        val shape2 = Shape(R.drawable.cylinder, "Cylinder")
        val shape3 = Shape(R.drawable.sphere, "Sphere")
        val shape4 = Shape(R.drawable.prism, "Prism")

        val shapeList = listOf(shape1, shape2, shape3, shape4)

        // Tạo một instance của MyCustomAdapter với danh sách các đối tượng Shape
        val adapter = MyCustomAdapter(this, shapeList)
        // Gán adapter cho GridView để hiển thị dữ liệu
        gridView.adapter = adapter

        gridView.setOnItemClickListener { _, _, position, _ ->
            val selectedShape = shapeList[position]
            // Nếu người dùng chọn hình cầu, chuyển đến SphereActivity
            if (selectedShape.shapeName == "Sphere") {
                // Tạo một Intent để chuyển đến SphereActivity
                val intent = Intent(this, SphereActivity::class.java)
                startActivity(intent) // Bắt đầu SphereActivity
            }
        }
    }
}