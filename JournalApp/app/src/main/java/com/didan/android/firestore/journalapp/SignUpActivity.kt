package com.didan.android.firestore.journalapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.didan.android.firestore.journalapp.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth // Khai báo biến FirebaseAuth
    lateinit var binding: ActivitySignUpBinding // Khai báo biến binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up) // Thiết lập DataBinding cho layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth // Lấy dịch vụ Authentication từ Firebase (Cần thiết lập trên Firebase Console)

        // Xử lý sự kiện khi người dùng nhấn nút đăng ký
        binding.accSignUpButton.setOnClickListener {
            createUser()
        }

    }

    override fun onStart() {
        super.onStart()
        // Kiểm tra nếu người dùng đã đăng nhập (null nếu chưa đăng nhập)
        val currentUser = auth.currentUser // Lấy người dùng hiện tại
        if (currentUser != null) { // Nếu người dùng đã đăng nhập
            reload() // Hàm reload để cập nhật giao diện người dùng
        }
    }

    private fun createUser() {
        val email = binding.emailCreate.text.toString() // Lấy email từ trường nhập liệu
        val password = binding.passwordCreate.text.toString() // Lấy mật khẩu từ trường nhập

        auth.createUserWithEmailAndPassword(email, password) // Tạo người dùng mới với email và mật khẩu
            .addOnCompleteListener(this) { task -> // Lắng nghe kết quả của tác vụ
                if (task.isSuccessful) {
                    // Đăng ký thành công, cập nhật giao diện người dùng với thông tin người dùng đã đăng ký
                    Log.d("DANY", "createUserWithEmail:success")
                    val user = auth.currentUser // Lấy người dùng hiện tại
                    updateUI(user) // Cập nhật giao diện người dùng
                } else {
                    // Nếu đăng ký thất bại, hiển thị thông báo cho người dùng.
                    Log.w("DANY", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null) // Cập nhật giao diện người dùng với null
                }
            }
    }

    // Hàm reload để cập nhật giao diện người dùng
    private fun updateUI(user: FirebaseUser?) {

    }

    // Hàm reload để cập nhật giao diện người dùng
    private fun reload() {

    }
}