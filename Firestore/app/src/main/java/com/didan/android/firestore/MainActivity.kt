package com.didan.android.firestore

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

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

        val textView = findViewById<TextView>(R.id.textView)

        // Khởi tạo Firestore instance
        val db = Firebase.firestore

        // Tạo 1 collection "Users"
        val users_collection = db.collection("Users")

        // Tạo document trong collection "Users"
        val user1 = hashMapOf(
            "name" to "jack",
            "lname" to "reacher",
            "born" to "1992"
        )

        val user2 = hashMapOf(
            "name" to "john",
            "lname" to "travolta",
            "born" to "1982"
        )

        // Thêm document vào collection "Users" với ID là "user1"
        users_collection.document("user1").set(user1)
        // Thêm document vào collection "Users" với ID là "user2"
        users_collection.document("user2").set(user2)

        // Nhận dữ liệu từ collection Firestore
        val docRef = db.collection("Users").document("user1") // Truy cập document "user1" trong collection "Users"

        // Lắng nghe sự kiện thàn công và hiển thị dữ liệu lên TextView (Dữ liệu cụ th từ document thuộc tính "name")
//        docRef.get().addOnSuccessListener { document ->
//            if (document != null) { // Kiểm tra document có tồn tại
//                textView.text = "${document.data?.get("name")}" // Hiển thị dữ liệu document lên TextView thuộc tính "name"
//            }
//        }

        // Lấy tất cả document trong collection "Users"
        var allDocuments: String = ""

        // lắng nghe sự kiện thành công và hiển thị dữ liệu lên TextView
        db.collection("Users").get().addOnSuccessListener { result ->
            // Duyệt qua tất cả document trong collection "Users"
            for (document in result) {
                Log.i("TAGY", "${document.data}")
                allDocuments += "${document.data}"
                allDocuments += "\n\n"
            }
            textView.text = allDocuments
        }


        // Cập nhật dữ liệu trong Firestore
        db.collection("Users") // Truy cập collection "Users"
            .document("user1") // Truy cập document "user1"
            .update("born", "1995") // Cập nhật thuộc tính "born" thành "1995"

        // Xóa dữ liệu trong Firestore
        db.collection("Users") // Truy cập collection "Users"
            .document("user2") // Truy cập document "user2"
            .delete() // Xóa document "user2"
    }
}