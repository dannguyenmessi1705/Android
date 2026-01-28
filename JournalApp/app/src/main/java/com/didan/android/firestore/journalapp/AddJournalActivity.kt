package com.didan.android.firestore.journalapp

import android.app.ComponentCaller
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.didan.android.firestore.journalapp.databinding.ActivityAddJournalBinding
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.Date

class AddJournalActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddJournalBinding // Khai báo biến binding cho ActivityAddJournalBinding

    // Credentials và các tham chiếu Firebase có thể được khai báo ở đây nếu cần thiết
    var currentUserId: String = ""
    var currentUserName: String = ""

    // Firebase
    lateinit var auth: FirebaseAuth
    lateinit var user: FirebaseUser

    // Firestore và Storage tham chiếu có thể được khai báo ở đây nếu cần thiết
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    lateinit var storageReference: StorageReference
    var collectionReference: CollectionReference =
        db.collection("Journal") // Tham chiếu đến collection "Journal" trong Firestore)

    lateinit var imageUri: Uri // Biến để lưu URI của hình ảnh được chọn

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_add_journal
        ) // Thiết lập DataBinding cho layout activity_add_journal
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        storageReference =
            FirebaseStorage.getInstance().getReference() // Lấy tham chiếu đến Firebase Storage

        auth = Firebase.auth // Lấy dịch vụ Authentication từ Firebase

        // Áp dụng các thiết lập hoặc sự kiện cho binding ở đây nếu cần thiết
        binding.apply {
            postProgressBar.visibility = View.INVISIBLE // Ẩn ProgressBar ban đầu
            // Nếu JournalUser singleton đã được khởi tạo, lấy userId và username
            if (JournalUser.instance != null) {
//                currentUserId =
//                    JournalUser.instance!!.userId.toString() // Lấy userId từ JournalUser singleton

                currentUserId =
                    auth.currentUser?.uid.toString() // Lấy userId từ FirebaseAuth hiện tại


//                currentUserName =
//                    JournalUser.instance!!.username.toString() // Lấy username từ JournalUser singleton

                currentUserName =
                    auth.currentUser?.displayName.toString() // Lấy username từ FirebaseAuth hiện tại

                postUsernameTextView.text = currentUserName // Hiển thị username trên TextView
            }


            // Xử lý sự kiện khi người dùng chọn hình ảnh từ thư viện
            postCameraButton.setOnClickListener {
                val intent: Intent = Intent(Intent.ACTION_GET_CONTENT) // Tạo Intent để chọn nội dung
                intent.type = "image/*" // Chỉ định loại nội dung là hình ảnh
                startActivityForResult(intent, 1) // Bắt đầu Activity để chọn hình ảnh với requestCode là 1 (sẽ được xử lý trong onActivityResult)
            }

            postSaveJournalButton.setOnClickListener {
                saveJournal() // Gọi hàm saveJournal khi nhấn nút lưu bài viết
            }
        }

    }

    /**
     * Hàm saveJournal để lưu bài viết mới vào Firestore và Storage.
     */
    private fun saveJournal() {
        val title: String = binding.postTitleEt.text.toString().trim()
        val thoughts: String = binding.postDescriptionEt.text.toString().trim()

        binding.postProgressBar.visibility =
            View.VISIBLE // Hiển thị ProgressBar khi bắt đầu lưu bài viết

        // Kiểm tra nếu tiêu đề, nội dung và hình ảnh không rỗng trước khi lưu
        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(thoughts) && imageUri != null) {
            // Lưu bài viết vào Firestore và Storage ở đây .../journal_images/our_image.png

            // Tạo một tham chiếu tệp duy nhất trong Firebase Storage
            val filePath: StorageReference = storageReference
                .child("journal_images") // Thư mục trong Storage
                .child("my_image_" + Timestamp.now().seconds) // Tên tệp duy nhất dựa trên thời gian hiện tại

            // Tải lên hình ảnh từ URI đã chọn
            filePath.putFile(imageUri) // Tải lên tệp từ imageUri
                .addOnSuccessListener { // Lắng nghe khi tải lên thành công
                    filePath.downloadUrl.addOnSuccessListener { // Lấy URL tải xuống của tệp đã tải lên
                        val imageUri: String = it.toString() // Lấy URL hình ảnh dưới dạng chuỗi

                        // Tạo một đối tượng Journal với thông tin bài viết
                        val journal: Journal = Journal(
                            title,
                            thoughts,
                            imageUri,
                            currentUserId,
                            Timestamp(Date()),
                            currentUserName
                        )

                        // Thêm bài viết vào Firestore
                        collectionReference.add(journal)
                            .addOnSuccessListener {
                                binding.postProgressBar.visibility =
                                    View.INVISIBLE // Ẩn ProgressBar khi lưu thành công
                                // Quay lại JournalList sau khi lưu thành công
                                val intent = Intent(this, JournalList::class.java)
                                startActivity(intent)
                                finish() // Kết thúc AddJournalActivity (Cần có hàm finish để tránh quay lại màn hình này khi nhấn nút back)
                            }
                    }
                }.addOnFailureListener { // Xử lý khi tải lên thất bại
                    binding.postProgressBar.visibility = View.INVISIBLE // Ẩn ProgressBar khi có lỗi
                }

        } else {
            binding.postProgressBar.visibility =
                View.INVISIBLE // Ẩn ProgressBar nếu dữ liệu không hợp lệ
        }
    }

    /**
     * Hàm onActivityResult để xử lý kết quả từ Activity chọn hình ảnh.
     */
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        caller: ComponentCaller
    ) {
        super.onActivityResult(requestCode, resultCode, data, caller)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                imageUri = data.data!! // Lấy URI của hình ảnh được chọn
                binding.postImageView.setImageURI(imageUri) // Hiển thị hình ảnh đã chọn trong ImageView
            }
        }
    }

    /**
     * Hàm onStart để lấy người dùng hiện tại từ Firebase Authentication khi Activity bắt đầu.
     */
    override fun onStart() {
        super.onStart()

        user = auth.currentUser!! // Lấy người dùng hiện tại từ Firebase Authentication
    }

    /**
     * Hàm onStop thực hiện khi Activity dừng.
     */
    override fun onStop() {
        super.onStop()
        if (auth != null) {
        }
    }
}