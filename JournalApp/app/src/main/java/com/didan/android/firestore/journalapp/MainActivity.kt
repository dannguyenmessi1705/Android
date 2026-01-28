package com.didan.android.firestore.journalapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.didan.android.firestore.journalapp.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding // Khai báo biến binding cho ActivityMainBinding

    // Firebase Auth có thể được khai báo ở đây nếu cần thiết
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        ) // Thiết lập DataBinding cho layout activity_main
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Xử lý sự kiện khi người dùng nhấn nút tạo tài khoản
        binding.createAccButton.setOnClickListener {
            val intent =
                Intent(this, SignUpActivity::class.java) // Tạo Intent để chuyển sang SignUpActivity
            startActivity(intent) // Bắt đầu Activity SignUpActivity
        }

        binding.emailSignInButton.setOnClickListener {
            loginWithEmailPassword(
                binding.email.text.toString().trim(),
                binding.password.text.toString().trim()
            ) // Gọi hàm đăng nhập với email và mật khẩu
        }

        // Authentication có thể được khởi tạo ở đây nếu cần thiết
        auth = Firebase.auth
    }

    /**
     * Phương thức onStart được gọi khi Activity trở nên hiển thị với người dùng.
     */
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser // Lấy người dùng hiện tại
        // Nếu người dùng đã đăng nhập, chuyển sang JournalList
        if (currentUser != null) {
            val intent =
                Intent(this, JournalList::class.java) // Tạo Intent để chuyển sang JournalList
            startActivity(intent) // Bắt đầu Activity JournalList
        }
    }

    fun loginWithEmailPassword(email: String, password: String) {
        // Thực hiện đăng nhập với email và mật khẩu
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user =
                        auth.currentUser // Lấy người dùng hiện tại sau khi đăng nhập thành công

                    val journalUser: JournalUser = JournalUser.instance!! // Lấy instance của JournalUser singleton
                    journalUser.userId = user?.uid // Lưu userId vào JournalUser
                    journalUser.username = user?.displayName // Lưu username vào JournalUser

                    // Đăng nhập thành công, chuyển sang JournalList
                    val intent =
                        Intent(
                            this,
                            JournalList::class.java
                        ) // Tạo Intent để chuyển sang JournalList
                    startActivity(intent) // Bắt đầu Activity JournalList
                } else {
                    // Xử lý lỗi đăng nhập nếu cần thiết
                    Toast.makeText(
                        this,
                        "Authentication failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}