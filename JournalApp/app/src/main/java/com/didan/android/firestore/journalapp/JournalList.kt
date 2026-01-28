package com.didan.android.firestore.journalapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.didan.android.firestore.journalapp.databinding.ActivityJournalListBinding
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference

class JournalList : AppCompatActivity() {

    lateinit var binding: ActivityJournalListBinding // Khai báo biến binding cho ActivityJournalListBinding

    // Tham chiếu đến Firestore, Cloud Storage, FirebaseAuth nếu cần thiết
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var user: FirebaseUser
    var db = FirebaseFirestore.getInstance()
    lateinit var storageReference: StorageReference
    var collectionReference: CollectionReference =
        db.collection("Journal") // Tham chiếu đến collection "Journal" trong Firestore

    lateinit var journalList: MutableList<Journal> // Danh sách các bài viết Journal, mutableList để có thể thêm/xóa phần tử
    lateinit var adapter: JournalRecyclerAdapter
    lateinit var noPostsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_journal_list
        ) // Thiết lập DataBinding cho layout activity_journal_list
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Firebase Auth
        firebaseAuth = Firebase.auth // Lấy dịch vụ Authentication từ Firebase
        user = firebaseAuth.currentUser!! // Lấy người dùng hiện tại

        // RecyclerView và Adapter có thể được thiết lập ở đây nếu cần thiết
        binding.recyclerView.setHasFixedSize(true) // Tối ưu hóa hiệu suất RecyclerView
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this) // Sử dụng LinearLayoutManager cho RecyclerView

        // Posts array list
        journalList = arrayListOf()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu) // Inflate menu từ tài nguyên menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> if (user != null && firebaseAuth != null) {
                // Chuyển đến AddJournalActivity để thêm bài viết mới
                val intent = android.content.Intent(this, AddJournalActivity::class.java)
                startActivity(intent)
            }

            R.id.action_sign_out -> {
                if (user != null && firebaseAuth != null) {
                    firebaseAuth.signOut() // Đăng xuất người dùng
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent) // Chuyển đến MainActivity sau khi đăng xuất
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Hàm onStart để xử lý các tác vụ khi Activity bắt đầu
    override fun onStart() {
        super.onStart()

        // Lấy dữ liệu từ Firestore và cập nhật RecyclerView
        collectionReference.whereEqualTo(
            "userId",
            user.uid
        ) // Lọc các bài viết theo userId
            .get() // Lấy dữ liệu từ Firestore
            .addOnSuccessListener { // Xử lý khi lấy dữ liệu thành công
                if (!it.isEmpty) {
                    // Lặp qua các tài liệu trong kết quả truy vấn
                    for (document in it) {
                        val journal = Journal(
                            document.data["title"].toString(),
                            document.data["thoughts"].toString(),
                            document.data["imageUri"].toString(),
                            document.data["userId"].toString(),
                            document.data["timeAdded"] as Timestamp,
                            document.data["username"].toString(),
                        )

                        journalList.add(journal)
                    }

                    // RecyclerView adapter
                    adapter = JournalRecyclerAdapter(
                        this,
                        journalList
                    ) // Tạo adapter với danh sách journal
                    binding.recyclerView.adapter = adapter // Gán adapter cho RecyclerView
                    adapter.notifyDataSetChanged() // Thông báo adapter về việc dữ liệu đã thay đổi
                } else {
                    binding.listNoPosts.visibility =
                        View.VISIBLE // Hiển thị thông báo không có bài viết nếu danh sách rỗng
                }
            }
            .addOnFailureListener { // Xử lý khi lấy dữ liệu thất bại
                Toast.makeText(
                    this,
                    "Opps... Something went wrong.",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}