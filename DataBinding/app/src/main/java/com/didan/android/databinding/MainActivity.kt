package com.didan.android.databinding

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.didan.android.databinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Khai báo biến binding vào layout có tên là "activity_main.xml"
    lateinit var binding: ActivityMainBinding // class được tự động sinh ra theo tên file layout + "Binding"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_main) // Không sử dụng setContentView thông thường nữa (vì đã dùng Data Binding)
        binding = DataBindingUtil.setContentView(
            this, // Activity hiện tại
            R.layout.activity_main // Layout để binding
        ) // Sử dụng Data Binding để thay thế cho setContentView
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Sử dụng findViewById để tham chiếu đến TextView và thay đổi văn bản của nó
//        var textView: TextView = findViewById(R.id.textView1)
//        textView.text = "Hello Data Binding"

        // Sử dụng binding để tham chiếu đến TextView và thay đổi văn bản của nó
        var v1 = Vehicle("2025", "Mercedes-Benz")
//        binding.textView1.text = "Hello Data Binding" // Cách thông thường (lấy id của TextView từ binding)
        binding.myVehicle =
            v1 // Gán giá trị cho biến myVehicle đã định nghĩa trong layout thông qua binding

        // Thiết lập sự kiện click cho nút button sử dụng Data Binding cách thông thường (lấy id của Button từ binding)
//        binding.button.setOnClickListener {
//            Toast.makeText(
//                this,
//                "You Clicked the Button!",
//                Toast.LENGTH_SHORT
//            ).show()
//        }

        var v2wayBinding = TwoBindingVehicle()
        v2wayBinding.modelYear = "2024"
        v2wayBinding.name = "Audi A8"
        // Gán giá trị cho biến twoBindingMyVehicle đã định nghĩa trong layout thông qua
        binding.twoBindingMyVehicle = v2wayBinding

        // Thiết lập đối tượng xử lý sự kiện click cho nút button sử dụng Data Binding
        binding.clickHandlers = VehicleClickHandlers(this)

    }
}