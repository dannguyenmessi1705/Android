package com.didan.android.fragment.app

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

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

        val btn1 = findViewById<Button>(R.id.displayFrag1)
        val btn2 = findViewById<Button>(R.id.displayFrag2)

        // Xử lý sự kiện click cho nút btn1 và btn2
        btn1.setOnClickListener {
            val fragment1: FragmentOne = FragmentOne() // Tạo instance của FragmentOne
            loadFragment(fragment1) // Gọi hàm loadFragment để hiển thị FragmentOne
        }

        btn2.setOnClickListener {
            val fragment2: FragmentTwo = FragmentTwo() // Tạo instance của FragmentTwo
            loadFragment(fragment2) // Gọi hàm loadFragment để hiển thị FragmentTwo
        }


    }

    fun loadFragment(fragment: Fragment) {
        // Fragment Manager: Chịu trách nhiệm quản lý các Fragment trong một Activity.
        // Nó lưu trữ và quản lý vòng đời của các Fragment, bao gồm việc thêm, loại bỏ, thay thế
        // Cũng chịu trách nhiệm xử lý các Fragment Transactions.
        val fragmentManager = supportFragmentManager

        // Fragment Transaction: Là một tập hợp các thao tác (operations) được thực hiện trên các fragment
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        // Thay thế Fragment hiện tại trong container với Fragment mới
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit() // Áp dụng các thay đổi
    }
}