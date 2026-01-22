package com.didan.android.firebase.dbrt

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class MainActivity : AppCompatActivity() {

    // Tạo biến tham chiếu đến cơ sở dữ liệu Realtime Database
    private lateinit var database: DatabaseReference

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
        val textview2: TextView = findViewById(R.id.textView2)

        // Gán biến tham chiếu đến cơ sở dữ liệu
        // https://fir-kotlin-77e8d-default-rtdb.asia-southeast1.firebasedatabase.app/
        database = Firebase.database("https://fir-kotlin-77e8d-default-rtdb.asia-southeast1.firebasedatabase.app").reference

        // Viết dữ liệu vào Realtime Database với khóa "price" và giá trị "1920 $"
        database.child("price").setValue("1930 $")

        // Đọc dữ liệu từ Realtime Database với khóa "price"
        val postListener = object : ValueEventListener {
            // Xử lý khi có dữ liệu thay đổi
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.i("DANY", "Value is: ${snapshot.value}")
                val gold_price = snapshot.value // Lấy giá trị từ snapshot
                textView.text = gold_price.toString()
            }

            // Xử lý khi có lỗi xảy ra
            override fun onCancelled(error: DatabaseError) {
                Log.i("DANY", "Failed to read value.", error.toException())
            }
        }

        // Gắn listener để lắng nghe thay đổi dữ liệu tại khóa "price"
        database.child("price").addValueEventListener(postListener)

        // Viết dữ liệu kiểu object vào Realtime Database
        val user1 = User("didan", "12345")
        database.child("Users").setValue(user1)

        // Đọc dữ liệu từ Realtime Database với khóa "price"
        val postUserListener = object : ValueEventListener {
            // Xử lý khi có dữ liệu thay đổi
            override fun onDataChange(snapshot: DataSnapshot) {
                val u1 = snapshot.getValue<User>() // Lấy giá trị từ snapshot và ép kiểu về User
                Log.i("DANY", "User name: ${u1?.username}, password: ${u1?.password}")
                textview2.text = "User name: ${u1?.username}, password: ${u1?.password}"
            }

            // Xử lý khi có lỗi xảy ra
            override fun onCancelled(error: DatabaseError) {
                Log.i("DANY", "Failed to read value.", error.toException())
            }
        }

        // Gắn listener để lắng nghe thay đổi dữ liệu tại khóa "Users"
        database.child("Users").addValueEventListener(postUserListener)
    }
}