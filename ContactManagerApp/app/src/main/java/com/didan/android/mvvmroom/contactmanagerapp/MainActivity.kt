package com.didan.android.mvvmroom.contactmanagerapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.didan.android.mvvmroom.contactmanagerapp.databinding.ActivityMainBinding
import com.didan.android.mvvmroom.contactmanagerapp.repository.ContactRepository
import com.didan.android.mvvmroom.contactmanagerapp.room.Contact
import com.didan.android.mvvmroom.contactmanagerapp.room.ContactDatabase
import com.didan.android.mvvmroom.contactmanagerapp.view.MyRecyclerViewAdapter
import com.didan.android.mvvmroom.contactmanagerapp.viewmodel.ContactViewModel
import com.didan.android.mvvmroom.contactmanagerapp.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Thiết lập Data Binding cho Activity
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Room database và ViewModel sẽ được khởi tạo ở đây (code khởi tạo bị bỏ qua để đơn giản hóa)
        val dao = ContactDatabase.getInstance(applicationContext).contactDAO // Lấy DAO từ database
        val repository = ContactRepository(dao) // Tạo repository với DAO
        val factory = ViewModelFactory(repository)

        // ViewModel
        contactViewModel = ViewModelProvider(this, factory)[ContactViewModel::class.java]

        // Gán ViewModel cho Data Binding
        binding.contactViewModel = contactViewModel

        binding.lifecycleOwner =
            this // Thiết lập LifecycleOwner để Data Binding có thể quan sát LiveData

        // Thiết lập recycleView và các thành phần giao diện khác ở đây (code bị bỏ qua để đơn giản hóa)
        initRecycleView()
    }

    private fun initRecycleView() {
        // Thiết lập LayoutManager cho RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Thiết lập Adapter cho RecyclerView (code bị bỏ qua để đơn giản hóa)
        DisplayUserList()
    }

    /**
     * Hiển thị danh sách người dùng trong RecyclerView.
     */
    private fun DisplayUserList() {
        // Quan sát thay đổi trong danh sách contacts từ ViewModel
        contactViewModel.contacts.observe(this, {
            // Cập nhật Adapter của RecyclerView với danh sách mới và xử lý sự kiện nhấp vào mục
            binding.recyclerView.adapter = MyRecyclerViewAdapter(
                it,
                { selectItem: Contact -> listItemClicked(selectItem) }
            )
        })
    }

    /**
     * Xử lý sự kiện khi một mục trong danh sách được nhấp.
     * Hiển thị một Toast với tên của liên hệ đã chọn.
     * @param selectedItem Liên hệ đã được chọn.
     */
    private fun listItemClicked(selectedItem: Contact) {
        Toast.makeText(
            this,
            "Selected Name is ${selectedItem.contact_name}",
            Toast.LENGTH_LONG
        ).show()
    }
}
